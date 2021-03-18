package com.michaelflisar.materialpreferences.preferencescreen.input.string

import android.view.LayoutInflater
import android.view.ViewGroup
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.input.input
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.viewholders.base.BaseDialogViewHolder

class InputStringViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
) : BaseDialogViewHolder<String, InputStringPreference>(inflater, parent) {

    override fun showDialog(preference: InputStringPreference) {
        val dialog = if (preference.bottomSheet) MaterialDialog(itemView.context, BottomSheet()) else MaterialDialog(itemView.context)
        dialog.show {
            title(text = preference.title.get(itemView.context))
            input(
                    hint = preference.hint.get(context),
                    prefill = value,
                    inputType = preference.textInputType,
                    allowEmpty = preference.allowEmpty
            ) { dialog, text ->
                update(text.toString(), preference)
            }
            positiveButton(android.R.string.ok)
        }.show()
    }
}