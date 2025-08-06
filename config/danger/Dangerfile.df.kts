import systems.danger.kotlin.danger
import systems.danger.kotlin.markdown
import systems.danger.kotlin.message
import systems.danger.kotlin.models.danger.DangerDSL
import systems.danger.kotlin.models.danger.Utils
import systems.danger.kotlin.models.github.GitHub
import systems.danger.kotlin.models.github.GitHubCommit
import systems.danger.kotlin.onGitHub
import java.security.MessageDigest
import java.time.DayOfWeek
import java.time.LocalDate
import kotlin.random.Random

private val projectRoot = "../../"

danger(args) {

    generateInsights()

    onGitHub {
        notifyBigPR()

        notifyAnyWipInPR()
    }

    notifyApkLink()

    notifyFunMessages()
}

/**
 * Notifies if the PR title or any commit message contains "WIP" (Work in Progress).
 * This helps to prevent merging incomplete work.
 *
 * If "WIP" is found, a warning message is posted.
 */
private fun GitHub.notifyAnyWipInPR() {
    val wipMessage = "wip"

    if (pullRequest.title.contains(wipMessage, ignoreCase = true) && !pullRequest.isDraft) {
        message("PR is classed as Work in Progress")
    }

    commits
        .map(GitHubCommit::commit)
        .filter { it.message.contains(wipMessage, ignoreCase = true) }
        .forEach { commit ->
            val commitMessage = commit.sha ?: commit.message
            message("Commit `${commitMessage}` is classed as Work in Progress")
        }
}

/**
 * Notifies if a pull request has a large number of changes (more than 300 lines added/deleted).
 * This encourages smaller, more manageable pull requests.
 */
private fun GitHub.notifyBigPR() {
    if ((pullRequest.additions ?: 0) - (pullRequest.deletions ?: 0) > 300) {
        message("Big PR, try to keep changes smaller if you can")
    }
}

/**
 * Notifies with a link to download the APK.
 *
 * This function retrieves the APK path from the "BITRISE_APK_PATH" environment variable
 * and posts a message with a hyperlink to download the APK.
 */
private fun notifyApkLink() {
    val apkPath = System.getenv("BITRISE_PUBLIC_INSTALL_PAGE_URL")
    markdown(
        """
        ## 📦 Debug APK Available for Download

        A new debug APK has been generated for this pull request and is available for testing:

        👉 [**Download APK**]($apkPath)
        *(Built automatically via Bitrise CI)*
        """.trimIndent()
    )
}

private fun DangerDSL.generateInsights() {
    markdown(
        getHtmlModuleOverview(
            addedFiles = git.createdFiles,
            modifiedFiles = git.modifiedFiles,
            deletedFiles = git.deletedFiles
        )
    )
}

/**
 * Generates an HTML report summarizing the changes made to files within different modules.
 *
 * The report is structured as a table with columns for "Added", "Modified", and "Deleted" files.
 *
 * Files are color-coded based on their status:
 * - Green (🟢) for added files
 * - Yellow (🟡) for modified files
 * - Red (🔴) for deleted files
 *
 * @param addedFiles A list of paths to files that were added.
 * @param modifiedFiles A list of paths to files that were modified.
 * @param deletedFiles A list of paths to files that were deleted.
 * @return A string containing the HTML report.
 */
private fun DangerDSL.getHtmlModuleOverview(
    addedFiles: List<String>,
    modifiedFiles: List<String>,
    deletedFiles: List<String>,
): String {
    val github = github()
    val modules = (
        addedFiles.mapFilesTo(Status.Added, utils) +
            modifiedFiles.mapFilesTo(Status.Modified, utils) +
            deletedFiles.mapFilesTo(Status.Deleted, utils)
        )
        .mapToModules()

    return buildString {

        val versionedFiles = modules.flatMap(Module::files)

        val totalAdded = versionedFiles
            .filter { it.status == Status.Added }
            .sumOf { it.insertions ?: 0 }

        val totalDeleted = versionedFiles
            .filter { it.status == Status.Deleted }
            .sumOf { it.deletions ?: 0 }

        val added = "\$\\color{Green}{\\textsf{+$totalAdded}}\$"
        val deleted = "\$\\color{Red}{\\textsf{-$totalDeleted}}\$"

        appendLine(
            """
            # Information
            ## Updated Modules
            <table>
                <tr>
                    <th></th>
                    <th>Added ($added)</th>
                    <th>Modified </th>
                    <th>Deleted ($deleted)</th>
                </tr>
            """.trimIndent()
        )

        modules.forEach { module ->
            val addedColumn = buildString {
                module.files
                    .filter { it.status == Status.Added }
                    .forEach {
                        appendLine("🟢&nbsp;${it.getFileLink(github)}<br>")
                    }
            }
            val modifiedColumn = buildString {
                module.files
                    .filter { it.status == Status.Modified }
                    .forEach { versionedFile ->
                        append("🟡&nbsp;${versionedFile.getFileLink(github)}<br>")
                    }
            }

            val deletedColumn = buildString {
                module.files
                    .filter { it.status == Status.Deleted }
                    .forEach {
                        appendLine("🔴&nbsp;${it.getFileLink(github)}<br>")
                    }
            }

            appendLine(
                """
                <tr>
                    <td><div style="display: inline-block;"><b>${module.name}</b></div></td>
                    <td>$addedColumn</td>
                    <td>$modifiedColumn</td>
                    <td>$deletedColumn</td>
                </tr>
            """.trimIndent()
            )
        }

        appendLine("</table>")
    }
}

