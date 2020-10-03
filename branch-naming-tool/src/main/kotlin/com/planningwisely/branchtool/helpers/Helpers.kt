package com.planningwisely.branchtool.helpers

import kotlinx.serialization.json.Json

/**
 * Prints message some things incorrect behavior happened.
 * @param actual any type object, it will converted to string.
 * @param expected like actual but will printed after `expected` word.
 * @param what what incorrect?
 */
inline fun incorrect(actual: Any, expected: Any, what: () -> String) = println(
    "Incorrect ${what()}, expected $expected but your input is $actual"
)

/**
 * @return stable configuration of Json instance
 * for correctly working.
 */
fun json() = Json { ignoreUnknownKeys = true }
