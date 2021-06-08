package com.codetron.dogs.utils

import android.view.View

fun View.visibleIf(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}