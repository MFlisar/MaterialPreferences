package com.michaelflisar.materialpreferences.preferencescreen.recyclerview.viewholders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.michaelflisar.materialpreferences.preferencescreen.ScreenUtil
import com.michaelflisar.materialpreferences.preferencescreen.databinding.PreferenceWithWidgetSmallBinding
import com.michaelflisar.materialpreferences.preferencescreen.interfaces.PreferenceItem
import com.michaelflisar.materialpreferences.preferencescreen.preferences.SubScreen
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.PreferenceAdapter
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.viewholders.base.BaseViewHolder

class SimpleViewHolder(
    inflater: LayoutInflater,
    parent: ViewGroup,
    override val adapter: PreferenceAdapter
) : BaseViewHolder<PreferenceWithWidgetSmallBinding, PreferenceItem>(
    PreferenceWithWidgetSmallBinding.inflate(
        inflater,
        parent,
        false
    )
) {

    override fun bind(preference: PreferenceItem, rebind: Boolean) {
        super.bind(preference, rebind)
        preference.title.display(binding.title)
        if (preference is PreferenceItem.PreferenceWithIcon) {
            updateIconFrame(preference, binding.iconFrame)
            preference.icon.display(binding.icon)
        }
        if (preference is PreferenceItem.PreferenceWithBadge) {
            preference.badge.display(binding.badge)
        }
        if (preference is PreferenceItem.PreferenceWithSummary) {
            ScreenUtil.display(preference.summary, binding.summary, View.GONE)
        }
        if (!rebind && isClickable(preference)) {
            binding.root.setOnClickListener {
                onClick(preference)
            }
        } else {
            binding.root.isClickable = false
        }
    }

    private fun isClickable(preference: PreferenceItem): Boolean {
        return preference is PreferenceItem.ClickablePreference || preference is SubScreen
    }

    private fun onClick(preference: PreferenceItem) {
        if (preference is PreferenceItem.ClickablePreference) {
            preference.onClick?.invoke()
        }
        if (preference is SubScreen) {
            adapter.onSubScreenClicked(preference)
        }
    }

    override fun unbind() {
        super.unbind()
        binding.root.setOnClickListener(null)
        binding.title.text = null
        binding.icon.setImageDrawable(null)
        binding.badge.text = null
        binding.summary.text = null
    }
}