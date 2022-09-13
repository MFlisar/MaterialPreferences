package com.michaelflisar.materialpreferences.preferencescreen.recyclerview

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.michaelflisar.materialpreferences.preferencescreen.interfaces.PreferenceItem

internal object PreferenceDiff : DiffUtil.ItemCallback<PreferenceItem>() {

    override fun areItemsTheSame(oldItem: PreferenceItem, newItem: PreferenceItem): Boolean =
        oldItem == newItem

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: PreferenceItem, newItem: PreferenceItem): Boolean {
        return oldItem == newItem
    }
}