package com.github.app.lint

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.client.api.Vendor
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue
import com.github.app.lint.serialization.ShouldUseCustomComposableIssue

class LintRegistry : IssueRegistry() {
    override val issues: List<Issue> = listOf(
        ShouldUseCustomComposableIssue.ISSUE,
    )
    override val api: Int = CURRENT_API

    override val vendor: Vendor = Vendor(
        vendorName = VENDOR,
        feedbackUrl = "${BASE_URL}/issues",
        contact = BASE_URL,
    )

}

private const val VENDOR = "Github App"
private const val BASE_URL = "https://github.com/SebVay/"
