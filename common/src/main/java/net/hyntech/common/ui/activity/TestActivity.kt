package net.hyntech.common.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ToastUtils
import net.hyntech.common.R
import net.hyntech.common.base.BaseActivity
import net.hyntech.common.provider.ARouterConstants
import net.hyntech.common.provider.IDemoProvider

@Route(path = ARouterConstants.TEST_PAGE)
class TestActivity:BaseActivity() {


    override fun getLayoutId(): Int = R.layout.activity_test


    override fun initData(savedInstanceState: Bundle?) {
        val demo:IDemoProvider? = ARouter.getInstance().build(ARouterConstants.DEMO_OBJ).navigation() as IDemoProvider
        ToastUtils.showLong(demo?.getObj())

    }
}