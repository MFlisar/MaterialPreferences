package com.michaelflisar.materialpreferences.preferencescreen.recyclerview.viewholders.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.michaelflisar.dialogs.interfaces.IMaterialDialogEvent
import com.michaelflisar.materialpreferences.preferencescreen.interfaces.PreferenceItem
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.PreferenceAdapter

abstract class BaseDialogViewHolder<D : Any, T, B : ViewBinding?>(
    inflater: LayoutInflater,
    parent: ViewGroup
) : BaseViewHolderWidget<D, T, B>(inflater, parent)
        where T : PreferenceItem.PreferenceWithData<D>, T : PreferenceItem.Preference {

    override fun bindWidget(preference: T, rebind: Boolean) {
    }

    override fun onClick(preference: T) {
        createDialog(preference)
    }

    abstract fun onDialogResultAvailable(preference: PreferenceItem.Preference, event: IMaterialDialogEvent)

    abstract fun createDialog(preference: T)
}