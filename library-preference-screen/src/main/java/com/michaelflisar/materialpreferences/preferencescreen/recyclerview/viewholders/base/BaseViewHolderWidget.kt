package com.michaelflisar.materialpreferences.preferencescreen.recyclerview.viewholders.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.michaelflisar.materialpreferences.preferencescreen.PreferenceScreenConfig
import com.michaelflisar.materialpreferences.preferencescreen.databinding.PreferenceBinding
import com.michaelflisar.materialpreferences.preferencescreen.interfaces.PreferenceItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseViewHolderWidget<D : Any, T, B : ViewBinding?>(
        inflater: LayoutInflater,
        parent: ViewGroup
) : BaseViewHolder<PreferenceBinding, T>(PreferenceBinding.inflate(inflater, parent, false))
        where T : PreferenceItem.PreferenceWithData<D>, T : PreferenceItem.Preference {

    val subBinding: B = createSubBinding(inflater, binding.widgetFrame, true)

    protected lateinit var value: D

    init {
        binding.title.maxLines = PreferenceScreenConfig.maxLinesTitle
        binding.summary.maxLines = PreferenceScreenConfig.maxLinesSummary
    }

    fun update(newValue: D, preference: T) {
        if (!preference.canChange(newValue)) {
            return
        }
        value = newValue
        scope.launch(Dispatchers.IO) {
            preference.setting.update(newValue)
            withContext(Dispatchers.Main) {
                preference.onChanged?.invoke(newValue)
            }
        }
        rebind(preference)
    }

    override fun bind(preference: T, rebind: Boolean) {
        super.bind(preference, rebind)
        preference.title.display(binding.title)
        if (preference is PreferenceItem.PreferenceWithIcon) {
            updateIconFrame(preference, binding.iconFrame)
            preference.icon.display(binding.icon)
        }
        if (preference is PreferenceItem.PreferenceWithBadge) {
            preference.badge.display(binding.badge)
        }
        scope.launch(Dispatchers.IO) {
            value = preference.setting.read()
            withContext(Dispatchers.Main) {
                rebind(preference)
                binding.root.setOnClickListener {
                    onClick(preference)
                }
            }
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

    fun rebind(preference: T) {
        updateSummary(preference)
        bindWidget(preference, true)
    }

    open fun updateSummary(preference: T) {
        if (preference is PreferenceItem.PreferenceWithSummary) {
            preference.summary.display(binding.summary, View.GONE, value)
        }
    }

    abstract fun createSubBinding(inflater: LayoutInflater, parent: ViewGroup, attachToParent: Boolean): B

    abstract fun bindWidget(preference: T, rebind: Boolean)

    abstract fun onClick(preference: T)
}