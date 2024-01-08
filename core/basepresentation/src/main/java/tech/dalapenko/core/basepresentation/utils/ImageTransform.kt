package tech.dalapenko.core.basepresentation.utils

import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.FitCenter as GlideFitCenter
import jp.wasabeef.glide.transformations.CropTransformation

internal sealed interface CustomCrop {
    val type: CropTransformation.CropType
}

internal sealed interface GlideTransform {
    val type: BitmapTransformation
}

sealed interface ImageTransform {

    class CropTop(
        override val type: CropTransformation.CropType = CropTransformation.CropType.TOP
    ) : ImageTransform, CustomCrop
    class CropCenter(
        override val type: BitmapTransformation = CenterCrop()
    ) : ImageTransform, GlideTransform
    class FitCenter(
        override val type: BitmapTransformation = GlideFitCenter()
    ) : ImageTransform, GlideTransform
}