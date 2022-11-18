package com.example.witt.utils

import android.text.format.DateFormat

fun Long.convertTimestampToDate(): String =
    DateFormat.format("yyyy.MM.dd", this).toString()