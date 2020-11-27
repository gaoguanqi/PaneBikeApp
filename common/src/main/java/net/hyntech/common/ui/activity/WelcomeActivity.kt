package net.hyntech.common.ui.activity

import android.content.Intent
import android.os.Bundle
import com.blankj.utilcode.util.SPUtils
import kotlinx.android.synthetic.main.activity_welcome.*
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.common.R
import net.hyntech.common.base.BaseActivity
import net.hyntech.common.ui.adapter.WeclomeAdapter
import net.hyntech.common.global.Constants

class WelcomeActivity:BaseActivity() {
    override fun getLayoutId(): Int = R.layout.activity_welcome

    override fun initData(savedInstanceState: Bundle?) {

        ToastUtil.showToast(buildType)

        val list: Array<Int> = arrayOf(
            R.drawable.welcome1,
            R.drawable.welcome2,
            R.drawable.welcome3,
            R.drawable.welcome4
        )
        vp.adapter = WeclomeAdapter(this, list, object : WeclomeAdapter.OnClickListener {
            override fun onItemClick(position: Int) {
                if (list.size == (position + 1)) {
                    launchTarget()
                }
            }
        })
    }

    private fun launchTarget() {
        when(buildType){
            Constants.BundleKey.EXTRA_USUAL -> {
                SPUtils.getInstance(this.packageName).put(Constants.SaveInfoKey.HAS_WELCOME_USUAL,true)
            }

            Constants.BundleKey.EXTRA_POLICE -> {
                SPUtils.getInstance(this.packageName).put(Constants.SaveInfoKey.HAS_WELCOME_POLICE,true)
            }
        }
        startActivity(Intent(this, LoginActivity::class.java).putExtra(Constants.GlobalValue.BUILD_TYPE,buildType))
        onFinish()
    }
}