package net.hyntech.usual.ui.activity

import android.content.Intent
import android.os.Bundle
import com.blankj.utilcode.util.SPUtils
import kotlinx.android.synthetic.main.activity_weclome.*
import net.hyntech.common.base.BaseActivity
import net.hyntech.common.ui.adapter.WeclomeAdapter
import net.hyntech.ebike.app.global.Constants
import net.hyntech.usual.R

class WeclomeActivity:BaseActivity() {
    override fun getLayoutId(): Int = R.layout.activity_weclome

    override fun initData(savedInstanceState: Bundle?) {
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
        SPUtils.getInstance(this.packageName).put(Constants.SaveInfoKey.HAS_WECLOME_USUAL,true)
        startActivity(Intent(this, LoginActivity::class.java))
        onFinish()
    }
}