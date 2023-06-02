package kz.nearbygems.chat.util

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.Test

class ExtensionsTest {

    @Test
    fun `function isCommand returns false for nullable`() {
        assertThat(null.isCommand()).isFalse
    }

    @ParameterizedTest
    @ValueSource(strings = ["/login", "/join", "/leave", "/disconnect", "/list", "/users"])
    fun `function isCommand returns true`(candidate: String) {
        assertThat(candidate.isCommand()).isTrue
    }

    @ParameterizedTest
    @ValueSource(strings = ["//login", "join", "message", "dis/connect", ""])
    fun `function isCommand returns false`(candidate: String) {
        assertThat(candidate.isCommand()).isFalse
    }

    @TestFactory
    fun firstWord() = listOf(
        "/login <name> <password>" to "/login",
        "/join <channel>" to "/join",
        "/leave" to "/leave")
        .map { (input, expected) ->
            dynamicTest("first word of $input is $expected") {
                assertThat(input.firstWord()).isEqualTo(expected)
            }
        }

    @TestFactory
    fun message() = listOf(
        "/login <name> <password>" to "<name> <password>",
        "/join <channel>" to "<channel>",
        "message" to "message")
        .map { (input, expected) ->
            dynamicTest("message of $input is $expected") {
                assertThat(input.message()).isEqualTo(expected)
            }
        }

}