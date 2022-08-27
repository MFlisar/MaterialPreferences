package com.michaelflisar.materialpreferences.preferencescreen

import android.view.View
import android.widget.TextView
import com.michaelflisar.text.Text

object ScreenUtil {
    fun display(text: Text, textView: TextView, visibilityEmpty: Int, vararg args: Any?) {
        if (args.isNotEmpty())
            textView.text = text.getString(textView.context, *args)
        else
            text.display(textView)
        if (textView.text.isEmpty())
            textView.visibility = visibilityEmpty
        else
            textView.visibility = View.VISIBLE
    }
}