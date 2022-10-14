package com.michaelflisar.materialpreferences.preferencescreen.choice.multi

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

class MultiChoiceViewHolder(
    inflater: LayoutInflater,
    parent: ViewGroup,
    override val adapter: PreferenceAdapter
) : BaseDialogViewHolder<Set<Int>, MultiChoicePreference, ViewBinding?>(inflater, parent) {

    override fun createSubBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        attachToParent: Boolean
    ): ViewBinding? = null

    override fun updateSummary(preference: MultiChoicePreference) {
        val displayValue = value
            .map { preference.choices[it] }
            .sortedBy { it.order }
            .joinToString(", ") { it.label.getString(itemView.context) }
        ScreenUtil.display(preference.summary, binding.summary, View.GONE, displayValue)
    }

    override fun onDialogResultAvailable(
        preference: PreferenceItem.Preference,
        event: IMaterialDialogEvent
    ) {
        event as DialogList.Event
        preference as MultiChoicePreference
        if (event is DialogList.Event.Result) {
            val items = event.selectedItems as List<DialogList.SimpleListItem>
            val indizes = items.map { it.listItemId.toInt() }
            update(indizes.toSet(), preference)
        }
    }

    override fun createDialog(preference: MultiChoicePreference) {
        val dlg = DialogList(
            -1,
            title = preference.title,
            items = DialogList.createList(preference.choices.map { it.label }),
            selectionMode = DialogList.SelectionMode.MultiSelect(
                value.toIntArray().map { it.toLong() }.toSortedSet()
            ),
            buttonPositive = android.R.string.ok.asText(),
            extra = DialogExtra(preference.setting.key)
        )
        adapter.showDialog(preference, dlg)
    }
}