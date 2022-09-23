package com.michaelflisar.materialpreferences.preferencescreen.recyclerview.viewholders.base

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.viewbinding.ViewBinding
import com.michaelflisar.materialpreferences.preferencescreen.databinding.PreferenceWithWidgetLargeBinding
import com.michaelflisar.materialpreferences.preferencescreen.interfaces.PreferenceItem

abstract class BaseViewHolderWidgetLarge<D : Any, T, B : ViewBinding?>(
    inflater: LayoutInflater,
    parent: ViewGroup
) : BaseViewHolderWithWidget<D, T, B, PreferenceWithWidgetLargeBinding>(
    inflater,
    PreferenceWithWidgetLargeBinding.inflate(inflater, parent, false)
) where T : PreferenceItem.PreferenceWithData<D>, T : PreferenceItem.Preference {

    override val widgetFrame: LinearLayout = binding.widgetFrame
    override val iconFrame: LinearLayout = binding.iconFrame
    override val title: TextView = binding.title
    override val summary: TextView = binding.summary
    override val icon: ImageView = binding.icon
    override val badge: TextView = binding.badge

    init {
        init()
    }
}