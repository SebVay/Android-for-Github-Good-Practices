@file:Import("./insightReport.kts")

import systems.danger.kotlin.danger
import systems.danger.kotlin.markdown
import systems.danger.kotlin.message
import systems.danger.kotlin.models.git.Git
import systems.danger.kotlin.models.github.GitHub
import systems.danger.kotlin.models.github.GitHubCommit
import systems.danger.kotlin.onGit
import systems.danger.kotlin.onGitHub
import systems.danger.kotlin.warn

danger(args) {

    onGit {
        generateInsights()
    }

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

fun Git.generateInsights() {
    generateInsightReport(createdFiles, modifiedFiles, deletedFiles)
}

fun printAndMessage(message: String) {
    println(message)
    message(message)
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
fun generateInsightReport(addedFiles: List<String>, modifiedFiles: List<String>, deletedFiles: List<String>) {
    val modules = (
        addedFiles.mapFilesTo(Status.Added) +
            modifiedFiles.mapFilesTo(Status.Modified) +
            deletedFiles.mapFilesTo(Status.Deleted)
        )
        .mapToModules()

    buildString {
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
                        appendLine("🟢 ${it.name} <br>")
                    }
            }
            val modifiedColumn = buildString {
                module.files
                    .filter { it.status == Status.Modified }
                    .forEach {
                        appendLine("🟡 ${it.name} <br>")
                    }
            }

            val deletedColumn = buildString {
                module.files
                    .filter { it.status == Status.Deleted }
                    .forEach {
                        appendLine("🔴 ${it.name} <br>")
                    }
            }

            appendLine(
                """
                <tr>
                <td> **${module.name}** </td>
                <td> $addedColumn </td>
                <td> $modifiedColumn </td>
                <td> $deletedColumn </td>
                <td>
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
 * If a file's path does not contain "/src/", it is assigned to a fallback module named "Other Modules".
 * The resulting list of modules is sorted so that the "Other Modules" (if present) appears last.
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
            name = name ?: "Other Modules",
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
)

enum class Status {
    Added, Modified, Deleted
}