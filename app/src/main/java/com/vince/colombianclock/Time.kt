package com.vince.colombianclock

import com.vince.colombianclock.utils.getRectifiedMinute

data class Time(var hour: Int, var minutes: Int) {
    init {
        if (minutes > 57) {
            minutes = 0
            if (hour == 23) {
                hour = 0
            } else {
                hour++
            }
        } else {
            minutes = getRectifiedMinute(minutes)
        }
    }
}
