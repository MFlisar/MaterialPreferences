package com.michaelflisar.materialpreferences.demo.apps

import android.widget.ImageView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.michaelflisar.dialogs.DialogList
import com.michaelflisar.dialogs.MaterialDialogSetup
import com.michaelflisar.dialogs.interfaces.IMaterialDialogEvent
import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting
import com.michaelflisar.materialpreferences.preferencescreen.DialogExtra
import com.michaelflisar.materialpreferences.preferencescreen.image.ImagePreferenceHandler
import com.michaelflisar.text.asText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/*
 * this handler saves the INDEX of a selected app - this does not really make sense, but shows, that you can use ANY data as base
 * for the image preference
 */
object DemoImagePreferenceHandler2 : ImagePreferenceHandler<Int> {

    override fun displayData(data: Int, imageView: ImageView) {
        val selectedAppIndex = data
        if (selectedAppIndex < 0) {
            imageView.setImageDrawable(null)
            return
        }
        // slow, ineffective and dangerous, but good enough for demo purposes
        (imageView.context as LifecycleOwner).lifecycleScope.launch(Dispatchers.IO) {
            val apps = AppsManager.load(imageView.context)
            val app = if (selectedAppIndex < apps.size) apps[selectedAppIndex] else null
            val ri = app?.app?.resolveInfo
            val icon = ri?.loadIcon(imageView.context.packageManager)
            withContext(Dispatchers.Main) {
                imageView.setImageDrawable(icon)
            }
        }
    }

    override fun createDialog(setting: StorageSetting<Int>): MaterialDialogSetup<*, *, *> {
        return DialogList(
            -1,
            title = "Image".asText(),
            buttonPositive = android.R.string.ok.asText(),
            // Important!!! this will make handling of this dialog event automatically filtered
            // and only this preference will receive its result!!
            extra = DialogExtra(setting.key),
            items = DialogList.Items.Loader(AppsManager),
            selectionMode = DialogList.SelectionMode.SingleClick,
            filter = null
        )
    }

    override fun onDialogEvent(event: IMaterialDialogEvent): Int? {
        val e = event as DialogList.Event
        return if (e is DialogList.Event.Result) {
            val item = e.selectedItems.firstOrNull() as? AppListItem
            item?.index
        } else null
    }
}