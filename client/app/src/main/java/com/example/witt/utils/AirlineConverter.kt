package com.example.witt.utils

fun String.convertAirline(): String {
    return when (this) {
        "대한항공(KE)" -> "KE"
        "아시아나항공(OZ)" -> "OZ"
        "제주항공(7C)" -> "7C"
        "진에어(LJ)" -> "LJ"
        "티웨이항공(TW)" -> "TW"
        "에어서울(RS)" -> "RS"
        "에어부산(BX)" -> "BX"
        "플라이강원(4V)" -> "4V"
        else -> ""
    }
}
