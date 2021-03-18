package com.michaelflisar.materialpreferences.preferencescreen.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.constraintlayout.widget.ConstraintLayout
import com.michaelflisar.materialpreferences.preferencescreen.isUsingDarkTheme

class SettingsRootView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    companion object {

        // material design: disabled text color is white with 50% opacity
        val PAINT_DARK_THEME = Paint().apply {
            val filter: ColorFilter = PorterDuffColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
            colorFilter = filter
            alpha = 128
        }

        // material design: disabled text color is black with 38% opacity
        val PAINT_LIGHT_THEME = Paint().apply {
            val filter: ColorFilter = PorterDuffColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP)
            colorFilter = filter
            alpha = 97
        }
    }

    private var darkTheme = false
    private var viewIsEnabled = true

    init {
        darkTheme = context.isUsingDarkTheme()
    }

    fun setViewState(enabled: Boolean) {
        if (viewIsEnabled != enabled) {
            viewIsEnabled = enabled
            invalidate()
        }
    }

    private fun getPaint() = if (darkTheme) PAINT_DARK_THEME else PAINT_LIGHT_THEME

    override fun dispatchDraw(canvas: Canvas) {
        if (!viewIsEnabled) {
            canvas.saveLayer(null, getPaint(), Canvas.ALL_SAVE_FLAG)
        }
        super.dispatchDraw(canvas)
        if (!viewIsEnabled) {
            canvas.restore()
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        // consume all touches if disabled
        return if (!viewIsEnabled) {
            true
        } else super.dispatchTouchEvent(ev)
    }
}