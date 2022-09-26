package com.michaelflisar.materialpreferences.preferencescreen.image

import android.widget.ImageView
import com.michaelflisar.dialogs.MaterialDialogSetup
import com.michaelflisar.dialogs.interfaces.IMaterialDialogEvent
import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting

interface ImagePreferenceHandler<T: Any> {
    fun displayData(data: T, imageView: ImageView)
    fun createDialog(setting: StorageSetting<T>): MaterialDialogSetup<*, *, *>
    fun onDialogEvent(event: IMaterialDialogEvent): T?
}