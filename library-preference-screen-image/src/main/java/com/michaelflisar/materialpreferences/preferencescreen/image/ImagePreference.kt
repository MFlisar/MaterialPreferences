package com.michaelflisar.materialpreferences.preferencescreen.image

import android.view.LayoutInflater
import android.view.ViewGroup
import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting
import com.michaelflisar.materialpreferences.preferencescreen.PreferenceScreenConfig
import com.michaelflisar.materialpreferences.preferencescreen.ViewHolderFactory
import com.michaelflisar.materialpreferences.preferencescreen.imahe.ImageViewHolder
import com.michaelflisar.materialpreferences.preferencescreen.interfaces.PreferenceItem
import com.michaelflisar.materialpreferences.preferencescreen.preferences.BasePreferenceItem
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.PreferenceAdapter

class ImagePreference<T: Any>(
    override val setting: StorageSetting<T>,
    val handler: ImagePreferenceHandler<T>
) : BasePreferenceItem(), PreferenceItem.PreferenceWithData<T>,
    PreferenceItem.PreferenceDialog<T> {

    override var canChange: (value: T) -> Boolean = { true }
    override var onChanged: ((value: T) -> Unit)? = null
    override var bottomSheet: Boolean = PreferenceScreenConfig.bottomSheet

    companion object : ViewHolderFactory.ViewHolderCreator {
        val TYPE = R.id.pref_image
        override fun createViewHolder(
            adapter: PreferenceAdapter,
            layoutInflater: LayoutInflater,
            parent: ViewGroup
        ) = ImageViewHolder(layoutInflater, parent, adapter)
    }

    override val type = TYPE
}