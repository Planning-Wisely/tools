import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Request.Builder
import okhttp3.Response

private val client = OkHttpClient()
private const val api = "https://api.github.com"
private const val accept = "application/vnd.github.v3+json"
private const val authHeader = "Authorization"

private fun String.fixJsonArray() = "{ \"entry\": ".plus(this).plus("}")
private fun token() = "Bearer ${System.getenv("GradlePass")}"
private fun request() = Builder().header(authHeader, token()).addHeader("Accept", accept)
private inline fun <T> Request.perform(block: (Response) -> T) =
    client.newCall(this).execute().use { block(it) }

/**
 * @return repositories of organization `Planning Wisely`.
 */
fun fetchRepositories() = request()
    .url("$api/orgs/Planning-Wisely/repos").build().run {
        perform {
            check(it.code == 200) {
                "An error occurred while getting repositories, code: ${it.code}"
            }.let { _ -> it.body?.string() ?: "{}" }
        }
    }.fixJsonArray()

/**
 * @param page page of repositories, one page contains 50 repositories.
 * @param repository repository to see issues.
 * @return issues of [repository] repository.
 */
fun fetchIssues(page: Int, repository: String) = request()
    .url("$api/repos/Planning-Wisely/${repository}/issues?state=open&direction=asc&page=$page&per_page=50")
    .build().run {
        perform {
            check(it.code == 200) {
                "An error occurred while getting issues of repository ${repository}, code: ${it.code}"
            }.let { _ -> it.body?.string() ?: "{}" }
        }
    }.fixJsonArray()

