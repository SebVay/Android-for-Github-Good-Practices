import systems.danger.kotlin.danger
import systems.danger.kotlin.markdown
import systems.danger.kotlin.message
import systems.danger.kotlin.models.git.Git
import systems.danger.kotlin.models.github.GitHub
import systems.danger.kotlin.models.github.GitHubCommit
import systems.danger.kotlin.onGitHub
import systems.danger.kotlin.warn
import java.security.MessageDigest

danger(args) {

    generateInsights(git, github)

    onGitHub {
        notifyBigPR()

        notifyAnyWipInPR()

        notifyDraftPR()
    }

    notifyApkLink()
}

/**
 * Notifies if a pull request is a draft.
 *
 * This function checks if the current pull request is a draft. If it is,
 * it posts a message to remind the author to open it before assigning reviewers.
 */
fun GitHub.notifyDraftPR() {
    if (pullRequest.isDraft) {
        message("This PR is a Draft, remember to open it before assigning people.")
    }
}

/**
 * Notifies if the PR title or any commit message contains "WIP" (Work in Progress).
 * This helps to prevent merging incomplete work.
 *
 * If "WIP" is found, a warning message is posted.
 */
fun GitHub.notifyAnyWipInPR() {
    val wipMessage = "WIP"

    if (pullRequest.title.contains(wipMessage, false)) {
        warn("PR is classed as Work in Progress")
    }

    commits
        .map(GitHubCommit::commit)
        .filter { it.message.contains(other = wipMessage, ignoreCase = true) }
        .forEach { commit ->
            val commitMessage = commit.sha ?: commit.message
            warn("Commit `${commitMessage}` is classed as Work in Progress")
        }
}

/**
 * Notifies if a pull request has a large number of changes (more than 300 lines added/deleted).
 * This encourages smaller, more manageable pull requests.
 */
fun GitHub.notifyBigPR() {
    if ((pullRequest.additions ?: 0) - (pullRequest.deletions ?: 0) > 300) {
        warn("Big PR, try to keep changes smaller if you can")
    }
}

/**
 * Notifies with a link to download the APK.
 *
 * This function retrieves the APK path from the "BITRISE_APK_PATH" environment variable
 * and posts a message with a hyperlink to download the APK.
 */
fun notifyApkLink() {
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

fun generateInsights(git: Git, gitHub: GitHub) {
    markdown(
        getInsightReport(
            gitHub = gitHub,
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
fun getInsightReport(
    gitHub: GitHub,
    addedFiles: List<String>,
    modifiedFiles: List<String>,
    deletedFiles: List<String>,
): String {
    val modules = (
        addedFiles.mapFilesTo(Status.Added) +
            modifiedFiles.mapFilesTo(Status.Modified) +
            deletedFiles.mapFilesTo(Status.Deleted)
        )
        .mapToModules()

    return buildString {
        append(
            """
            # Informations
            ## Modules modifiés
            <table>
            <tr><th></th>
            <th>Added</th>
            <th>Modified</th>
            <th>Deleted</th>
            </tr>
            """.trimIndent()
        )

        modules.forEach { module ->

            val addedColumn = buildString {
                module.files
                    .filter { it.status == Status.Added }
                    .forEach {
                        appendLine("🟢&nbsp;${it.getFileLink(gitHub)} ")
                    }
            }
            val modifiedColumn = buildString {
                module.files
                    .filter { it.status == Status.Modified }
                    .forEach {
                        appendLine("🟡&nbsp;${it.getFileLink(gitHub)} ")
                    }
            }

            val deletedColumn = buildString {
                module.files
                    .filter { it.status == Status.Deleted }
                    .forEach {
                        appendLine("🔴&nbsp;${it.getFileLink(gitHub)} ")
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

private fun List<String>.mapFilesTo(status: Status): List<VersionedFile> {
    return map { filePath ->
        val fullPath = filePath.removePrefix("'a/' --dst-prefix='b/'")
        val fileName = fullPath.substringAfterLast("/")
        VersionedFile(
            name = fileName,
            fullPath = fullPath,
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
fun List<VersionedFile>.mapToModules(): List<Module> {
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

data class Module(
    val name: String,
    val files: List<VersionedFile>,
    val isFallback: Boolean,
)

data class VersionedFile(
    val name: String,
    val fullPath: String,
    val status: Status,
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

enum class Status {
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
fun VersionedFile.getFileLink(gitHub: GitHub): String {
    val link = gitHub.pullRequest.htmlURL + "/files#diff-" + sha256Path
    return "<a href=\"$link\">$name</a>"
}