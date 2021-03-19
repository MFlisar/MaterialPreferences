package com.michaelflisar.materialpreferences.preferencescreen.recyclerview.viewholders.base

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.michaelflisar.materialpreferences.preferencescreen.PreferenceScreenConfig
import com.michaelflisar.materialpreferences.preferencescreen.classes.Icon
import com.michaelflisar.materialpreferences.preferencescreen.interfaces.PreferenceItem
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.PreferenceAdapter
import com.michaelflisar.materialpreferences.preferencescreen.views.SettingsRootView
import kotlinx.coroutines.Job

abstract class BaseViewHolder<T : ViewBinding, P : PreferenceItem>(
        val binding: T
) : RecyclerView.ViewHolder(binding.root) {

    abstract val adapter: PreferenceAdapter

    protected val scope = (binding.root.context as AppCompatActivity).lifecycleScope
    private var job: Job? = null

    @CallSuper
    open fun bind(preference: P, rebind: Boolean) {
        val enabled = (preference as? PreferenceItem.Preference)?.enabled ?: true
        (preference as? PreferenceItem.Preference)?.dependsOn?.let {
            job = it.observe(scope) {
                setState(it && enabled)
            }
        } ?: setState(enabled)
    }

    @CallSuper
    open fun unbind() {
        job?.cancel()
        job = null
    }

    private fun setState(enabled: Boolean) {
        setState(binding.root, enabled)
    }

    private fun setState(v: View, enabled: Boolean) {
        (v as? SettingsRootView)?.setViewState(enabled) ?: run {
            v.isEnabled = enabled
            (v as? ViewGroup)?.children?.forEach {
                setState(it, enabled)
            }
        }
    }

    protected fun updateIconFrame(preference: PreferenceItem.PreferenceWithIcon, iconFrame: LinearLayout) {
        iconFrame.gravity = if (PreferenceScreenConfig.alignIconsWithBackArrow) Gravity.START or Gravity.CENTER_VERTICAL else Gravity.CENTER
        if (preference.icon is Icon.Empty) {
            iconFrame.visibility = preference.noIconVisibility.visibility
        } else {
            iconFrame.visibility = View.VISIBLE
        }
    }
}