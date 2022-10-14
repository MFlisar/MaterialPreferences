package com.michaelflisar.materialpreferences.preferencescreen.input.number.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.michaelflisar.dialogs.DialogInput
import com.michaelflisar.materialpreferences.preferencescreen.DialogExtra
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.PreferenceAdapter
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.viewholders.base.BaseDialogViewHolder
import com.michaelflisar.text.asText

abstract class InputNumberViewHolder<T : Number, P : InputNumberPreference<T>>(
    inflater: LayoutInflater,
    parent: ViewGroup,
    override val adapter: PreferenceAdapter
) : BaseDialogViewHolder<T, P, ViewBinding?>(inflater, parent) {

    abstract val dialogInputType: Int

    override fun createSubBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        attachToParent: Boolean
    ): ViewBinding? = null

    override fun createDialog(preference: P) {
        val dlg = DialogInput(
            -1,
            title = preference.title,
            input = DialogInput.Input.Single(
                value = value.toString().asText(),
                hint = preference.hint,
                inputType = dialogInputType,
                validator = DialogInput.NumberValidator(preference.min, preference.max)
            ),
            selectAllOnFocus = preference.selectAllOnFocus,
            buttonPositive = android.R.string.ok.asText(),
            extra = DialogExtra(preference.setting.key)
        )
        adapter.showDialog(preference, dlg)
    }
}