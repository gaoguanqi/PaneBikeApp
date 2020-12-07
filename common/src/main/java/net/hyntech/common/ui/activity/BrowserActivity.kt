package net.hyntech.common.ui.activity

import android.content.res.Configuration
import android.graphics.PixelFormat
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import net.hyntech.baselib.utils.LogUtils
import net.hyntech.common.R
import net.hyntech.common.base.BaseActivity
import net.hyntech.common.global.Constants
import net.hyntech.common.provider.ARouterConstants
import net.hyntech.common.widget.x5web.WebViewJavaScriptFunction
import net.hyntech.common.widget.x5web.X5WebView


@Route(path = ARouterConstants.BROWSER_PAGE)
class BrowserActivity : BaseActivity() {

    private var webView: X5WebView? = null
    private var title:String = ""
    private var webURL:String = ""

    override fun getLayoutId(): Int = R.layout.activity_browser

    override fun initData(savedInstanceState: Bundle?) {

        this.intent?.extras?.let {
            title = it.getString(Constants.BundleKey.EXTRA_TITLE,"")
            webURL = it.getString(Constants.BundleKey.EXTRA_URL,"")
        }

        setTitle<BrowserActivity>(title).onBack<BrowserActivity>{
            onFinish()
        }

        LogUtils.logGGQ("title--->>>${title}")
        LogUtils.logGGQ("webURL--->>>${webURL}")


        webView = findViewById(R.id.webView)
        webView?.apply {
            this.loadUrl(webURL)
            window?.setFormat(PixelFormat.TRANSLUCENT)
            this.view?.overScrollMode = View.OVER_SCROLL_ALWAYS
            this.addJavascriptInterface(object :WebViewJavaScriptFunction{
                override fun onJsFunctionCalled(tag: String?) {

                }
            },"Html2PhoneUtils")
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        try {
            super.onConfigurationChanged(newConfig)
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) { } else if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) { }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        webView?.destroy()
        super.onDestroy();
    }


}