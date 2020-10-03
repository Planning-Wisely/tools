package com.planningwisely.branchtool.github.impl

import com.planningwisely.branchtool.ACCEPT
import com.planningwisely.branchtool.API
import com.planningwisely.branchtool.AUTH_HEADER
import com.planningwisely.branchtool.HTTP_SUCCESS_CODE
import com.planningwisely.branchtool.github.abst.IGitHubRequest
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Request.Builder
import okhttp3.Response

class GitHubRequest : IGitHubRequest {
    private val client = OkHttpClient()

    private fun token() = "Bearer ${System.getenv("GradlePass")}"
    private fun String.fixJsonArray() = "{ \"root\": ".plus(this).plus("}")
    private fun request() = Builder().header(AUTH_HEADER, token()).addHeader("Accept", ACCEPT)
    private inline fun <T> Request.perform(block: (Response) -> T) =
        client.newCall(this).execute().use { block(it) }

    override fun fetchRepositories(owner: String) = request()
        .url("$API/orgs/$owner/repos").build().run {
            perform {
                check(it.code == HTTP_SUCCESS_CODE) {
                    "An error occurred while getting repositories, code: ${it.code}"
                }.let { _ -> it.body?.string() ?: "{}" }
            }
        }.fixJsonArray()

    override fun fetchIssues(
        owner: String, repo: String, params: Set<Pair<String, String>>
    ) = request()
        .url("$API/repos/$owner/$repo/issues?${params.joinToString("&") { "${it.first}=${it.second}" }}")
        .build().run {
            perform {
                check(it.code == HTTP_SUCCESS_CODE) {
                    "An error occurred while getting issues of repository ${owner}/${repo}, code: ${it.code}"
                }.let { _ -> it.body?.string() ?: "{}" }
            }
        }.fixJsonArray()
}
