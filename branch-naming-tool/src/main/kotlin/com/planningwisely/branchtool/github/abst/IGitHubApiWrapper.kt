package com.planningwisely.branchtool.github.abst

/**
 * Contract / interface of internal github
 * api implementation.
 */
interface IGitHubApiWrapper {
    /**
     * @param T repositories model return type.
     * @param owner repositories owner.
     * @return repositories model represented as [T].
     */
    fun <T> getRepositories(owner: String): T

    /**
     * @param T issues model return type.
     * @param owner repositories owner.
     * @param repo repository owned by the owner.
     * @param page page of issues.
     * @return issues model represented as [T].
     */
    fun <T> getIssues(owner: String, repo: String, page: Int): T
}
