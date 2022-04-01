package com.sorto.eduardosortoswapidev.view.detail.hero

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.sorto.eduardosortoswapidev.models.SWModel
import com.sorto.eduardosortoswapidev.viewmodels.SWImage


abstract class HeroLayout(context: Context) : RelativeLayout(context) {

    lateinit var model: SWModel
    lateinit var title: TextView
    lateinit var subtitle: TextView

    fun updateHeroImage(imageView: ImageView, imagePath: String, fallback: Int) {

        // placeholder
        val requestOptions = RequestOptions()
                .placeholder(SWImage.getPlaceholderImage(model.categoryId))
                .error(fallback)

        // load image and fade in
        Glide.with(this)
                .setDefaultRequestOptions(requestOptions)
                .load(Uri.parse(imagePath))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView)
    }

}

