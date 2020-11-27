package net.hyntech.common.app

import android.content.Context
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshFooter
import com.scwang.smart.refresh.layout.api.RefreshHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.DefaultRefreshFooterCreator
import com.scwang.smart.refresh.layout.listener.DefaultRefreshHeaderCreator
import net.hyntech.baselib.app.BaseApp
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.R


abstract class CommonApp:BaseApp() {

   init {
       SmartRefreshLayout.setDefaultRefreshHeaderCreator(object :DefaultRefreshHeaderCreator{
           override fun createRefreshHeader(
               context: Context,
               layout: RefreshLayout
           ): RefreshHeader {
               layout.setPrimaryColorsId(R.color.common_white,R.color.common_colorTheme)
               return ClassicsHeader(context)
           }
       })

       SmartRefreshLayout.setDefaultRefreshFooterCreator(object :DefaultRefreshFooterCreator{
           override fun createRefreshFooter(
               context: Context,
               layout: RefreshLayout
           ): RefreshFooter {
               return ClassicsFooter(context).setDrawableSize(18f).setAccentColor(UIUtils.getColor(R.color.common_colorTheme))
           }
       })
   }


}