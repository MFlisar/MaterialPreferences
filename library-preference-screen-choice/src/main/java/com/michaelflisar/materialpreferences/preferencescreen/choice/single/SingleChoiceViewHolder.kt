package com.michaelflisar.materialpreferences.preferencescreen.choice.single

import android.view.LayoutInflater
import android.view.ViewGroup
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.list.listItems
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.viewholders.base.BaseDialogViewHolder

class SingleChoiceViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
) : BaseDialogViewHolder<Int, SingleChoicePreference>(inflater, parent) {

    override fun showDialog(preference: SingleChoicePreference) {
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
        }.show()
    }
}