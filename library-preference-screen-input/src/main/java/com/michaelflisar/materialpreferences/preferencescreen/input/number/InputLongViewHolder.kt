package com.michaelflisar.materialpreferences.preferencescreen.input.number

import android.text.InputType
import android.view.LayoutInflater
import android.view.ViewGroup
import com.michaelflisar.dialogs.DialogInput
import com.michaelflisar.dialogs.interfaces.IMaterialDialogEvent
import com.michaelflisar.materialpreferences.preferencescreen.input.number.base.InputNumberViewHolder
import com.michaelflisar.materialpreferences.preferencescreen.interfaces.PreferenceItem
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.PreferenceAdapter

class InputLongViewHolder(
    inflater: LayoutInflater,
    parent: ViewGroup,
    adapter: PreferenceAdapter
) : InputNumberViewHolder<Long, InputLongPreference>(inflater, parent, adapter) {

    override val dialogInputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED

    override fun onDialogResultAvailable(
        preference: PreferenceItem.Preference,
        event: IMaterialDialogEvent
    ) {
        event as DialogInput.Event
        preference as InputLongPreference
        if (event is DialogInput.Event.Result) {
            update(event.input.toLongOrNull() ?: preference.defaultValue, preference)
        }
    }
}