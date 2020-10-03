package com.planningwisely.branchtool.koin

import com.planningwisely.branchtool.github.abst.IGitHubApiWrapper
import com.planningwisely.branchtool.github.abst.IGitHubRequest
import com.planningwisely.branchtool.github.impl.GitHubApiWrapper
import com.planningwisely.branchtool.github.impl.GitHubRequest
import org.koin.core.context.startKoin
import org.koin.dsl.module

private val modules = module {
    single<IGitHubRequest> { GitHubRequest() }
    single<IGitHubApiWrapper> { GitHubApiWrapper() }
}

/**
 * Does initializing Dependency Injection container.
 */
fun initializeDi() = startKoin { modules(modules) }.let { Unit }
