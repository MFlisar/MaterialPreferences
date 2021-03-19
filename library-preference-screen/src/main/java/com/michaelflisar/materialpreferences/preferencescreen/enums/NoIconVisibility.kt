package com.michaelflisar.materialpreferences.preferencescreen.enums

import android.view.View

enum class NoIconVisibility(val visibility: Int) {
    Invisible(View.INVISIBLE),
    Gone(View.GONE)
}