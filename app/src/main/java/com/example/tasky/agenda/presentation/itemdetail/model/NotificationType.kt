@file:SuppressLint("NewApi")

package com.example.tasky.agenda.presentation.itemdetail.model

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import com.example.tasky.R
import com.example.tasky.agenda.domain.util.toLong
import java.time.Duration
import java.time.LocalDateTime
import kotlin.math.abs

enum class NotificationType(@StringRes val type: Int) {
    TEN_MINUTES(R.string.ten_minutes_before),
    THIRTY_MINUTES(R.string.thirty_minutes_before),
    ONE_HOUR(R.string.one_hour_before),
    SIX_HOURS(R.string.six_hours_before),
    ONE_DAY(R.string.one_day_before);

    companion object {
        fun from(dateTime: LocalDateTime, notificationTime: LocalDateTime): NotificationType {
            val difference = Duration.between(dateTime, notificationTime)
            if (abs(difference.toDays()) == 1L) {
                return ONE_DAY
            }
            if (abs(difference.toHours()) == 6L) {
                return SIX_HOURS
            }
            if (abs(difference.toHours()) == 1L) {
                return ONE_HOUR
            }
            if (abs(difference.toMinutes()) == 30L) {
                return THIRTY_MINUTES
            }
            return TEN_MINUTES
        }

        fun remindAt(time: LocalDateTime, notificationType: NotificationType): Long {
            return when (notificationType) {
                TEN_MINUTES -> time.minusMinutes(10)
                THIRTY_MINUTES -> time.minusMinutes(30)
                ONE_HOUR -> time.minusHours(1)
                SIX_HOURS -> time.minusHours(6)
                ONE_DAY -> time.minusDays(1)
            }.toLong()
        }
    }
}