package com.michaelflisar.materialpreferences.preferencescreen.choice.multi

import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.list.listItemsMultiChoice
import com.michaelflisar.materialpreferences.preferencescreen.choice.single.SingleChoicePreference
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.PreferenceAdapter
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.viewholders.base.BaseDialogViewHolder

class MultiChoiceViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        override val adapter: PreferenceAdapter
) : BaseDialogViewHolder<Set<Int>, MultiChoicePreference, ViewBinding?>(inflater, parent) {

    override fun createSubBinding(inflater: LayoutInflater, parent: ViewGroup, attachToParent: Boolean): ViewBinding? = null

    override fun updateSummary(preference: MultiChoicePreference) {
        val displayValue = value
                .map { preference.choices[it] }
                .sortedBy { it.order }
                .joinToString(", ") { it.label.get(itemView.context) }
        preference.summary.display(binding.summary, View.GONE, displayValue)
    }

    override fun createDialog(preference: MultiChoicePreference): Dialog {
        val dialog = if (preference.bottomSheet) MaterialDialog(itemView.context, BottomSheet()) else MaterialDialog(itemView.context)
        dialog.show {
            title(text = preference.title.get(itemView.context))
            listItemsMultiChoice(
                    allowEmptySelection = preference.allowEmptySelection,
                    items = preference.choices.map { it.label.get(context) },
                    initialSelection = value.toIntArray()
            ) { dialog, indices, items ->
                update(indices.toSet(), preference)
            }
            positiveButton(android.R.string.ok)
        }
        return dialog
    }
}