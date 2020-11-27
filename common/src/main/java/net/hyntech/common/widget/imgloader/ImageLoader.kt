package net.hyntech.common.widget.imgloader

import android.content.Context
import net.hyntech.common.widget.imgloader.glide.GlideImageLoaderStrategy

class ImageLoader <T : ImageConfig> {

    private var mStrategy: BaseImageLoaderStrategy<T>? = null

    companion object {
        private var INSTANCE: ImageLoader<ImageConfig>? = null
        fun getInstance(strategy: BaseImageLoaderStrategy<ImageConfig> = GlideImageLoaderStrategy() as BaseImageLoaderStrategy<ImageConfig>): ImageLoader<ImageConfig> {
            if(INSTANCE == null){
                INSTANCE = ImageLoader(strategy)
            }
            return INSTANCE!!
        }
    }

    private constructor(strategy: BaseImageLoaderStrategy<T>) {
        this.mStrategy = strategy
    }


    fun loadImage(ctx: Context, config: T) {
        this.mStrategy?.loadImage(ctx, config);
    }
}