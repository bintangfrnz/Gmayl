package com.bintangfajarianto.gmayl.extension

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

val MONTHS = listOf(
    "Januari", "Februari", "Maret", "April", "Mei", "Juni",
    "Juli", "Agustus", "September", "Oktober", "November", "Desember",
)

enum class StringDateFormat {
    MESSAGE_ITEM_DATE,
}

fun LocalDate.format(format: StringDateFormat): String =
    when (format) {
        StringDateFormat.MESSAGE_ITEM_DATE -> "${MONTHS[monthNumber - 1].take(3)} ${year.toString().takeLast(2)}"
    }

fun Instant.getLocalDate(): LocalDate = toLocalDateTime(TIME_ZONE_ID).date

fun String.handleColon(): String = replace("[+-]\\d{4}\$".toRegex()) {
    StringBuilder(it.value).insert(3, ':')
}

fun String.parseIsoInstant(handleColon: Boolean = true): Instant? =
    try {
        Instant.parse(if (handleColon) handleColon() else this)
    } catch (ex: Throwable) {
        null
    }

private val TIME_ZONE_ID = TimeZone.currentSystemDefault()
