package net.hyntech.police.ui.activity

import android.content.Intent
import android.os.Bundle
import com.blankj.utilcode.util.SPUtils
import net.hyntech.common.base.BaseActivity
import net.hyntech.common.ui.activity.LoginActivity
import net.hyntech.common.ui.activity.WelcomeActivity
import net.hyntech.common.global.Constants
import net.hyntech.police.R

class SplashActivity : BaseActivity() {
    override fun getLayoutId(): Int = R.layout.activity_splash

    override fun initData(savedInstanceState: Bundle?) {
        if(SPUtils.getInstance(this.packageName).getBoolean(Constants.SaveInfoKey.HAS_WELCOME_POLICE,false)){
            startActivity(Intent(this, LoginActivity::class.java).putExtra(Constants.GlobalValue.BUILD_TYPE,Constants.BundleKey.EXTRA_POLICE))
        }else{
            startActivity(Intent(this,WelcomeActivity::class.java).putExtra(Constants.GlobalValue.BUILD_TYPE,Constants.BundleKey.EXTRA_POLICE))
        }
        onFinish()
    }


}