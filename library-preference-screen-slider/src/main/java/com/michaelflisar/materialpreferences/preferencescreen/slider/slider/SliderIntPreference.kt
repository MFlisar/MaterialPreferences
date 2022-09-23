package com.michaelflisar.materialpreferences.preferencescreen.slider.slider

import android.view.LayoutInflater
import android.view.ViewGroup
import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting
import com.michaelflisar.materialpreferences.preferencescreen.ViewHolderFactory
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.PreferenceAdapter
import com.michaelflisar.materialpreferences.preferencescreen.slider.R
import com.michaelflisar.materialpreferences.preferencescreen.slider.slider.base.BaseSliderPreference

class SliderIntPreference(
    setting: StorageSetting<Int>
) : BaseSliderPreference<Int>(setting) {

    override var min = Int.MIN_VALUE
    override var max = Int.MAX_VALUE
    override var stepSize: Int? = null

    override val defaultStepSize = 1

    companion object : ViewHolderFactory.ViewHolderCreator {
        val TYPE = R.id.pref_seekbar_int
        override fun createViewHolder(
            adapter: PreferenceAdapter,
            layoutInflater: LayoutInflater,
            parent: ViewGroup
        ) = SliderIntViewHolder(
            layoutInflater,
            parent,
            adapter
        )
    }

    override val type = TYPE

}