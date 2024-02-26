package com.vince.colombianclock.utils

import com.vince.colombianclock.Time
import com.vince.colombianclock.TimeType.DOCE
import com.vince.colombianclock.TimeType.EN_PUNTO
import com.vince.colombianclock.TimeType.FALTAN
import com.vince.colombianclock.TimeType.NORMAL

fun getStructure(time: Time): List<String> {

    val type = time.toTimeType()

    val translatedTimeParts = mutableListOf<String>()

    when (type) {
        FALTAN -> {
            // Faltan veinte para las doce
            // Falta un cuarto para las cuatro
            with(translatedTimeParts) {
                add("faltan")
                add("minutos_para_siguiente_hora")
                add("para")
                add("articulo")
                add("siguiente_hora")
            }
        }

        EN_PUNTO -> {
            // Son las tres en punto
            with(translatedTimeParts) {
                add("es_son")
                add("articulo")
                add("hora")
                add("dia_noche")
                add("en_punto")
            }
        }

        NORMAL -> {
            // Es la una y treinta y cinco de la mañana
            // Son las cuatro y diez de la tarde
            with(translatedTimeParts) {
                add("es_son")
                add("articulo")
                add("hora")
                add("y")
                add("minutos")
                add("dia_noche")
            }
        }

        DOCE -> {
            // Es media noche
            with(translatedTimeParts) {
                add("es_media_noche_o_medio_dia")
            }
        }
    }
    return translatedTimeParts.toList()
}

fun getRectifiedMinute(minute: Int): Int {
    val residue = minute % 5
    // Si el residuo es menor o igual a 2, acercamos al múltiplo de 5 inferior
    return if (residue <= 2) {
        minute - residue
    } else {
        // Si el residuo es mayor a 2, acercamos al múltiplo de 5 superior
        minute + (5 - residue)
    }
}
