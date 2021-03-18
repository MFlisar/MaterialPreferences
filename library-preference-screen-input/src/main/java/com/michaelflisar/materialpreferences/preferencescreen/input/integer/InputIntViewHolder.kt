package com.michaelflisar.materialpreferences.preferencescreen.input.integer

import android.text.InputType
import android.view.LayoutInflater
import android.view.ViewGroup
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.input.input
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.viewholders.base.BaseDialogViewHolder

class InputIntViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
) : BaseDialogViewHolder<Int, InputIntPreference>(inflater, parent) {

    override fun showDialog(preference: InputIntPreference) {
        val dialog = if (preference.bottomSheet) MaterialDialog(itemView.context, BottomSheet()) else MaterialDialog(itemView.context)
        dialog.show {
            title(text = preference.title.get(itemView.context))
            input(
                    hint = preference.hint.get(context),
                    prefill = value.toString(),
                    inputType = InputType.TYPE_CLASS_NUMBER,
                    allowEmpty = false
            ) { dialog, text ->
                update(text.toString().toIntOrNull() ?: preference.defaultValue, preference)
            }
            positiveButton(android.R.string.ok)
        }.show()
    }
}