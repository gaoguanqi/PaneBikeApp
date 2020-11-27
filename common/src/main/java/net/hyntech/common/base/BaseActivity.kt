package net.hyntech.common.base

import android.view.View
import kotlinx.android.synthetic.main.include_title.*
import net.hyntech.common.widget.dialog.LoadingDialog
import net.hyntech.baselib.base.BaseActivity as B

abstract class BaseActivity : B() {

    private var loadingDialog: LoadingDialog? = null


    open fun showLoading() {
        loadingDialog ?: LoadingDialog(this).let {
            if (!it.isShowing) it.show()
        }
    }

    open fun dismissLoading() {
        loadingDialog?.cancel()
    }


    fun <T : BaseActivity> setTitle(txt: String): T {
        tv_title?.text = txt
        return this as T
    }

    fun <T : BaseActivity> setLeftTxt(txt: String): T {
        tv_left?.visibility = View.VISIBLE
        tv_left?.text = txt
        return this as T
    }

    fun <T : BaseActivity> setRightTxt(txt: String): T {
        tv_right?.visibility = View.VISIBLE
        tv_right?.text = txt
        return this as T
    }

    fun <T : BaseActivity> onBack(m: () -> Unit): T {
        ll_left?.setOnClickListener { m() }
        return this as T
    }

    fun <T : BaseActivity> onSide(m: () -> Unit): T {
        ll_right?.setOnClickListener { m() }
        return this as T
    }
}