package com.michaelflisar.materialpreferences.preferencescreen.color

import android.app.Dialog
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.ImageViewCompat
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.color.ColorPalette
import com.afollestad.materialdialogs.color.colorChooser
import com.michaelflisar.materialpreferences.preferencescreen.color.databinding.WidgetColorBinding
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.PreferenceAdapter
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.viewholders.base.BaseDialogViewHolder
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.viewholders.base.BaseViewHolderWidget

class ColorViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        override val adapter: PreferenceAdapter
) : BaseDialogViewHolder<Int, ColorPreference, WidgetColorBinding>(inflater, parent)  {

    override fun createSubBinding(inflater: LayoutInflater, parent: ViewGroup, attachToParent: Boolean) =
            WidgetColorBinding.inflate(inflater, parent, true)

    override fun bindWidget(preference: ColorPreference, rebind: Boolean) {
        ImageViewCompat.setImageTintList(subBinding.color, ColorStateList.valueOf(value))
    }

    override fun createDialog(preference: ColorPreference): Dialog {
        val colors = ColorPalette.Primary
        val subColors = ColorPalette.PrimarySub
        val dialog = if (preference.bottomSheet) MaterialDialog(itemView.context, BottomSheet()) else MaterialDialog(itemView.context)
        dialog.show {
            title(text = preference.title.get(itemView.context))
            colorChooser(
                    initialSelection = value,
                    colors = colors,
                    subColors = subColors,
                    allowCustomArgb = true,
                    showAlphaSelector = preference.supportsAlpha
            ) { dialog, color ->
                update(color, preference)
            }
            positiveButton(android.R.string.ok)
        }
        return dialog
    }
}