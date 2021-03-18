package com.michaelflisar.materialpreferences.preferencescreen.recyclerview.viewholders.base

import android.app.Dialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.michaelflisar.materialpreferences.preferencescreen.interfaces.PreferenceItem

abstract class BaseDialogViewHolder<D : Any, T>(
        inflater: LayoutInflater,
        parent: ViewGroup
) : BaseViewHolderWidget<D, T, ViewBinding?>(inflater, parent)
        where T : PreferenceItem.PreferenceWithData<D>, T : PreferenceItem.Preference {

    override fun createSubBinding(inflater: LayoutInflater, parent: ViewGroup, attachToParent: Boolean) = null

    override fun bindWidget(preference: T, rebind: Boolean) {

    }

    override fun onClick(preference: T) {
        showDialog(preference)
    }

    abstract fun showDialog(preference: T)
}