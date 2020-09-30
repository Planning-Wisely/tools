package models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RepositoriesModel(
    val entry: List<RepositoriesModelItem> = listOf()
) {
    @Serializable
    data class RepositoriesModelItem(
        val name: String = "",
        @SerialName("open_issues_count")
        val openIssuesCount: Int = 0,
        val url: String = ""
    )
}
