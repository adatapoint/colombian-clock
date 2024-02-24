package com.vince.colombianclock

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val randomTimes = generateRandomTimes(300)
        randomTimes.forEach { hour ->
            getTranslatedTime(hour)
        }
    }

    private fun getTranslatedTime(time: Time) {
        val translatedTime = getBeginning(time) +
                " ${getHour(time)}" +
                " ${getMinutes(time)}" +
                " ${getEnding(time)}"
        Log.d("TAG", "$time -> $translatedTime")
    }

    private fun getEnding(time: Time): String = when {
        (time.hour == 0 || time.hour == 12) && time.minutes == 0 -> ""
        time.hour < 12 -> "de la mañana"
        time.hour > 12 -> "de la noche"
        else -> throw Exception("No está mapeada esta opción $time")
    }

    private fun getHour(time: Time): String {
        val adjustedMinute = when (time.minutes) {
            in 35..40 -> {
                if (time.minutes - 35 <= 40 - time.minutes) {
                    35
                } else {
                    40
                }
            }

            in 0..5 -> {
                if (time.minutes <= 5 - time.minutes) {
                    0
                } else {
                    5
                }
            }

            else -> time.minutes
        }
        return when {
            adjustedMinute >= 40 -> ""
            time.hour == 0 && adjustedMinute == 0 -> "media noche"
            time.hour == 12 -> "medio día"
            time.hour in 0..11 -> when (time.hour) {
                0 -> "doce"
                1 -> "una"
                2 -> "dos"
                3 -> "tres"
                4 -> "cuatro"
                5 -> "cinco"
                6 -> "seis"
                7 -> "siete"
                8 -> "ocho"
                9 -> "nueve"
                10 -> "diez"
                11 -> "once"
                else -> throw Exception("No está mapeada esta opción minuto: $adjustedMinute")
            }

            else -> throw Exception("No está mapeada esta opción $time")
        }
    }

    private fun getRoundedMinutes(minute: Int): Int {
        val remainder = minute % 5
        val adjustedMinute = if (remainder < 3) {
            minute - remainder
        } else {
            minute + (5 - remainder)
        }
        return if (adjustedMinute > 58) {
            0
        } else {
            adjustedMinute
        }
    }

    private fun getMinutes(time: Time): String =
        if ((time.hour == 0 || time.hour == 12) && time.minutes == 0) {
            ""
        } else {
            when (getRoundedMinutes(time.minutes)) {
                0 -> "en punto"
                5 -> "y cinco"
                10 -> "y diez"
                15 -> "y cuarto"
                20 -> "y veinte"
                25 -> "y veinticinco"
                30 -> "y media"
                35 -> "y treinta y cinco"
                40 -> "veinte para"
                45 -> "cuarto para"
                50 -> "diez para"
                55 -> "cinco para"
                else -> throw Exception("No está mapeada esta opción $time")
            }
        }

    private fun generateRandomTimes(count: Int): List<Time> {
        val randomTimes = mutableListOf<Time>()
        repeat(count) {
            val hour = Random.nextInt(0, 24)
            val minute = Random.nextInt(0, 60)
            randomTimes.add(Time(hour, minute))
        }
        return randomTimes
    }

    private fun getBeginning(time: Time): String {
        val adjustedMinute = when (time.minutes) {
            in 35..40 -> {
                if (time.minutes - 35 <= 40 - time.minutes) {
                    35
                } else {
                    40
                }
            }

            in 0..5 -> {
                if (time.minutes <= 5 - time.minutes) {
                    0
                } else {
                    5
                }
            }

            else -> time.minutes
        }
        return when {
            adjustedMinute == 45 -> "falta"
            adjustedMinute >= 40 -> "faltan"
            time.hour == 0 && adjustedMinute == 0 -> "es"
            time.hour == 0 -> "son las"
            time.hour == 1 -> "es la"
            time.hour == 12 -> "es"
            time.hour >= 2 -> "son las"
            else -> throw Exception("No está mapeada esta opción $time")
        }
    }

    data class Time(val hour: Int, val minutes: Int)
    private fun Time.toString() = "$hour:$minutes"
}

