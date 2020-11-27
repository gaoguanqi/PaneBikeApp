package net.hyntech.common.widget.imgloader.glide

import android.widget.ImageView
import net.hyntech.common.R
import net.hyntech.common.widget.imgloader.ImageConfig
import net.hyntech.common.widget.imgloader.TransType
import net.hyntech.common.widget.imgloader.listener.ProgressListener

class GlideImageConfig: ImageConfig {
    var overWidth:Int = 0
    var overHeight:Int = 0

    var valueBlur:Int = 0
    var valueRound:Int = 0

    var progressListener: ProgressListener? = null
    var type: TransType = TransType.NORMAL

    constructor(any:Any, imageView: ImageView, progressListener: ProgressListener? = null, placeholder:Int = R.drawable.ic_default_placeholder, errorPic:Int = R.drawable.ic_default_errorpic){
        this.any = any
        this.imageView = imageView
        this.progressListener = progressListener
        this.placeholder = placeholder
        this.errorPic = errorPic
    }
}