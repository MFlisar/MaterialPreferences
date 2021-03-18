package com.michaelflisar.materialpreferences.preferencescreen.recyclerview.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import com.michaelflisar.materialpreferences.preferencescreen.databinding.WidgetSwitchBinding
import com.michaelflisar.materialpreferences.preferencescreen.preferences.SwitchPreference
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.PreferenceAdapter
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.viewholders.base.BaseViewHolderWidget
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SwitchViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        override val adapter: PreferenceAdapter
) : BaseViewHolderWidget<Boolean, SwitchPreference, WidgetSwitchBinding>(inflater, parent) {

    override fun createSubBinding(inflater: LayoutInflater, parent: ViewGroup, attachToParent: Boolean) =
            WidgetSwitchBinding.inflate(inflater, parent, attachToParent)

    override fun bindWidget(preference: SwitchPreference, rebind: Boolean) {
        subBinding.switchWidget.isChecked = value
        subBinding.switchWidget.jumpDrawablesToCurrentState()
    }

    override fun onClick(preference: SwitchPreference) {
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