package models

import kotlinx.serialization.Serializable

@Serializable
class IssuesModel(val entry: List<IssuesModelItem> = listOf()) {
    @Serializable
    data class IssuesModelItem(val number: Int = 0, val title: String = "", val url: String = "")
}
