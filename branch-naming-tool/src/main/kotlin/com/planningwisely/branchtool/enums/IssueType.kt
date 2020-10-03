package com.planningwisely.branchtool.enums

/**
 * Type of issue represented in gitflow format.
 * @param flow git flow mark.
 */
enum class IssueType(val flow: String) { Feature("f/"), Bug("b/") }
