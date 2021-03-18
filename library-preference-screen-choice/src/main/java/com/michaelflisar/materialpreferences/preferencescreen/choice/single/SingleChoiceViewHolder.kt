package com.michaelflisar.materialpreferences.preferencescreen.choice.single

import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.list.listItems
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.PreferenceAdapter
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.viewholders.base.BaseDialogViewHolder

class SingleChoiceViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        override val adapter: PreferenceAdapter
) : BaseDialogViewHolder<Int, SingleChoicePreference, ViewBinding?>(inflater, parent) {

    override fun createSubBinding(inflater: LayoutInflater, parent: ViewGroup, attachToParent: Boolean): ViewBinding? = null

    override fun updateSummary(preference: SingleChoicePreference) {
        val displayValue = preference.choices[value].label.get(itemView.context)
        preference.summary.display(binding.summary, View.GONE, displayValue)
    }

    override fun createDialog(preference: SingleChoicePreference): Dialog {
        val dialog = if (preference.bottomSheet) MaterialDialog(itemView.context, BottomSheet()) else MaterialDialog(itemView.context)
        dialog.show {
            title(text = preference.title.get(itemView.context))
            if (preference.showCheckBoxes) {
                listItemsSingleChoice(
                        items = preference.choices.map { it.label.get(context) },
                        waitForPositiveButton = false
                ) { dialog, index, text ->
                    update(preference.choices[index].id, preference)
                }
            } else {
                listItems(
                        items = preference.choices.map { it.label.get(context) },
                        waitForPositiveButton = false
                ) { dialog, index, text ->
                    update(preference.choices[index].id, preference)
                    dialog.dismiss()
                }
            }
            positiveButton(android.R.string.ok)
        }
        return dialog
    }
}