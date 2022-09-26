package com.michaelflisar.materialpreferences.preferencescreen

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class DialogExtra(
    val key: String,
    val extra: Parcelable? = null
) : Parcelable