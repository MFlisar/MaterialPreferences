package com.michaelflisar.materialpreferences.preferencescreen

import android.view.LayoutInflater
import android.view.ViewGroup
import com.michaelflisar.materialpreferences.preferencescreen.preferences.*
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.PreferenceAdapter
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.viewholders.base.BaseViewHolder

object ViewHolderFactory {

    private val MAP = HashMap<Int, ViewHolderCreator>()

    fun init() {

        // register default creators
        register(SubScreen.TYPE, SubScreen.Companion)
        register(CategoryHeaderPreference.TYPE, CategoryHeaderPreference.Companion)
        register(ButtonPreference.TYPE, ButtonPreference.Companion)
        register(CheckboxPreference.TYPE, CheckboxPreference.Companion)
        register(SwitchPreference.TYPE, SwitchPreference.Companion)

    }

    fun register(viewType: Int, function: ViewHolderCreator) {
        MAP[viewType] = function
    }

    internal fun create(adapter: PreferenceAdapter, parent: ViewGroup, viewType: Int): BaseViewHolder<*, *> {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MAP[viewType]?.createViewHolder(adapter, layoutInflater, parent)
                ?: throw RuntimeException("Type $viewType is not handled! Did you forget to register your custom preference?")
    }

    interface ViewHolderCreator {
        fun createViewHolder(adapter: PreferenceAdapter, layoutInflater: LayoutInflater, parent: ViewGroup): BaseViewHolder<*, *>
    }
}