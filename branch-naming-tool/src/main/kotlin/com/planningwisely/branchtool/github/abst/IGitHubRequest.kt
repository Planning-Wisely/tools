package com.planningwisely.branchtool.github.abst

/**
 * Contract / interface of github api request
 * implementation.
 */
interface IGitHubRequest {
    /**
     * Does fetches github repositories.
     * @param owner repositories owner.
     * @return raw json response.
     */
    fun fetchRepositories(owner: String): String

    /**
     * Does fetches github issues.
     * @param owner repositories owner.
     * @param repo repository owned by the owner.
     * @param params parameters for request.
     * @return raw json response.
     */
    fun fetchIssues(
        owner: String, repo: String, params: Set<Pair<String, String>>
    ): String
}
