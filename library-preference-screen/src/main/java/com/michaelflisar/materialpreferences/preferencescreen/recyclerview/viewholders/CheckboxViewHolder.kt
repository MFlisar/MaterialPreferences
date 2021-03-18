package com.michaelflisar.materialpreferences.preferencescreen.recyclerview.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import com.michaelflisar.materialpreferences.preferencescreen.databinding.WidgetCheckboxBinding
import com.michaelflisar.materialpreferences.preferencescreen.databinding.WidgetSwitchBinding
import com.michaelflisar.materialpreferences.preferencescreen.preferences.CheckboxPreference
import com.michaelflisar.materialpreferences.preferencescreen.preferences.SwitchPreference
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.PreferenceAdapter
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.viewholders.base.BaseViewHolderWidget
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CheckboxViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        override val adapter: PreferenceAdapter
) : BaseViewHolderWidget<Boolean, CheckboxPreference, WidgetCheckboxBinding>(inflater, parent) {

    override fun createSubBinding(inflater: LayoutInflater, parent: ViewGroup, attachToParent: Boolean) =
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