import enums.IssueType
import models.RepositoriesModel
import java.awt.Toolkit.getDefaultToolkit
import java.awt.datatransfer.StringSelection
import kotlin.system.exitProcess

fun main() {
    println("Branch naming tool for Planning Wisely project, 1.0.0-SNAPSHOT.1\n")
    println("Fetching repositories from origin ...")
    with(getRepositories()) {
        printSelectMessage()
        printRepositories(this)
        handleSelect(parseSelect(this), this)
        exitCondition(this)
    }
}

fun printSelectMessage() {
    println("Select a repository number you want to see issues and page of its separate via `,` (by default 1): ")
    println(" ** Remarks: I - Repository issues count, U - Url of repository\n")
}

fun printRepositories(repositories: RepositoriesModel) {
    with(repositories) {
        entry.joinToString(",\n") { repository ->
            "   > ${entry.indexOf(entry.find { it.name == repository.name }) + 1}. ${repository.name} (I: ${repository.openIssuesCount}, U: ${repository.url})"
        }
    }.also(::println)
}

fun parseSelect(repositories: RepositoriesModel) =
    readLine()!!.split(",").let {
        listOf(it.first().toIntOrNull(), if (it.count() == 1) 1 else it.last().toIntOrNull())
    }.map { it ?: 1 }.let {
        val (first, last) = it.first() to it.last()
        Pair(
            check(first >= 1 && first <= repositories.entry.count()) {
                incorrect(first, repositories.entry.count()) { "repository number" }
            }.let { first },
            check(last >= 1) { incorrect(last, "great than 0") { "page number" } }.let { last }
        )
    }

fun handleSelect(repository: Pair<Int, Int>, repositories: RepositoriesModel) {
    val issues = getIssues(repository.second, repositories.entry[repository.first - 1].name)
    println("Select a issue number you want to get issue branch name: ")

    with(issues) {
        entry.joinToString(",\n") { issue ->
            "   > ${issue.number}. ${issue.title}, (U: ${issue.url})"
        }
    }.also(::println)

    val selected = readLine()!!.toIntOrNull()?.let { selected ->
        check(selected >= 1) {
            incorrect(selected, "no more than ${issues.entry.count()}") { "issue id" }
        }.let { selected }
    } ?: 1

    println("Type `f` if that issue is feature or `b` if that issues is bug, default type is `f`")

    val issueRawType = readLine()!!
    val type = IssueType.values().find { it.flow.equals(issueRawType, true) } ?: IssueType.Feature
    val issue = issues.entry.find { it.number == selected }

    with(issue?.title?.toLowerCase()?.replace(" ", "-")) {
        val new = type.flow + "${issue?.number ?: "???"}-" + this
        val selection = StringSelection(new)
        getDefaultToolkit().systemClipboard.setContents(selection, selection)
        println("`$new` branch name copied to clipboard")
    }
}


fun exitCondition(repositories: RepositoriesModel) {
    println("Type `exit` for exit or `again` for see repository issues again")
    when (readLine()) {
        "exit" -> exitProcess(0)
        "again" -> {
            printSelectMessage()
            printRepositories(repositories)
            handleSelect(parseSelect(repositories), repositories)
            exitCondition(repositories)
        }
    }
}
