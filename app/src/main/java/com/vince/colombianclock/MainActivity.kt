package com.vince.colombianclock

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.vince.colombianclock.data.getAllTimes
import com.vince.colombianclock.data.getWords
import com.vince.colombianclock.databinding.ActivityMainBinding
import com.vince.colombianclock.utils.getStructure
import com.vince.colombianclock.utils.toHourText
import com.vince.colombianclock.utils.toMinuteText
import com.vince.colombianclock.utils.toMinutesQueFaltanText

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val times = getAllTimes()
        // val times = generateRandomTimes(300)

        Log.d("", "Inicio")
        times.forEach {
            Log.d("", "${getTranslatedTime(it)}")
        }
        Log.d("", "Final")

        var currentIndex = 0

        fun iterateWithDelay() {
            if (currentIndex < times.size) {
                val currentTime = times[currentIndex]
                val translatedTime = getTranslatedTime(currentTime)
                binding.tvTime.text = String.format(
                    getString(R.string.time_format),
                    String.format("%02d", currentTime.hour),
                    String.format("%02d", currentTime.minutes)
                )
                binding.tvTimeText.text = translatedTime
                showTranslatedTimeInScreen(translatedTime)

                // Incrementar el índice para la siguiente iteración
                currentIndex++

                // Esperar x segundos antes de la siguiente iteración
                Handler(Looper.getMainLooper()).postDelayed({
                    iterateWithDelay()
                }, 10000)
            }
        }

        iterateWithDelay()
    }

    private fun restartUI() {
        binding.tvText.text = getWords().joinToString(separator = " ")
    }

    private fun showTranslatedTimeInScreen(translatedTime: String) {
        restartUI()
        val wordsToShow = translatedTime.split(" ")

        val text = binding.tvText.text.toString()
        val spannableString = SpannableString(text)
        for (word in wordsToShow) {
            val wordPattern = "\\b$word\\b".toRegex()
            val matches = wordPattern.findAll(text)

            for (matchResult in matches) {
                val startWord = matchResult.range.first
                val endWord = matchResult.range.last + 1
                spannableString.setSpan(
                    ForegroundColorSpan(Color.RED),
                    startWord,
                    endWord,
                    Spannable.SPAN_INCLUSIVE_INCLUSIVE
                )
            }
        }

        binding.tvText.text = spannableString
    }

    private fun getTranslatedTime(time: Time): String {
        val structure = getStructure(time)
        val translatedTime = getTimeTextFromStructure(time, structure)

        //Log.d("TAG", "${time.hour}:${time.minutes} -> ${translatedTime.toText()}")
        return translatedTime.toText()
    }

    private fun getTimeTextFromStructure(time: Time, structure: List<String>): List<String> {
        val timeText = mutableListOf<String>()
        structure.forEach { part ->
            timeText.add(
                when (part) {
                    "articulo" -> if (time.hour == 1 || time.hour == 13) "la" else "las"
                    "siguiente_hora" -> getNextHourText(time.hour)
                    "es_son" -> if (time.hour == 1 || time.hour == 13) "es" else "son"
                    "hora" -> time.hour.toHourText()
                    "en_punto" -> "en_punto"
                    "y" -> "y"
                    "minutos" -> time.minutes.toMinuteText()
                    "media_medio" -> if (time.hour == 12) "medio" else "media"
                    "dia_noche" -> "de la ${getMananaTardeOrNoche(time.hour)}"
                    "faltan" -> if (time.minutes == 45) "falta un" else "faltan"
                    "para" -> "para"
                    "es_media_noche_o_medio_dia" -> if (time.hour == 12) "es medio día" else "es media noche"
                    "minutos_para_siguiente_hora" -> time.minutes.toMinutesQueFaltanText()
                    else -> throw Exception("No está mapeada esta opción $part")
                }
            )
        }
        return timeText
    }

    private fun getNextHourText(hour: Int): String {
        var nextHour = hour + 1
        if (nextHour == 24) {
            nextHour = 0
        }
        return nextHour.toHourText()
    }

    private fun getMananaTardeOrNoche(hour: Int): String =
        when (hour) {
            in 0..11 -> "mañana"
            in 12..18 -> "tarde"
            else -> "noche"
        }

    private fun List<String>.toText(): String {
        var text = ""
        forEachIndexed { index, part ->
            text += part
            if (index != size - 1) {
                text = "$text "
            }
        }
        return text
    }
}
