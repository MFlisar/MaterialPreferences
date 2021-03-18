package com.michaelflisar.materialpreferences.preferencescreen.recyclerview.viewholders.base

import android.app.Dialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.michaelflisar.materialpreferences.preferencescreen.interfaces.PreferenceItem
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.PreferenceAdapter

abstract class BaseDialogViewHolder<D : Any, T, B : ViewBinding?>(
        inflater: LayoutInflater,
        parent: ViewGroup
) : BaseViewHolderWidget<D, T, B>(inflater, parent)
        where T : PreferenceItem.PreferenceWithData<D>, T : PreferenceItem.Preference {

    override fun bindWidget(preference: T, rebind: Boolean) {
        if (adapter.dialogInfo?.preference == preference) {
            onClick(preference)
            adapter.dialogInfo = null
        }
    }

    override fun onClick(preference: T) {
        val dialog = createDialog(preference)
        dialog.setOnDismissListener {
            adapter.dialogInfo = null
        }
        dialog.show()
        adapter.dialogInfo = PreferenceAdapter.DialogInfo(adapterPosition)
    }

    fun showDialog(preference: PreferenceItem) = onClick(preference as T)

    abstract fun createDialog(preference: T): Dialog
}