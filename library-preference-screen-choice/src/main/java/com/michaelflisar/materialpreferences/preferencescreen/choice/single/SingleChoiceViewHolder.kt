package com.michaelflisar.materialpreferences.preferencescreen.choice.single

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.michaelflisar.dialogs.DialogList
import com.michaelflisar.dialogs.interfaces.IMaterialDialogEvent
import com.michaelflisar.materialpreferences.preferencescreen.DialogExtra
import com.michaelflisar.materialpreferences.preferencescreen.ScreenUtil
import com.michaelflisar.materialpreferences.preferencescreen.interfaces.PreferenceItem
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.PreferenceAdapter
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.viewholders.base.BaseDialogViewHolder
import com.michaelflisar.text.asText

class SingleChoiceViewHolder<T : Any>(
    inflater: LayoutInflater,
    parent: ViewGroup,
    override val adapter: PreferenceAdapter
) : BaseDialogViewHolder<T, SingleChoicePreference<T>, ViewBinding?>(inflater, parent) {

    override fun createSubBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        attachToParent: Boolean
    ): ViewBinding? = null

    override fun updateSummary(preference: SingleChoicePreference<T>) {
        val displayValue = preference.getChoiceDisplayValue(itemView.context, value)
        ScreenUtil.display(preference.summary, binding.summary, View.GONE, displayValue)
    }

    override fun onDialogResultAvailable(
        preference: PreferenceItem.Preference,
        event: IMaterialDialogEvent
    ) {
        event as DialogList.Event
        preference as SingleChoicePreference<T>
        if (event is DialogList.Event.Result) {
            val first = event.selectedItems.firstOrNull()
            if (first != null) {
                first as DialogList.SimpleListItem
                val index = first.listItemId.toInt()
                update(preference.getChoiceValue(index), preference)
            }
        }
    }

    override fun createDialog(preference: SingleChoicePreference<T>) {
        val dlg = DialogList(
            -1,
            title = preference.title,
            items = DialogList.createList(
                preference.getChoiceLabels(itemView.context).map { it.asText() }
            ),
            selectionMode = DialogList.SelectionMode.SingleSelect(
                preference.getSelectedIndex().toLong(),
                preference.closeOnSelect
            ),
            buttonPositive = android.R.string.ok.asText(),
            extra = DialogExtra(preference.setting.key)
        )
        adapter.showDialog(preference, dlg)
    }
}