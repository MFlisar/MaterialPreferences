package com.michaelflisar.materialpreferences.preferencescreen.slider.slider.base

import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.android.material.slider.Slider
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.PreferenceAdapter
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.viewholders.base.BaseViewHolderWidgetLarge
import com.michaelflisar.materialpreferences.preferencescreen.slider.databinding.WidgetSliderBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseSliderViewHolder<T : Number, P : BaseSliderPreference<T>>(
    inflater: LayoutInflater,
    parent: ViewGroup,
    override val adapter: PreferenceAdapter
) : BaseViewHolderWidgetLarge<T, P, WidgetSliderBinding>(inflater, parent) {

    internal abstract fun convertValueFromSlider(value: Float): T
    internal abstract fun convertValueToSlider(value: T): Float

    override var clickable: Boolean = false

    private var slideListener: Slider.OnChangeListener? = null

    override fun createSubBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        attachToParent: Boolean
    ) =
        WidgetSliderBinding.inflate(inflater, parent, attachToParent)

    override fun bindWidget(preference: P, rebind: Boolean) {
        subBinding.slider.valueFrom = convertValueToSlider(preference.min)
        subBinding.slider.valueTo = convertValueToSlider(preference.max)
        subBinding.slider.value = convertValueToSlider(value)
        val formatter = preference.labelFormatter
        if (formatter != null) {
            subBinding.slider.setLabelFormatter {
                formatter(it)
            }
        } else {
            subBinding.slider.setLabelFormatter(null)
        }
        subBinding.slider.stepSize = preference.stepSize?.let { convertValueToSlider(it) } ?:
                preference.defaultStepSize?.let { convertValueToSlider(it) } ?:
                0f
        subBinding.slider.isTickVisible = preference.discrete
        slideListener = Slider.OnChangeListener { slider, value, fromUser ->
            if (!fromUser)
                return@OnChangeListener
            val newValue = convertValueFromSlider(value)
            if (newValue == this@BaseSliderViewHolder.value)
                return@OnChangeListener
            if (!preference.canChange(newValue)) {
                slider.value = this@BaseSliderViewHolder.value.toFloat()
                return@OnChangeListener
            }
            this@BaseSliderViewHolder.value = newValue
            scope.launch(Dispatchers.IO) {
                preference.setting.update(newValue)
                withContext(Dispatchers.Main) {
                    preference.onChanged?.invoke(newValue)
                }
            }
            // actually, UI is already correct, BUT
            // eventually we need to update the summary!
            subBinding.slider.removeOnChangeListener(slideListener!!)
            rebind(preference)
        }
        subBinding.slider.addOnChangeListener(slideListener!!)
    }

    override fun unbind() {
        slideListener?.let { subBinding.slider.removeOnChangeListener(it) }
        slideListener = null
        super.unbind()
    }
}