package enums

/**
 * Type of issue, now contains just two types.
 * @param flow git flow mark.
 */
enum class IssueType(val flow: String) { Feature("f/"), Bug("b/") }
