package com.vince.colombianclock.utils

import com.vince.colombianclock.Time
import com.vince.colombianclock.TimeType
import com.vince.colombianclock.TimeType.DOCE
import com.vince.colombianclock.TimeType.EN_PUNTO
import com.vince.colombianclock.TimeType.FALTAN
import com.vince.colombianclock.TimeType.NORMAL

fun Time.toTimeType(): TimeType =
    when {
        minutes == 0 && (hour == 12 || hour == 0) -> DOCE
        minutes == 0 -> EN_PUNTO
        minutes >= 40 -> FALTAN
        else -> NORMAL
    }

fun Int.toHourText() = when (this) {
    0 -> "doce"
    1, 13 -> "una"
    2, 14 -> "dos"
    3, 15 -> "tres"
    4, 16 -> "cuatro"
    5, 17 -> "cinco"
    6, 18 -> "seis"
    7, 19 -> "siete"
    8, 20 -> "ocho"
    9, 21 -> "nueve"
    10, 22 -> "diez"
    11, 23 -> "once"
    12 -> "doce"
    else -> throw Exception("No está mapeada esta opción: $this")
}

fun Int.toMinuteText(): String = when (this) {
    5 -> "cinco"
    10 -> "diez"
    15 -> "cuarto"
    20 -> "veinte"
    25 -> "veinticinco"
    30 -> "media"
    35 -> "treinta y cinco"
    else -> throw Exception("No está mapeada esta opción: $this")
}

fun Int.toMinutesQueFaltanText(): String = when (this) {
    40 -> "veinte"
    45 -> "cuarto"
    50 -> "diez"
    55 -> "cinco"
    else -> throw Exception("No está mapeada esta opción: $this")
}