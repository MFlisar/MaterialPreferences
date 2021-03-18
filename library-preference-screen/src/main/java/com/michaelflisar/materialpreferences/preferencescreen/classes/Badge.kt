package com.michaelflisar.materialpreferences.preferencescreen.classes

import android.view.View
import android.widget.TextView
import com.michaelflisar.text.asText

sealed class Badge {

    class Text(val text: com.michaelflisar.text.Text) : Badge()
    object Empty : Badge()

    fun display(tv: TextView) {
        when (this) {
            Empty -> {
                tv.text = ""
                tv.visibility = View.GONE
            }
            is Text -> {
                text.display(tv)
                tv.visibility = View.VISIBLE
            }
        }
    }
}

fun Int.asBatch(): Badge {
    return Badge.Text(this.asText())
}

fun String.asBatch(): Badge {
    return Badge.Text(this.asText())
}
