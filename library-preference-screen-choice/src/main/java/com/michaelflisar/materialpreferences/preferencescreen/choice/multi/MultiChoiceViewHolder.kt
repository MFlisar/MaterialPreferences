package com.michaelflisar.materialpreferences.preferencescreen.choice.multi

import android.view.LayoutInflater
import android.view.ViewGroup
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.list.listItemsMultiChoice
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.viewholders.base.BaseDialogViewHolder

class MultiChoiceViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
) : BaseDialogViewHolder<Set<Int>, MultiChoicePreference>(inflater, parent) {

    override fun showDialog(preference: MultiChoicePreference) {
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
        }.show()
    }
}