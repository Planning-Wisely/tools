import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import models.IssuesModel
import models.RepositoriesModel

/**
 * @return repositories of organization `Planning Wisely`
 * represented as [RepositoriesModel].
 */
fun getRepositories() =
    Json { ignoreUnknownKeys = true }.decodeFromString<RepositoriesModel>(fetchRepositories())

/**
 * @param page page of repositories, one page contains 50 repositories.
 * @param repository repository to see issues.
 * @return issues of [repository] repository represented as [IssuesModel].
 */
fun getIssues(
    page: Int, repository: String
) = Json { ignoreUnknownKeys = true }.decodeFromString<IssuesModel>(fetchIssues(page, repository))

