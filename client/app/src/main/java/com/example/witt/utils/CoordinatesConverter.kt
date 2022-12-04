package com.example.witt.utils

fun String.convertCoordinates(): Pair<Double, Double> =
    when (this) {
        "부산/경상" -> Pair(35.17764796388484, 129.07131590718737)
        "대전/충청" -> Pair(36.35044767058149, 127.38517974898343)
        "전주/전라" -> Pair(35.82140206649375, 127.14794186216459)
        "강릉/강원" -> Pair(37.75103008051646, 128.87413171003212)
        "제주/우도" -> Pair(33.49947647780527, 126.53067852436659)
        else -> Pair(37.56004796368131, 126.97486265175372) // 서울 시청
    }
