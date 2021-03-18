package com.michaelflisar.materialpreferences.preferencescreen.input.string

import android.app.Dialog
import android.text.InputType
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.input.input
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.PreferenceAdapter
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.viewholders.base.BaseDialogViewHolder

class InputStringViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        override val adapter: PreferenceAdapter
) : BaseDialogViewHolder<String, InputStringPreference, ViewBinding?>(inflater, parent) {

    override fun createSubBinding(inflater: LayoutInflater, parent: ViewGroup, attachToParent: Boolean): ViewBinding? = null

    override fun createDialog(preference: InputStringPreference) : Dialog {
        val dialog = if (preference.bottomSheet) MaterialDialog(itemView.context, BottomSheet()) else MaterialDialog(itemView.context)
        dialog.show {
            title(text = preference.title.get(itemView.context))
            input(
                    hint = preference.hint.get(context),
                    prefill = value,
                    inputType = preference.textInputType ?: InputType.TYPE_CLASS_TEXT,
                    allowEmpty = preference.allowEmpty
            ) { dialog, text ->
                update(text.toString(), preference)
            }
            positiveButton(android.R.string.ok)
        }
        return dialog
    }
}