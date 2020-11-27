package net.hyntech.common.widget.imgloader

import android.content.Context

interface BaseImageLoaderStrategy <in T : ImageConfig> {
    fun loadImage(ctx: Context, config:T)
}