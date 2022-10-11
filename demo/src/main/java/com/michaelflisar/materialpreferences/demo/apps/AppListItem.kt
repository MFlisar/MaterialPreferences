package com.michaelflisar.materialpreferences.demo.apps

import android.widget.ImageView
import com.michaelflisar.dialogs.interfaces.IListItem
import com.michaelflisar.text.Text
import com.michaelflisar.text.asText
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class AppListItem(
    val index: Int,
    val app: App
) : IListItem {

    override val listItemId = index.toLong()

    @IgnoredOnParcel
    override val text: Text = app.name.asText()

    @IgnoredOnParcel
    override val subText: Text = (app.resolveInfo.activityInfo.packageName ?: "").asText()

    override fun displayIcon(imageView: ImageView): Boolean {
        return app.display(imageView)
    }

    override fun toString(): String {
        return app.name
    }
}