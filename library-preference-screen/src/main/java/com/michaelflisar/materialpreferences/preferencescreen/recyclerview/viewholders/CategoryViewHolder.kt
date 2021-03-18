package com.michaelflisar.materialpreferences.preferencescreen.recyclerview.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import com.michaelflisar.materialpreferences.preferencescreen.databinding.PreferenceCategoryBinding
import com.michaelflisar.materialpreferences.preferencescreen.preferences.CategoryHeaderPreference
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.viewholders.base.BaseViewHolder

class CategoryViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
) : BaseViewHolder<PreferenceCategoryBinding, CategoryHeaderPreference>(PreferenceCategoryBinding.inflate(inflater, parent, false)
) {
    override fun bind(preferences: CategoryHeaderPreference, rebind: Boolean) {
        super.bind(preferences, rebind)
        preferences.title.display(binding.title)
    }

    override fun unbind() {
        super.unbind()
        binding.title.text = null
    }
}