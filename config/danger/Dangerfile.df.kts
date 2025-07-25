import systems.danger.kotlin.danger
import systems.danger.kotlin.message
import systems.danger.kotlin.models.github.GitHub
import systems.danger.kotlin.models.github.GitHubCommit
import systems.danger.kotlin.onGitHub
import systems.danger.kotlin.warn

danger(args) {
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
    message("<a href=\"$apkPath\">:link: Download APK</a>")
}