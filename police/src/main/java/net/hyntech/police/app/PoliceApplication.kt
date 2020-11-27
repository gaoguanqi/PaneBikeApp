package net.hyntech.police.app

import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import net.hyntech.baselib.app.BaseApp
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.R

class PoliceApplication : BaseApp() {

    companion object {
        @JvmStatic
        lateinit var instance: PoliceApplication
            private set
    }

    init {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setPrimaryColorsId(R.color.common_white, R.color.common_colorTheme)
            ClassicsHeader(context)
        }

        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout -> ClassicsFooter(context).setDrawableSize(18f).setAccentColor(
            UIUtils.getColor(R.color.common_colorTheme)) }
    }

    override fun getAppPackage(): String = this.packageName

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}