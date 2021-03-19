package com.michaelflisar.materialpreferences.preferencescreen.recyclerview

import android.os.Parcelable
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.michaelflisar.materialpreferences.preferencescreen.ScreenChangedListener
import com.michaelflisar.materialpreferences.preferencescreen.ViewHolderFactory
import com.michaelflisar.materialpreferences.preferencescreen.interfaces.PreferenceItem
import com.michaelflisar.materialpreferences.preferencescreen.preferences.SubScreen
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.viewholders.base.BaseViewHolder
import kotlinx.parcelize.Parcelize
import java.util.*
import kotlin.collections.ArrayList

class PreferenceAdapter(
        private val preferences: List<PreferenceItem>,
        private val onScreenChanged: ScreenChangedListener?
) : RecyclerView.Adapter<BaseViewHolder<ViewBinding, PreferenceItem>>() {

    private var prefs: List<PreferenceItem> = preferences
    private var stack: Stack<StackEntry> = Stack()
    var dialogInfo: DialogInfo? = null
    var recyclerView: RecyclerView? = null

    override fun getItemViewType(position: Int) = prefs[position].type

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ViewBinding, PreferenceItem> {
        return ViewHolderFactory.create(this, parent, viewType) as BaseViewHolder<ViewBinding, PreferenceItem>
    }

    override fun onBindViewHolder(holder: BaseViewHolder<ViewBinding, PreferenceItem>, position: Int) {
        val pref = prefs[position]
        holder.bind(pref, false)
    }

    override fun onViewRecycled(holder: BaseViewHolder<ViewBinding, PreferenceItem>) {
        super.onViewRecycled(holder)
        holder.unbind()
    }

    override fun getItemCount() = prefs.size

    fun onSubScreenClicked(subScreen: SubScreen) {
        val index = prefs.indexOf(subScreen)
        stack.push(StackEntry.create(index, recyclerView))
        prefs = subScreen.preferences
        onScreenChanged(stack.peek())
    }

    internal fun onBackPressed(): Boolean {
        return if (stack.size > 0) {
            val stackEntry = stack.pop()
            prefs = getCurrentSubScreenPreferences()
            onScreenChanged(stackEntry)
            true
        } else false
    }

    private fun onScreenChanged(stackEntry: StackEntry) {
        // rerun view animation
        recyclerView?.scheduleLayoutAnimation()
        notifyDataSetChanged()
        restoreView(stackEntry)
        notifyScreenChangedListener(false)
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

    private fun getCurrentSubScreenPreferences(): List<PreferenceItem> {
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
            prefs = getCurrentSubScreenPreferences()
            notifyDataSetChanged()
        }
        notifyScreenChangedListener(true)
        dialogInfo = state.dialogShown?.let { DialogInfo(it, null) }
    }

    fun restoreView(state: StackEntry) {
        dialogInfo?.let {
            it.preference = prefs[it.index]
            // the rv will scroll to this item and it will check and reset the showDialog variable itself, nothing more to do here
        }
        if (state.firstVisibleItem != 0 || state.firstVisibleItemOffset != 0) {
            (recyclerView?.layoutManager as? LinearLayoutManager)?.scrollToPositionWithOffset(state.firstVisibleItem, state.firstVisibleItemOffset)
        }
    }

    fun getSavedState(): SavedState {
        val fullStack = stack.clone() as Stack<StackEntry>
        fullStack.push(StackEntry.create(-1, recyclerView))
        return SavedState(ArrayList(fullStack.toList()), dialogInfo?.index)
    }

    @Parcelize
    data class SavedState(
            val stack: ArrayList<StackEntry>,
            val dialogShown: Int?
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
                    offset = recyclerView?.findViewHolderForAdapterPosition(position)?.run { itemView.top }
                            ?: 0
                }
                return StackEntry(index, position, offset)
            }
        }
    }

    class DialogInfo(
            val index: Int,
            var preference: PreferenceItem? = null
    )
}