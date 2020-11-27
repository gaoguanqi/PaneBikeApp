package net.hyntech.usual.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import com.blankj.utilcode.util.SPUtils
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.ebike.app.global.Constants
import net.hyntech.usual.R

class HomeActivity : AppCompatActivity() {

    private var lastBackPressedMillis: Long = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (lastBackPressedMillis + 2000 > System.currentTimeMillis()) {
                //moveTaskToBack(true)
                this@HomeActivity.finish()
            } else {
                lastBackPressedMillis = System.currentTimeMillis()
                ToastUtil.showToast("再按一次退出程序")
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}
