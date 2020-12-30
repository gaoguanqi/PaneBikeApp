package net.hyntech.baselib.app

import android.app.Application
import androidx.lifecycle.ViewModelStoreOwner
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.Utils
import me.jessyan.autosize.utils.AutoSizeLog

abstract class BaseApp : Application(), ViewModelStoreOwner {

    companion object {
        @JvmStatic
        lateinit var instance: BaseApp
            private set
    }

    abstract fun getAppPackage():String

    private var buildType:String? = null

    fun getBuildType():String? = buildType
    fun setBuildType(v:String?){
        this.buildType = v
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        Utils.init(this)
        SPUtils.getInstance(getAppPackage())

        if (AutoSizeLog.isDebug()) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog()     // 打印日志
            ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this) // 尽可能早，推荐在Application中初始化
    }


}