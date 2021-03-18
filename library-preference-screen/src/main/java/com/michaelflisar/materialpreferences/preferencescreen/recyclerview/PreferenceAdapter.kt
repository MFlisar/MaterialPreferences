package com.michaelflisar.materialpreferences.preferencescreen.recyclerview

import android.os.Parcelable
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.michaelflisar.materialpreferences.preferencescreen.ViewHolderFactory
import com.michaelflisar.materialpreferences.preferencescreen.interfaces.PreferenceItem
import com.michaelflisar.materialpreferences.preferencescreen.preferences.SubScreen
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.viewholders.base.BaseViewHolder
import kotlinx.parcelize.Parcelize
import java.util.*

class PreferenceAdapter(
        private val preferences: List<PreferenceItem>,
        private val onScreenChanged: ((level: Int) -> Unit)?
) : RecyclerView.Adapter<BaseViewHolder<ViewBinding, PreferenceItem>>() {

    private var prefs: List<PreferenceItem> = preferences
    private var stack: Stack<Int> = Stack()
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
        stack.push(index)
        prefs = subScreen.preferences
        onScreenChanged()
    }

    internal fun onBackPressed(): Boolean {
        return if (stack.size > 0) {
            stack.pop()
            prefs = getCurrentSubScreenPreferences()
            onScreenChanged()
            true
        } else false
    }

    private fun onScreenChanged() {
        // rerun view animation
        recyclerView?.scheduleLayoutAnimation()
        notifyDataSetChanged()
        onScreenChanged?.invoke(stack.size)
    }

    private fun getCurrentSubScreenPreferences(): List<PreferenceItem> {
        if (stack.size == 0) {
            return preferences
        } else {
            var p = preferences[stack[0]] as SubScreen
            stack.asSequence().drop(1).forEach {
                p = p.preferences[it] as SubScreen
            }
            return p.preferences
        }
    }

    fun restoreState(stack: Stack<Int>) {
        if (stack != this.stack) {
            this.stack = stack
            prefs = getCurrentSubScreenPreferences()
            notifyDataSetChanged()
        }
    }

    fun getSavedState(): SavedState = SavedState(stack)

    @Parcelize
    data class SavedState(val stack: Stack<Int>) : Parcelable {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            return javaClass == other?.javaClass && stack == (other as SavedState).stack
        }

        override fun hashCode(): Int {
            return stack.hashCode()
        }
    }
}