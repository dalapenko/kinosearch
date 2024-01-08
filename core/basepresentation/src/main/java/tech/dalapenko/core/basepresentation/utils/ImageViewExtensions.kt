package tech.dalapenko.core.basepresentation.utils

import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.CropTransformation

fun ImageView.loadImage(imageUrl: String?, transform: ImageTransform = ImageTransform.CropCenter()) {
    if (imageUrl == null) return

    when(transform) {
        is CustomCrop -> {
            loadImage(imageUrl, CropTransformation(width, height, transform.type))
        }
        is GlideTransform -> {
            loadImage(imageUrl, transform.type)
        }
    }
}


private fun ImageView.loadImage(imageUrl: String?, transform: Transformation<Bitmap>) {
    if (imageUrl == null) return

    Glide.with(context)
        .load(imageUrl)
        .apply(RequestOptions.bitmapTransform(transform))
        .into(this)
}