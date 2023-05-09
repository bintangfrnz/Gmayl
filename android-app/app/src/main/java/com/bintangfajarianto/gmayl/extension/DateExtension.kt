package com.bintangfajarianto.gmayl.extension

import java.util.*
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

enum class StringDateFormat {
    MESSAGE_ITEM_DATE,
    FULL_DATE,
}

fun LocalDate.format(format: StringDateFormat): String =
    when (format) {
        StringDateFormat.MESSAGE_ITEM_DATE -> "${month.capitalize().take(3)} $dayOfMonth"
        StringDateFormat.FULL_DATE -> "${dayOfWeek.capitalize()}, ${month.capitalize()} $dayOfMonth, $year"
    }

private fun Month.capitalize(): String =
    this.toString().lowercase().replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
    }

private fun DayOfWeek.capitalize(): String =
    this.toString().lowercase().replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
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
