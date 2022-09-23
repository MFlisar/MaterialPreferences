package com.michaelflisar.materialpreferences.preferencescreen.bool.checkbox

import android.view.LayoutInflater
import android.view.ViewGroup
import com.michaelflisar.materialpreferences.preferencescreen.bool.databinding.WidgetCheckboxBinding
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.PreferenceAdapter
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.viewholders.base.BaseViewHolderWidgetSmall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CheckboxViewHolder(
    inflater: LayoutInflater,
    parent: ViewGroup,
    override val adapter: PreferenceAdapter
) : BaseViewHolderWidgetSmall<Boolean, CheckboxPreference, WidgetCheckboxBinding>(inflater, parent) {

    override fun createSubBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        attachToParent: Boolean
    ) =
        WidgetCheckboxBinding.inflate(inflater, parent, attachToParent)

    override fun bindWidget(preference: CheckboxPreference, rebind: Boolean) {
        subBinding.checkbox.isChecked = value
        subBinding.checkbox.jumpDrawablesToCurrentState()
    }

    override fun onClick(preference: CheckboxPreference) {
        val newValue = !value
        if (!preference.canChange(newValue)) {
            return
        }
        value = newValue
        scope.launch(Dispatchers.IO) {
            preference.setting.update(newValue)
            withContext(Dispatchers.Main) {
                preference.onChanged?.invoke(newValue)
            }
        }
        rebind(preference)
    }
}