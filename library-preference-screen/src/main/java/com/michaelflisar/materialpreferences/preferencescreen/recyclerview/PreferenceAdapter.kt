package com.michaelflisar.materialpreferences.preferencescreen.recyclerview

import android.os.Parcelable
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.michaelflisar.dialogs.MaterialDialogSetup
import com.michaelflisar.dialogs.interfaces.IMaterialDialogEvent
import com.michaelflisar.dialogs.onMaterialDialogEvent
import com.michaelflisar.dialogs.showBottomSheetDialogFragment
import com.michaelflisar.dialogs.showDialogFragment
import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting
import com.michaelflisar.materialpreferences.preferencescreen.*
import com.michaelflisar.materialpreferences.preferencescreen.interfaces.PreferenceItem
import com.michaelflisar.materialpreferences.preferencescreen.preferences.SubScreen
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.viewholders.base.BaseDialogViewHolder
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.viewholders.base.BaseViewHolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.parcelize.Parcelize
import java.util.*

class PreferenceAdapter(
    private val fragmentManager: FragmentManager,
    lifecycleOwner: LifecycleOwner,
    private val preferences: List<PreferenceItem>,
    private val onScreenChanged: ScreenChangedListener?
) : ListAdapter<PreferenceItem, BaseViewHolder<ViewBinding, PreferenceItem>>(PreferenceDiff) {

    private var currentUnfilteredPrefs: List<PreferenceItem> = preferences
    private var hiddenPrefs: MutableSet<PreferenceItem> = HashSet()

    private var stack: Stack<StackEntry> = Stack()
    var recyclerView: RecyclerView? = null

    init {

        val allPreferences = ScreenUtil.flatten(preferences)
        val preferencesWithDependency = allPreferences
            .filterIsInstance<PreferenceItem.PreferenceWithDependencies>()

        // load initial visibility state + set initial filtered adapter items
        // afterwards observe changes of visibility
        lifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            preferencesWithDependency
                .forEach {
                    if (it.visibilityDependsOn?.state() == false) {
                        hiddenPrefs.add(it)
                    }
                }
            withContext(Dispatchers.Main) {
                // set initial list
                updateCurrentFilteredItems(true)
                // observe further visibility changes
                preferencesWithDependency
                    .forEach { p ->
                        p.visibilityDependsOn?.let { dependency ->
                            dependency.observe(lifecycleOwner.lifecycleScope) { visible ->
                                if (visible) {
                                    if (hiddenPrefs.remove(p)) {
                                        updateCurrentFilteredItems(true)
                                    }
                                } else {
                                    if (hiddenPrefs.add(p)) {
                                        updateCurrentFilteredItems(true)
                                    }
                                }
                            }
                        }
                    }
            }
        }

        // handle dialog events centrally here
        lifecycleOwner.onMaterialDialogEvent<IMaterialDialogEvent> {
            if (it.extra is DialogExtra) {
                val extra = it.extra as DialogExtra
                val item = currentList.filterIsInstance<PreferenceItem.PreferenceDialog<*>>()
                    .find { it.setting.key == extra.key }
                if (item != null) {
                    val index = currentList.indexOf(item)
                    //Log.d("INDEX", "index = $index")
                    lifecycleOwner.lifecycleScope.launch {
                        val vh = recyclerView?.awaitViewHolder(index)
                        //Log.d("INDEX", "index visible...")
                        (vh as BaseDialogViewHolder<*, *, *>).onDialogResultAvailable(item, it)
                    }
                }
            }
        }
    }

    private fun updateCurrentFilteredItems(submit: Boolean): List<PreferenceItem> {
        val currentFilteredPrefs = currentUnfilteredPrefs.filter { !hiddenPrefs.contains(it) }
        if (submit)
            submitList(currentFilteredPrefs)
        return currentFilteredPrefs
    }

    fun notifyItemChanged(item: PreferenceItem) {
        val index = currentList.indexOf(item)
        if (index >= 0)
            notifyItemChanged(index)
    }

    fun notifyItemChanged(item: StorageSetting<*>) {
        val items =
            currentList.filter { it is PreferenceItem.PreferenceWithData<*> && it.setting == item }
        items.forEach {
            notifyItemChanged(it)
        }
    }

    override fun getItemViewType(position: Int) = currentList[position].type

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<ViewBinding, PreferenceItem> {
        return ViewHolderFactory.create(
            this,
            parent,
            viewType
        ) as BaseViewHolder<ViewBinding, PreferenceItem>
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<ViewBinding, PreferenceItem>,
        position: Int
    ) {
        val pref = currentList[position]
        holder.bind(pref, false)
    }

    override fun onViewRecycled(holder: BaseViewHolder<ViewBinding, PreferenceItem>) {
        super.onViewRecycled(holder)
        holder.unbind()
    }

    fun onSubScreenClicked(subScreen: SubScreen) {
        //val index = currentList.indexOf(subScreen)
        val unfilteredIndex = currentUnfilteredPrefs.indexOf(subScreen)
        stack.push(StackEntry.create(unfilteredIndex, recyclerView))
        currentUnfilteredPrefs = subScreen.preferences
        val filtered = updateCurrentFilteredItems(false)
        onScreenChanged(stack.peek(), filtered, true)
    }

    internal fun onBackPressed(): Boolean {
        return if (stack.size > 0) {
            val stackEntry = stack.pop()
            currentUnfilteredPrefs = getCurrentSubScreenPreferencesUnfiltered()
            val filtered = updateCurrentFilteredItems(false)
            onScreenChanged(stackEntry, filtered, false)
            true
        } else false
    }

    private fun onScreenChanged(
        stackEntry: StackEntry,
        filtered: List<PreferenceItem>,
        forward: Boolean
    ) {
        // rerun view animation
        recyclerView?.scheduleLayoutAnimation()
        submitList(filtered) {
            if (!forward)
                restoreView(stackEntry)
            notifyScreenChangedListener(false)
        }
    }

    private fun notifyScreenChangedListener(stateRestored: Boolean) {
        val subScreens = getCurrentSubScreens()
        onScreenChanged?.invoke(subScreens, stateRestored)
    }

    private fun getCurrentSubScreens(): List<SubScreen> {
        val screens = ArrayList<SubScreen>()
        if (stack.size > 0) {
            var p = preferences[stack[0].index] as SubScreen
            screens.add(p)
            stack.asSequence().drop(1).forEach {
                p = p.preferences[it.index] as SubScreen
                screens.add(p)
            }
        }
        return screens
    }

    private fun getCurrentSubScreenPreferencesUnfiltered(): List<PreferenceItem> {
        if (stack.size == 0) {
            return preferences
        } else {
            var p = preferences[stack[0].index] as SubScreen
            stack.asSequence().drop(1).forEach {
                p = p.preferences[it.index] as SubScreen
            }
            return p.preferences
        }
    }

    fun restoreStack(state: SavedState) {
        if (state.stack != stack) {
            stack = Stack()
            state.stack.forEach {
                stack.push(it)
            }
            currentUnfilteredPrefs = getCurrentSubScreenPreferencesUnfiltered()
            updateCurrentFilteredItems(true)
        }
        notifyScreenChangedListener(true)
    }

    fun restoreView(state: StackEntry) {
        if (state.firstVisibleItem != 0 || state.firstVisibleItemOffset != 0) {
            (recyclerView?.layoutManager as? LinearLayoutManager)?.scrollToPositionWithOffset(
                state.firstVisibleItem,
                state.firstVisibleItemOffset
            )
        }
    }

    fun getSavedState(): SavedState {
        val fullStack = stack.clone() as Stack<StackEntry>
        fullStack.push(StackEntry.create(-1, recyclerView))
        return SavedState(ArrayList(fullStack.toList()))
    }

    fun <S : MaterialDialogSetup<S, B, E>, B : ViewBinding, E : IMaterialDialogEvent> showDialog(
        preference: PreferenceItem.PreferenceDialog<*>,
        dialog: MaterialDialogSetup<S, B, E>
    ) {
        if (preference.bottomSheet)
            dialog.showBottomSheetDialogFragment(fragmentManager)
        else
            dialog.showDialogFragment(fragmentManager)
    }

    @Parcelize
    data class SavedState(
        val stack: ArrayList<StackEntry>
    ) : Parcelable

    @Parcelize
    data class StackEntry(
        val index: Int,
        val firstVisibleItem: Int,
        val firstVisibleItemOffset: Int
    ) : Parcelable {

        companion object {
            fun create(index: Int, recyclerView: RecyclerView?): StackEntry {
                var position = 0
                var offset = 0
                (recyclerView?.layoutManager as? LinearLayoutManager)?.let {
                    position = it.findFirstCompletelyVisibleItemPosition()
                    offset = recyclerView.findViewHolderForAdapterPosition(position)
                        ?.run { itemView.top }
                        ?: 0
                }
                return StackEntry(index, position, offset)
            }
        }
    }
}