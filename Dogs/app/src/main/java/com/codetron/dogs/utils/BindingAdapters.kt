package com.codetron.dogs.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.palette.graphics.Palette
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.codetron.dogs.R

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("app:imgUrl")
    fun ImageView.setImageUrl(url: String?) {
        Glide.with(this)
            .applyDefaultRequestOptions(
                RequestOptions()
                    .placeholder(getProgressDrawable(context))
                    .error(R.color.teal_700)
            )
            .load(url)
            .into(this)
    }

    private fun getProgressDrawable(context: Context): CircularProgressDrawable {
        return CircularProgressDrawable(context).apply {
            setColorSchemeColors(R.color.teal_200, R.color.purple_500)
            strokeWidth = 10f
            centerRadius = 50f
            start()
        }
    }

    @JvmStatic
    @BindingAdapter("app:paletteColor")
    fun View.setPaletteColor(url: String?) {
        Glide.with(this)
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    Palette.from(resource)
                        .generate { palette ->
                            val intColor = palette?.vibrantSwatch?.rgb
                            intColor?.let { setBackgroundColor(it) }
                        }
                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }
            })
    }

}