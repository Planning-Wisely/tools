inline fun incorrect(actual: Any, expected: Any, what: () -> String) = println(
    "Incorrect ${what()}, expected $expected but your input is $actual"
)
