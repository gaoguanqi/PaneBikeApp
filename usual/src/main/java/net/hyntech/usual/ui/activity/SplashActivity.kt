package net.hyntech.usual.ui.activity

import android.content.Intent
import android.os.Bundle
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.SPUtils
import com.tbruyelle.rxpermissions2.RxPermissions
import net.hyntech.baselib.utils.PermissionUtil
import net.hyntech.baselib.utils.RequestPermission
import net.hyntech.common.base.BaseActivity
import net.hyntech.common.ui.activity.LoginActivity
import net.hyntech.common.ui.activity.WelcomeActivity
import net.hyntech.common.widget.dialog.CommonDialog
import net.hyntech.common.global.Constants
import net.hyntech.usual.R

class SplashActivity : BaseActivity() {
    private val rxPermissions: RxPermissions = RxPermissions(this)
    private var dialog: CommonDialog? = null

    override fun getLayoutId(): Int = R.layout.activity_splash


    override fun initData(savedInstanceState: Bundle?) {
        applyPermissions()
    }



    override fun onRestart() {
        super.onRestart()
        applyPermissions()
    }

    private fun applyPermissions() {
        PermissionUtil.applyPermissions(object : RequestPermission {
            override fun onRequestPermissionSuccess() {
                launchTarget()
            }

            override fun onRequestPermissionFailure(permissions: List<String>) {
                showLoading()
            }

            override fun onRequestPermissionFailureWithAskNeverAgain(permissions: List<String>) {
                showLoading()
            }
        }, rxPermissions)
    }

    private fun launchTarget() {
        if(SPUtils.getInstance(this.packageName).getBoolean(Constants.SaveInfoKey.HAS_WELCOME_USUAL,false)){
            startActivity(Intent(this, LoginActivity::class.java).putExtra(Constants.GlobalValue.BUILD_TYPE,Constants.BundleKey.EXTRA_USUAL))
        }else{
            startActivity(Intent(this, WelcomeActivity::class.java).putExtra(Constants.GlobalValue.BUILD_TYPE,Constants.BundleKey.EXTRA_USUAL))
        }
        onFinish()
    }

    override fun showLoading() {
        dialog ?: CommonDialog(this,"权限申请","请开启必要权限,否则您无法正常使用一些功能","不同意","前往开启",object :CommonDialog.OnClickListener{
            override fun onCancleClick() {
                onFinish()
            }
            override fun onConfirmClick() {
                AppUtils.launchAppDetailsSettings()
            }
        }).let {
            if (!it.isShowing) it.show()
        }
    }

    override fun dismissLoading() {
        dialog?.cancel()
    }


}