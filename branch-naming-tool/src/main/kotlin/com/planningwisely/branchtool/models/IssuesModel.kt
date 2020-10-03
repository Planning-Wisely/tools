package com.planningwisely.branchtool.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Issues model of githib issue api response.
 *
 * [GitHub API Reference](https://docs.github.com/en/free-pro-team@latest/rest/reference/issues#list-repository-issues)
 * @property root list with issues represented as `List<IssuesModelItem>` type.
 */
@Serializable
class IssuesModel(val root: List<IssuesModelItem> = listOf()) {
    @Serializable
    data class IssuesModelItem(
        val number: Int = 0,
        val title: String = "",
        @SerialName("html_url")
        val htmlUrl: String = ""
    )
}
