package com.example.tasky.core.util

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

fun makeToast(context: Context, text: String) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}

fun makeToast(context: Context, @StringRes stringRes: Int) {
    Toast.makeText(context, stringRes, Toast.LENGTH_SHORT).show()
}