package com.dev_candra.myapplication.method

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import de.hdodenhof.circleimageview.CircleImageView

fun CircleImageView.loadImage(url: String){
    Glide.with(this.context)
        .load(url)
        .apply(RequestOptions().override(500,500))
        .into(this)
}