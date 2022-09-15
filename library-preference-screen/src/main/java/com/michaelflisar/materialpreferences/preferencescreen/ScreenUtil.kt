package com.michaelflisar.materialpreferences.preferencescreen

import android.view.View
import android.widget.TextView
import com.michaelflisar.materialpreferences.preferencescreen.interfaces.PreferenceItem
import com.michaelflisar.materialpreferences.preferencescreen.preferences.SubScreen
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

    internal fun flatten(preferences: List<PreferenceItem>): List<PreferenceItem> {
        val flattened = ArrayList<PreferenceItem>()
        preferences.forEach {
            if (it is SubScreen) {
                flattened += it
                flattened += flatten(it.preferences)
            } else {
                flattened += it
            }
        }
        return flattened
    }
}