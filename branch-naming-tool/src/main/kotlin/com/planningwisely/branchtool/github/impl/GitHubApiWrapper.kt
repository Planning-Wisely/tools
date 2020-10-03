package com.planningwisely.branchtool.github.impl

import com.planningwisely.branchtool.github.abst.IGitHubApiWrapper
import com.planningwisely.branchtool.github.abst.IGitHubRequest
import com.planningwisely.branchtool.helpers.json
import com.planningwisely.branchtool.models.IssuesModel
import com.planningwisely.branchtool.models.RepositoriesModel
import kotlinx.serialization.decodeFromString
import org.koin.core.KoinComponent
import org.koin.core.inject

@Suppress("UNCHECKED_CAST")
class GitHubApiWrapper : IGitHubApiWrapper, KoinComponent {
    private val api by inject<IGitHubRequest>()

    private fun issuesArgs(page: Int) = setOf(
        "state" to "open", "direction" to "asc", "page" to page.toString(), "per_page" to "50"
    )

    override fun <T> getRepositories(owner: String) =
        json().decodeFromString<RepositoriesModel>(api.fetchRepositories(owner)) as T

    override fun <T> getIssues(owner: String, repo: String, page: Int) =
        json().decodeFromString<IssuesModel>(api.fetchIssues(owner, repo, issuesArgs(page))) as T
}
