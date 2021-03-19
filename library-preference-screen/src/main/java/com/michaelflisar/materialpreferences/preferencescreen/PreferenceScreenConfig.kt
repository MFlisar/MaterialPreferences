package com.michaelflisar.materialpreferences.preferencescreen

import com.michaelflisar.materialpreferences.preferencescreen.enums.NoIconVisibility

object PreferenceScreenConfig {

    /**
     * defines the maximum number of allowed line for titles
     *
     * Default: 1
     */
    var maxLinesTitle: Int = 1

    /**
     * defines the maximum number of allowed line for summaries
     *
     * Default: 3
     */
    var maxLinesSummary: Int = 3

    /**
     * defines the default dialog styles (custom preferences must make sure to respect this flag!)
     *
     * Default: false
     */
    var bottomSheet: Boolean = false

    /**
     * defines the default behaviour for items without an icon
     *
     * Default: Invisible
     */
    var noIconVisibility: NoIconVisibility = NoIconVisibility.Invisible

    /**
     * defines if icons should be aligned with a toolbars back arrow or not
     *
     * Default: false
     */
    var alignIconsWithBackArrow: Boolean = false
}