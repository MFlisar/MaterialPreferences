package com.michaelflisar.materialpreferences.preferencescreen

import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.annotation.MainThread
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.michaelflisar.materialpreferences.preferencescreen.interfaces.PreferenceItem
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.PreferenceAdapter

typealias ScreenChangedListener = (subScreenStack: List<PreferenceItem.SubScreen>, stateRestored: Boolean) -> Unit
class PreferenceScreen(
        val preferences: List<PreferenceItem>,
        savedInstanceState: Bundle?,
        onScreenChanged: ScreenChangedListener? = null
) {

    companion object {
        val KEY_ADAPTER_STATE = PreferenceScreen::class.java.name
    }

    private val adapter: PreferenceAdapter = PreferenceAdapter(preferences, onScreenChanged)
    private var stateToRestore: PreferenceAdapter.StackEntry? = null

    init {
        savedInstanceState?.getParcelable<PreferenceAdapter.SavedState>(KEY_ADAPTER_STATE)?.let(::loadAdapterState)
    }

    fun bind(recyclerView: RecyclerView) {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(recyclerView.context, RecyclerView.VERTICAL, false)
            adapter = this@PreferenceScreen.adapter
            layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.preference_layout_fall_down)
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
}