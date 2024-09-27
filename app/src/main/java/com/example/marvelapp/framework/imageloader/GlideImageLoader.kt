package com.example.marvelapp.framework.imageloader

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import javax.inject.Inject

//@Inject constructor() - estamos ensinando o hilt a como instancia um GlideImageHolder pelo construtor.
class GlideImageLoader @Inject constructor() : ImageLoader {
    override fun load(
        imageView: ImageView,
        imageUrl: String,
        @DrawableRes placeholder: Int,
        @DrawableRes fallback: Int
    ) {
        Glide.with(imageView.rootView)
            .load(imageUrl)
            .placeholder(placeholder)
            .fallback(fallback)
            .into(imageView)
    }
}