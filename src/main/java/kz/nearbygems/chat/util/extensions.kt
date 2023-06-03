package kz.nearbygems.chat.util

fun String?.splitBySpace() = this?.split("\\s+".toRegex(), 2)

fun String?.isCommand() = this?.let { "/[^/].*".toRegex().matches(it) } ?: false

fun String?.firstWord() = this?.splitBySpace()?.getOrNull(0)

fun String?.message() = this?.let {
    if (it.isCommand())
        it.splitBySpace()?.getOrNull(1)
    else
        this
}