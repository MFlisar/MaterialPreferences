package com.michaelflisar.materialpreferences.demo.apps


import android.content.pm.ResolveInfo
import android.os.Parcelable
import android.widget.ImageView
import kotlinx.parcelize.Parcelize

@Parcelize
class App(
    val name: String,
    val resolveInfo: ResolveInfo
) : Parcelable {

    fun display(imageView: ImageView): Boolean {
        // slow, but good enough for the demo... using something like Glide/Coil/Picasso would work here as well
        val icon = resolveInfo.loadIcon(imageView.context.packageManager)
        imageView.setImageDrawable(icon)
        return icon != null
    }
}