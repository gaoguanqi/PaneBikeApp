package net.hyntech.police.ui.activity

import android.content.Intent
import android.os.Bundle
import com.blankj.utilcode.util.SPUtils
import net.hyntech.common.base.BaseActivity
import net.hyntech.ebike.app.global.Constants
import net.hyntech.police.R

class SplashActivity : BaseActivity() {
    override fun getLayoutId(): Int = R.layout.activity_splash

    override fun initData(savedInstanceState: Bundle?) {
        if(SPUtils.getInstance(this.packageName).getBoolean(Constants.SaveInfoKey.HAS_WECLOME_POLICE,false)){
            startActivity(Intent(this,HomeActivity::class.java))
        }else{
            startActivity(Intent(this,WeclomeActivity::class.java))
        }
        onFinish()
    }


}