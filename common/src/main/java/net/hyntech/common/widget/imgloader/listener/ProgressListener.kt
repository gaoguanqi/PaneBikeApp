package net.hyntech.common.widget.imgloader.listener

import net.hyntech.baselib.utils.LogUtils


interface ProgressListener {
    /**
     * 图片加载进度回调
     */
    fun onLoadProgress(isDone:Boolean,progress:Int)

    /**
     * 加载失败
     */
    fun onLoadFailed()

    fun onLoadStart(){
        LogUtils.logGGQ("listener->>> onLoadStart")
    }

}