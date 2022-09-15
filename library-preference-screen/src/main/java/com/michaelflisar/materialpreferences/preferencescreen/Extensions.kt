package com.michaelflisar.materialpreferences.preferencescreen

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.util.Log
import android.util.TypedValue
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.view.OneShotPreDrawListener
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.suspendCancellableCoroutine


internal fun Context.isUsingDarkTheme(): Boolean {
    val color = resolve(android.R.attr.colorBackground)
    val darkness =
        1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255
    return darkness > 0.5
}

internal val Context.colorAccent: Int
    @ColorInt get() = resolve(R.attr.colorAccent)

internal fun Context.resolve(attrId: Int): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(attrId, typedValue, true)
    return typedValue.data
}

internal val Int.dpToPx: Int get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

internal suspend fun RecyclerView.awaitViewHolder(index: Int): RecyclerView.ViewHolder {
    val vh = findViewHolderForAdapterPosition(index)
    // If view holder is already visible => return it
    if (vh != null)
        return vh

    // view holder may become visible by a layout change or by a scroll => so we wait
    // and scroll to index
    return suspendCancellableCoroutine { cont ->

        var listener: RecyclerView.OnScrollListener? = null
        var listener2: View.OnLayoutChangeListener? = null
        val cleanup = {
            listener?.let { removeOnScrollListener(it) }
            listener2?.let { removeOnLayoutChangeListener(it) }
        }
        val check = {
            val vh = findViewHolderForAdapterPosition(index)
            if (vh != null) {
                cleanup()
                cont.resume(vh, null)
            }
        }


        listener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                //Log.d("INDEX", "newState: $newState")
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    check()
                }
            }
        }
        listener2 =
            View.OnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
                //Log.d("INDEX", "layout changed")
                check()
            }

        // if the coroutine is cancelled, remove the listeners
        cont.invokeOnCancellation { cleanup() }
        // finally add the listeners
        addOnScrollListener(listener)
        addOnLayoutChangeListener(listener2)
        // force scroll to index
        scrollToPosition(index)
    }
}