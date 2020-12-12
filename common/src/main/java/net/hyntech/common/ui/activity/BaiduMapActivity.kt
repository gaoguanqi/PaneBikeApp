package net.hyntech.common.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.R
import net.hyntech.common.base.BaseActivity
import net.hyntech.common.provider.ARouterConstants

@Route(path = ARouterConstants.BAIDU_MAP_PAGE)
class BaiduMapActivity:BaseActivity() {

    override fun getLayoutId(): Int = R.layout.activity_baidu_map

    override fun initData(savedInstanceState: Bundle?) {
        setTitle<BaiduMapActivity>(UIUtils.getString(R.string.common_title_baidu_map)).onBack<BaiduMapActivity> {
            onFinish()
        }
    }
}