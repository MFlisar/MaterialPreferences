package com.michaelflisar.materialpreferences.preferencescreen.imahe

import android.view.LayoutInflater
import android.view.ViewGroup
import com.michaelflisar.dialogs.interfaces.IMaterialDialogEvent
import com.michaelflisar.materialpreferences.preferencescreen.image.ImagePreference
import com.michaelflisar.materialpreferences.preferencescreen.image.databinding.WidgetImageBinding
import com.michaelflisar.materialpreferences.preferencescreen.interfaces.PreferenceItem
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.PreferenceAdapter
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.viewholders.base.BaseDialogViewHolder

class ImageViewHolder(
    inflater: LayoutInflater,
    parent: ViewGroup,
    override val adapter: PreferenceAdapter
) : BaseDialogViewHolder<Any, ImagePreference<Any>, WidgetImageBinding>(inflater, parent) {

    override fun createSubBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        attachToParent: Boolean
    ) = WidgetImageBinding.inflate(inflater, parent, true)

    override fun bindWidget(preference: ImagePreference<Any>, rebind: Boolean) {
        preference.handler.displayData(value, subBinding.ivImage)
    }

    override fun onDialogResultAvailable(
        preference: PreferenceItem.Preference,
        event: IMaterialDialogEvent
    ) {
        preference as ImagePreference<Any>
        val updatedValue = preference.handler.onDialogEvent(event)
        updatedValue.takeIf { it != value }?.let {
            update(it, preference)
        }
    }

    override fun createDialog(preference: ImagePreference<Any>) {
        val dlg = preference.handler.createDialog(preference.setting)
        adapter.showDialog(preference, dlg)
    }
}