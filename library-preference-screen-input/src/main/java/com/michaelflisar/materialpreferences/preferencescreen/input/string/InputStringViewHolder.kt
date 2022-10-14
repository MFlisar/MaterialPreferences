package com.michaelflisar.materialpreferences.preferencescreen.input.string

import android.text.InputType
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.michaelflisar.dialogs.DialogInput
import com.michaelflisar.dialogs.interfaces.IMaterialDialogEvent
import com.michaelflisar.materialpreferences.preferencescreen.DialogExtra
import com.michaelflisar.materialpreferences.preferencescreen.interfaces.PreferenceItem
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.PreferenceAdapter
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.viewholders.base.BaseDialogViewHolder
import com.michaelflisar.text.asText

class InputStringViewHolder(
    inflater: LayoutInflater,
    parent: ViewGroup,
    override val adapter: PreferenceAdapter
) : BaseDialogViewHolder<String, InputStringPreference, ViewBinding?>(inflater, parent) {

    override fun createSubBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        attachToParent: Boolean
    ): ViewBinding? = null

    override fun onDialogResultAvailable(
        preference: PreferenceItem.Preference,
        event: IMaterialDialogEvent
    ) {
        event as DialogInput.Event
        preference as InputStringPreference
        if (event is DialogInput.Event.Result) {
            update(event.input, preference)
        }
    }

    override fun createDialog(preference: InputStringPreference) {
        val dlg = DialogInput(
            -1,
            title = preference.title,
            input = DialogInput.Input.Single(
                value = value.asText(),
                hint = preference.hint,
                inputType = preference.textInputType ?: InputType.TYPE_CLASS_TEXT,
                validator = DialogInput.TextValidator(if (preference.allowEmpty) null else 1)
            ),
            selectAllOnFocus = preference.selectAllOnFocus,
            buttonPositive = android.R.string.ok.asText(),
            extra = DialogExtra(preference.setting.key)
        )
        adapter.showDialog(preference, dlg)
    }
}