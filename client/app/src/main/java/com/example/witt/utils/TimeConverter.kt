package com.example.witt.utils

import android.text.format.DateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun Long.convertTimestampToDate(): String =
    DateFormat.format("yyyy.MM.dd", this).toString()

fun String.convertIsoToDate(): String =
    LocalDateTime.parse(this, DateTimeFormatter.ISO_DATE_TIME)
        .format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))

fun String.convertIsoToTime(): String =
    LocalDateTime.parse(this, DateTimeFormatter.ISO_DATE_TIME)
        .format(DateTimeFormatter.ofPattern("a HH:mm"))
