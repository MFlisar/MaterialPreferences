package com.michaelflisar.materialpreferences.preferencescreen

import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting
import com.michaelflisar.materialpreferences.preferencescreen.interfaces.PreferenceItem
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.PreferenceAdapter

typealias ScreenChangedListener = (subScreenStack: List<PreferenceItem.SubScreen>, stateRestored: Boolean) -> Unit

class PreferenceScreen(
    val preferences: List<PreferenceItem>,
    val savedInstanceState: Bundle?,
    val onScreenChanged: ScreenChangedListener? = null
) {

    companion object {
        val KEY_ADAPTER_STATE = PreferenceScreen::class.java.name
    }

    private lateinit var adapter: PreferenceAdapter
    private var stateToRestore: PreferenceAdapter.StackEntry? = null

    fun bind(recyclerView: RecyclerView, fragment: Fragment) {
        bind(recyclerView, fragment.childFragmentManager, fragment)
    }

    fun bind(recyclerView: RecyclerView, activity: FragmentActivity) {
        bind(recyclerView, activity.supportFragmentManager, activity)
    }

    fun bind(recyclerView: RecyclerView, fragmentManager: FragmentManager, lifecycleOwner: LifecycleOwner) {

        adapter = PreferenceAdapter(fragmentManager, lifecycleOwner, preferences, onScreenChanged)
        savedInstanceState?.getParcelable<PreferenceAdapter.SavedState>(KEY_ADAPTER_STATE)
            ?.let(::loadAdapterState)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(recyclerView.context, RecyclerView.VERTICAL, false)
            adapter = this@PreferenceScreen.adapter
            layoutAnimation =
                AnimationUtils.loadLayoutAnimation(context, R.anim.preference_layout_fall_down)
        }
        adapter.recyclerView = recyclerView
        stateToRestore?.let {
            adapter.restoreView(it)
        }
        stateToRestore = null
    }

    fun onBackPressed() = adapter.onBackPressed()

    @MainThread
    fun loadAdapterState(state: PreferenceAdapter.SavedState) {
        stateToRestore = if (state.stack.size > 0) state.stack.removeLast() else null
        adapter.restoreStack(state)
    }

    fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(KEY_ADAPTER_STATE, adapter.getSavedState())
    }

    fun notifyItemChanged(item: PreferenceItem) {
        adapter.notifyItemChanged(item)
    }

    fun notifyItemChanged(item: StorageSetting<*>) {
        adapter.notifyItemChanged(item)
    }
}