private fun List<String>.mapFilesTo(status: Status, utils: Utils): List<VersionedFile> {
    return map { filePath ->
        val fullPath = filePath.removePrefix("'a/' --dst-prefix='b/'")
        val fileName = fullPath.substringAfterLast("/")

        val regex =
            """1 file changed, (?:(\d+)\s+insertions\(\+\))?(?:,\s*)?(?:(\d+)\s+deletions\(-\))?""".toRegex()
        val diffShortStat = utils.exec("git diff --shortstat origin/main -- $projectRoot$fullPath")

        val (insertions, deletions) = regex.find(diffShortStat).let { match ->
            val insertions = match?.groups?.get(1)?.value?.toIntOrNull()
            val deletions = match?.groups?.get(2)?.value?.toIntOrNull()

            insertions to deletions
        }

        VersionedFile(
            name = fileName,
            fullPath = fullPath,
            insertions = insertions,
            deletions = deletions,
            status = status
        )
    }
}


/**
 * Maps a list of [VersionedFile] objects to a list of [Module] objects.
 *
 * This function groups the files by their module name, which is derived from the file's full path.
 * The module name is extracted by taking the part of the path before "/src/" and replacing "/" with ":".
 * If a file's path does not contain "/src/", it is assigned to a fallback category named "Others".
 * The resulting list of modules is sorted so that "Others" (if present) appears last.
 *
 * @receiver A list of [VersionedFile] objects to be mapped.
 * @return A list of [Module] objects, where each module contains files belonging to it.
 *         The list is sorted with the "Other Modules" (if any) at the end.
 */
private fun List<VersionedFile>.mapToModules(): List<Module> {
    return groupBy { file ->
        file.fullPath
            .takeIf { path -> path.contains("/src/") }
            ?.substringBefore("/src/")
            ?.replace("/", ":")
    }.map { (name, files) ->
        Module(
            name = name ?: "Others",
            files = files,
            isFallback = name != null
        )
    }.sortedBy { !it.isFallback }
}

private data class Module(
    val name: String,
    val files: List<VersionedFile>,
    val isFallback: Boolean,
)

private data class VersionedFile(
    val name: String,
    val fullPath: String,
    val status: Status,
    val insertions: Int?,
    val deletions: Int?,
) {
    /**
     * Calculates the SHA-256 hash of the file's full path.
     *
     * This hash is used by GitHub to generate hyperlinks to specific file diffs.
     *
     * @return A string representing the SHA-256 hash of the `fullPath` in hexadecimal format.
     */
    val sha256Path: String
        get() = MessageDigest
            .getInstance("SHA-256")
            .digest(fullPath.toByteArray())
            .fold("") { str, it -> str + "%02x".format(it) }
}


private enum class Status {
    Added, Modified, Deleted
}

/**
 * Generates an HTML hyperlink to the specific file diff on GitHub.
 *
 * The link is constructed using the pull request's HTML URL and the SHA-256 hash
 * of the file's path, which is a format GitHub uses to identify specific file changes
 * within a pull request's "Files changed" tab.
 *
 * @param gitHub The GitHub context, used to access the pull request URL.
 * @return An HTML `<a>` tag string that links to the file diff on GitHub.
 *         The link text will be the `name` of the file.
 */
private fun VersionedFile.getFileLink(gitHub: GitHub?): String {
    val link = gitHub?.pullRequest?.htmlURL.orEmpty() + "/files#diff-" + sha256Path
    return "<a href=\"$link\">$name</a>"
}

/**
 * A collection of fun messages to be displayed in the Danger report.
 */
private fun DangerDSL.notifyFunMessages() {
    notifyIfFriday()
    notifyEstimatedCoffeeItTook()
    tossACoinToYourDanger()
}

private fun notifyIfFriday() {
    if (LocalDate.now().dayOfWeek == DayOfWeek.FRIDAY) {
        markdown(
            """
            🙈 **It's Friday**
            Are you _sure_ you want to merge this on a Friday?
            > "Nothing ruins a weekend like a Friday deploy."
            
            So, just a reminder to:
            - 📆 Relax and enjoy your weekend
            _From your friendly CI assistant 💚_
            """.trimIndent()
        )
    }
}

private fun tossACoinToYourDanger() {

    val random100 = Random(seed = 1).nextInt(until = 100)

    // Five percent chance of being triggered
    if (random100 in (0..5)) {
        val coin = listOf(
            "🪙 Heads — merge it!",
            "🪙 Tails — wait for another round."
        ).random()

        markdown(
            """
            🎰 **Merge Decision Coin Flip**  
            $coin  
            (_This is not legally binding. Use at your own risk._)
            """.trimIndent()
        )
    }
}

private fun DangerDSL.notifyEstimatedCoffeeItTook() {

    val random100 = Random(seed = 2).nextInt(until = 100)

    // Five percent chance of being triggered
    if (random100 in (0..5)) {

        val linesChanged = (github.pullRequest.additions ?: 0) + (github.pullRequest.deletions ?: 0)
        val estimatedCups = (linesChanged / 80).coerceAtLeast(1)

        markdown(
            """
            ☕ **Caffeine Estimation**

            This PR changed $linesChanged lines.
            - $estimatedCups cup(s) of coffee
            """.trimIndent()
        )
    }
}

private fun DangerDSL.github(): GitHub? = takeIf { onGitHub }?.github
