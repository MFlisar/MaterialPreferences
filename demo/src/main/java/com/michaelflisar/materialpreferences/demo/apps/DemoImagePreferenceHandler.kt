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
 * this handler saves the activity name of a selected app
 */
object DemoImagePreferenceHandler : ImagePreferenceHandler<String> {

    override fun displayData(data: String, imageView: ImageView) {
        val activityName = data
        // slow, ineffective and dangerous, but good enough for demo purposes
        (imageView.context as LifecycleOwner).lifecycleScope.launch(Dispatchers.IO) {
            val apps = AppsManager.load(imageView.context)
            val app = apps.find { it.app.resolveInfo.activityInfo.name == activityName }
            val ri = app?.app?.resolveInfo
            val icon = ri?.loadIcon(imageView.context.packageManager)
            withContext(Dispatchers.Main) {
                imageView.setImageDrawable(icon)
            }
        }
    }

    override fun createDialog(setting: StorageSetting<String>): MaterialDialogSetup<*, *, *> {
        return DialogList(
            -1,
            title = "Image".asText(),
            buttonPositive = android.R.string.ok.asText(),
            // Important!!! this will make handling of this dialog event automatically filtered
            // and only this preference will receive its result!!
            extra = DialogExtra(setting.key),
            items = DialogList.Items.Loader(AppsManager),
            selectionMode = DialogList.SelectionMode.SingleClick,
            filter = DialogList.Filter(
                searchInText = true,
                searchInSubText = true,
                highlight = true, // highlights search term in items
                algorithm = DialogList.Filter.Algorithm.String, // either search for items containing all words or the search term as a whole
                ignoreCase = true,
                unselectInvisibleItems = true // true means, items are unselected as soon as they are filtered out and get invisible for the user
            )
        )
    }

    override fun onDialogEvent(event: IMaterialDialogEvent): String? {
        val e = event as DialogList.Event
        return if (e is DialogList.Event.Result) {
            val item = e.selectedItems.firstOrNull() as? AppListItem
            val ri = item?.app?.resolveInfo
            ri?.activityInfo?.name
        } else null
    }
}