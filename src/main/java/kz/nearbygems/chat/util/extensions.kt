package kz.nearbygems.chat.util

fun String?.isCommand() = this?.let { "/[^/].*".toRegex().matches(it) } ?: false

fun String?.firstWord() = this?.split("\\s+".toRegex(), 2)?.getOrNull(0)

fun String?.message() = this?.let {
    if (it.isCommand())
        it.split("\\s+".toRegex(), 2).getOrNull(1)
    else
        this
}