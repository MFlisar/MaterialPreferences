package com.michaelflisar.materialpreferences.preferencescreen.choice.single

import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.list.customListAdapter
import com.michaelflisar.materialpreferences.preferencescreen.ScreenUtil
import com.michaelflisar.materialpreferences.preferencescreen.choice.SingleChoiceAdapter
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.PreferenceAdapter
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.viewholders.base.BaseDialogViewHolder

class SingleChoiceViewHolder<T : Any>(
        inflater: LayoutInflater,
        parent: ViewGroup,
        override val adapter: PreferenceAdapter
) : BaseDialogViewHolder<T, SingleChoicePreference<T>, ViewBinding?>(inflater, parent) {

    override fun createSubBinding(inflater: LayoutInflater, parent: ViewGroup, attachToParent: Boolean): ViewBinding? = null

    override fun updateSummary(preference: SingleChoicePreference<T>) {
        val displayValue = preference.getChoiceDisplayValue(itemView.context, value)
        ScreenUtil.display(preference.summary, binding.summary, View.GONE, displayValue)
    }

    override fun createDialog(preference: SingleChoicePreference<T>): Dialog {
        val dialog = if (preference.bottomSheet) MaterialDialog(itemView.context, BottomSheet()) else MaterialDialog(itemView.context)
        dialog.show {
            title(text = preference.title.getString(itemView.context))

            val selectedIndex = preference.getSelectedIndex()
            val items = preference.getChoiceLabels(context).mapIndexed { index, label -> SingleChoiceAdapter.Item(label, index == selectedIndex) }

            customListAdapter(SingleChoiceAdapter(preference.displayType, items) { item, index ->
                update(preference.getChoiceValue(index), preference)
                dismiss()
            })

            /*
            if (preference.showCheckBoxes) {
                listItemsSingleChoice(
                        items = preference.getChoiceLabels(context),
                        waitForPositiveButton = false,
                        initialSelection = preference.getSelectedIndex()
                ) { dialog, index, text ->
                    update(preference.getChoiceValue(index), preference)
                }
            } else {
                listItems(
                        items = preference.getChoiceLabels(context),
                        waitForPositiveButton = false
                ) { dialog, index, text ->
                    update(preference.getChoiceValue(index), preference)
                    dialog.dismiss()
                }
            }*/
            positiveButton(android.R.string.ok)
        }
        return dialog
    }
}