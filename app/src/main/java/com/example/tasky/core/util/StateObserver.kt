package com.example.tasky.core.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext

@Composable
fun ObserveError(errorMessage: String?, onErrorShown: () -> Unit) {
    val context = LocalContext.current
    LaunchedEffect(key1 = errorMessage) {
        if (errorMessage != null) {
            makeToast(context, errorMessage)
            onErrorShown()
        }
    }
}

@Composable
fun ObserveBoolean(observer: Boolean?, onTriggered: () -> Unit) {
    LaunchedEffect(key1 = observer) {
        if (observer == true) {
            onTriggered()
        }
    }
}

@Composable
fun <T> Observe(observer: T?, onTriggered: (T) -> Unit) {
    LaunchedEffect(key1 = observer) {
        if (observer != null) {
            onTriggered(observer)
        }
    }
}