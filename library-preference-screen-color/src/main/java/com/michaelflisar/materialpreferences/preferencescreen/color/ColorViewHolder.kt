package com.michaelflisar.materialpreferences.preferencescreen.color

import android.view.LayoutInflater
import android.view.ViewGroup
import com.michaelflisar.dialogs.DialogColor
import com.michaelflisar.dialogs.drawables.DrawableCheckerBoard
import com.michaelflisar.dialogs.interfaces.IMaterialDialogEvent
import com.michaelflisar.materialpreferences.preferencescreen.DialogExtra
import com.michaelflisar.materialpreferences.preferencescreen.color.databinding.WidgetColorBinding
import com.michaelflisar.materialpreferences.preferencescreen.interfaces.PreferenceItem
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.PreferenceAdapter
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.viewholders.base.BaseDialogViewHolder
import com.michaelflisar.text.asText

class ColorViewHolder(
    inflater: LayoutInflater,
    parent: ViewGroup,
    override val adapter: PreferenceAdapter
) : BaseDialogViewHolder<Int, ColorPreference, WidgetColorBinding>(inflater, parent) {

    override fun createSubBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        attachToParent: Boolean
    ) =
        WidgetColorBinding.inflate(inflater, parent, true).apply {
            vCheckerBackground.background = DrawableCheckerBoard()
        }

    override fun bindWidget(preference: ColorPreference, rebind: Boolean) {
        subBinding.vSelectedColor.setBackgroundColor(value)
    }

    override fun onDialogResultAvailable(
        preference: PreferenceItem.Preference,
        event: IMaterialDialogEvent
    ) {
        event as DialogColor.Event
        preference as ColorPreference
        if (event is DialogColor.Event.Result) {
            update(event.color, preference)
        }
    }

    override fun createDialog(preference: ColorPreference) {
        val dlg = DialogColor(
            -1,
            title = preference.title,
            color = value,
            alphaAllowed = preference.supportsAlpha,
            buttonPositive = android.R.string.ok.asText(),
            extra = DialogExtra(preference.setting.key)
        )
        adapter.showDialog(preference, dlg)
    }
}