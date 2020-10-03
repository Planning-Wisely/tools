package com.planningwisely.branchtool

import com.planningwisely.branchtool.enums.IssueType
import com.planningwisely.branchtool.github.abst.IGitHubApiWrapper
import com.planningwisely.branchtool.helpers.incorrect
import com.planningwisely.branchtool.koin.initializeDi
import com.planningwisely.branchtool.models.IssuesModel
import com.planningwisely.branchtool.models.RepositoriesModel
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.awt.Toolkit.getDefaultToolkit
import java.awt.datatransfer.StringSelection
import kotlin.system.exitProcess

object EntryPoint : KoinComponent {
    private var owner = "Planning-Wisely"
    private val github by inject<IGitHubApiWrapper>()

    @JvmStatic
    fun main(args: Array<String>) {
        processArguments(args)
        initializeDi()
        printWelcomeMessage()
        with(github.getRepositories<RepositoriesModel>(owner)) {
            printSelectMessage()
            printRepositories(this)
            handleSelect(parseSelect(this), this)
            exitCondition(this)
        }
    }

    private fun processArguments(args: Array<String>) = args.map {
        it.split("=").let { pairs -> pairs.first() to pairs.last() }
    }.also { processRepoOwner(it) }

    private fun processRepoOwner(args: List<Pair<String, String>>) = args.find {
        it.first.equals("owner", true)
    }?.also { owner = it.second }.let { Unit }

    private fun printWelcomeMessage() {
        println("Branch naming tool for Planning Wisely project, 1.0.0-SNAPSHOT.1\n")
        println("Fetching repositories from origin ...")
    }

    private fun printRepositories(repositories: RepositoriesModel) {
        with(repositories) {
            root.joinToString(",\n", postfix = "\n") { repository ->
                val repositoryIndex = root.indexOf(root.find { it.name == repository.name }) + 1
                val issuesCount = repository.openIssuesCount
                val originUrl = repository.htmlUrl
                "   > ${repositoryIndex}. ${repository.name} (I: $issuesCount, U: $originUrl)"
            }
        }.also(::println)
    }

    private fun parseSelect(repositories: RepositoriesModel) =
        readLine()!!.split(",").let {
            listOf(it.first().toIntOrNull(), if (it.count() == 1) 1 else it.last().toIntOrNull())
        }.map { it ?: 1 }.let {
            val (first, last) = it.first() to it.last()
            Pair(
                check(first >= 1 && first <= repositories.root.count()) {
                    incorrect(first, repositories.root.count()) { "repository number" }
                }.let { first },
                check(last >= 1) { incorrect(last, "great than 0") { "page number" } }.let { last }
            )
        }

    private fun handleSelect(repository: Pair<Int, Int>, repositories: RepositoriesModel) {
        val issues = github.getIssues<IssuesModel>(
            owner, repositories.root[repository.first - 1].name, repository.second
        )

        println("Select a issue number you want to get issue branch name: ")

        with(issues) {
            root.joinToString(",\n", postfix = "\n") { issue ->
                "   > ${issue.number}. ${issue.title}, (U: ${issue.htmlUrl})"
            }
        }.also(::println)

        val selected = readLine()!!.toIntOrNull()?.let { selected ->
            check(selected >= 1) {
                incorrect(selected, "no more than ${issues.root.count()}") { "issue id" }
            }.let { selected }
        } ?: 1

        println("Type `f` if that issue is feature or `b` if that issues is bug, default type is `f`\n")

        val issueRawType = readLine()!!
        val type =
            IssueType.values().find { it.flow.equals(issueRawType, true) } ?: IssueType.Feature
        val issue = issues.root.find { it.number == selected }

        with(issue?.title?.toLowerCase()?.replace(" ", "-")) {
            val new = type.flow + "${issue?.number ?: "???"}-" + this
            val selection = StringSelection(new)
            getDefaultToolkit().systemClipboard.setContents(selection, selection)
            println("`$new` branch name copied to clipboard")
        }
    }

    private fun exitCondition(repositories: RepositoriesModel) {
        println("Type `exit` for exit or `again` for see repository issues again\n")
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

    private fun printSelectMessage() {
        println("Select a repository number you want to see issues and page of its separate via `,` (by default 1): ")
        println(" ** Remarks: I - Repository issues count, U - Url of repository\n")
    }
}
