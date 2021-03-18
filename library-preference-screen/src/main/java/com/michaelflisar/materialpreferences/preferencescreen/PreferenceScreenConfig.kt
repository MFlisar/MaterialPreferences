package com.michaelflisar.materialpreferences.preferencescreen

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
}