package com.michaelflisar.materialpreferences.preferencescreen.recyclerview.viewholders.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.viewbinding.ViewBinding
import com.michaelflisar.materialpreferences.preferencescreen.PreferenceScreenConfig
import com.michaelflisar.materialpreferences.preferencescreen.ScreenUtil
import com.michaelflisar.materialpreferences.preferencescreen.interfaces.PreferenceItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseViewHolderWithWidget<D : Any, T, B : ViewBinding?, B2 : ViewBinding>(
    inflater: LayoutInflater,
    baseBinding: B2
) : BaseViewHolder<B2, T>(baseBinding)
        where T : PreferenceItem.PreferenceWithData<D>, T : PreferenceItem.Preference {

    open var clickable: Boolean = true
    abstract val widgetFrame: LinearLayout
    abstract val iconFrame: LinearLayout
    abstract val title: TextView
    abstract val summary: TextView
    abstract val icon: ImageView
    abstract val badge: TextView

    val subBinding: B by lazy { createSubBinding(inflater, widgetFrame, true) }

    protected lateinit var value: D

    protected fun init() {
        title.maxLines = PreferenceScreenConfig.maxLinesTitle
        summary.maxLines = PreferenceScreenConfig.maxLinesSummary
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
        preference.title.display(title)
        if (preference is PreferenceItem.PreferenceWithIcon) {
            updateIconFrame(preference, iconFrame)
            preference.icon.display(icon)
        }
        if (preference is PreferenceItem.PreferenceWithBadge) {
            preference.badge.display(badge)
        }
       //binding.root.isFocusable = clickable
       //binding.root.isFocusableInTouchMode = clickable
        binding.root.isClickable = clickable
        scope.launch(Dispatchers.IO) {
            value = preference.setting.read()
            withContext(Dispatchers.Main) {
                rebind(preference)
                if (clickable) {
                    binding.root.setOnClickListener {
                        onClick(preference)
                    }
                }
            }
        }
    }

    override fun unbind() {
        super.unbind()
        unbindWidget()
        binding.root.setOnClickListener(null)
        title.text = null
        icon.setImageDrawable(null)
        badge.text = null
        summary.text = null
    }

    fun rebind(preference: T) {
        updateSummary(preference)
        bindWidget(preference, true)
    }

    open fun updateSummary(preference: T) {
        if (preference is PreferenceItem.PreferenceWithSummary) {
            ScreenUtil.display(preference.summary, summary, View.GONE, value)
        }
    }

    abstract fun createSubBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        attachToParent: Boolean
    ): B

    abstract fun bindWidget(preference: T, rebind: Boolean)
    fun unbindWidget() {}

    open fun onClick(preference: T) {
    }
}