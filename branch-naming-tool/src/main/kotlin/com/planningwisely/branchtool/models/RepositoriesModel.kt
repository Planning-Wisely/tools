package com.planningwisely.branchtool.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Repositories model of githib repositories api response.
 *
 * GitHub API Reference: `https://docs.github.com/en/free-pro-team@latest/
 * rest/reference/repos#list-organization-repositories`
 * @property root list with repositories represented as `List<RepositoriesModelItem>` type.
 */
@Serializable
data class RepositoriesModel(val root: List<RepositoriesModelItem> = listOf()) {
    @Serializable
    data class RepositoriesModelItem(
        val name: String = "",
        @SerialName("open_issues_count")
        val openIssuesCount: Int = 0,
        @SerialName("html_url")
        val htmlUrl: String = ""
    )
}
