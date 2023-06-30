package com.example.tasky.itemdetail.presentation.model

import androidx.annotation.StringRes
import com.example.tasky.R

enum class VisitorType(@StringRes val value: Int) {
    ALL(value = R.string.all),
    GOING(value = R.string.going),
    NOT_GOING(value = R.string.not_going)
}
