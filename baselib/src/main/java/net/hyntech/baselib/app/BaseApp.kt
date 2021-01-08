package net.hyntech.baselib.app

import android.app.Application
import androidx.lifecycle.ViewModelStoreOwner
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.Utils
import com.xuexiang.xupdate.XUpdate
import com.xuexiang.xupdate.entity.UpdateError
import com.xuexiang.xupdate.listener.OnUpdateFailureListener
import me.jessyan.autosize.utils.AutoSizeLog
import net.hyntech.baselib.app.config.Config
import net.hyntech.baselib.error.GlobalCrashHandler
import net.hyntech.baselib.utils.LogUtils
import net.hyntech.baselib.utils.OKHttpUpdateHttpService


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

    abstract fun getVersionCode():Int


    override fun onCreate() {
        super.onCreate()
        if(!Config.CONFIG_DEBUG){
            GlobalCrashHandler.init(this, "307590625@qq.com")
        }
        instance = this
        Utils.init(this)
        SPUtils.getInstance(getAppPackage())

        if (AutoSizeLog.isDebug()) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog()     // 打印日志
            ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this) // 尽可能早，推荐在Application中初始化
        initUpdate()
    }

    private fun initUpdate(){
        XUpdate.get()
            .debug(true)
            .isWifiOnly(true)                                               //默认设置只在wifi下检查版本更新
            .isGet(true)                                                    //默认设置使用get请求检查版本
            .isAutoMode(false)
            .setOnUpdateFailureListener(object :OnUpdateFailureListener{
                override fun onFailure(error: UpdateError?) {
                    error?.let {
                        if(error.code != UpdateError.ERROR.CHECK_NO_NEW_VERSION){
                            LogUtils.logGGQ(it.message)
                        }
                    }
                }
            }).supportSilentInstall(true)
            .setIUpdateHttpService(OKHttpUpdateHttpService())
            .init(this)
    }
}