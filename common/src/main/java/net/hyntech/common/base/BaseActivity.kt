package net.hyntech.common.base

import android.view.View
import kotlinx.android.synthetic.main.include_title.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.widget.dialog.LoadingDialog
import net.hyntech.baselib.base.BaseActivity as B

abstract class BaseActivity : B() {

    private var loadingDialog: LoadingDialog? = null


    open fun showLoading() {
        if (loadingDialog == null) {
            loadingDialog = this?.let {
                LoadingDialog(this)
            }
        }
        loadingDialog?.show()

    }

    open fun dismissLoading() {
        loadingDialog?.run { if (isShowing) {
            runBlocking {
                delay(500L)
            }
            dismiss()
        } }
    }


    inline fun <reified T : BaseActivity> setTitle(txt: String): T {
        tv_title?.text = txt
        return this as T
    }

    inline fun <reified T : BaseActivity> setLeftTxt(txt: String): T {
        tv_left?.visibility = View.VISIBLE
        tv_left?.text = txt
        return this as T
    }

    inline fun <reified T : BaseActivity> setRightTxt(txt: String): T {
        tv_right?.visibility = View.VISIBLE
        tv_right?.text = txt
        return this as T
    }

    inline fun <reified T : BaseActivity> onBack(crossinline m: () -> Unit): T {
        ll_left?.setOnClickListener { if(!UIUtils.isFastDoubleClick()){m()} }
        return this as T
    }

    inline fun <reified T : BaseActivity> onSide(crossinline m: () -> Unit): T {
        ll_right?.setOnClickListener { if(!UIUtils.isFastDoubleClick()){m()} }
        return this as T
    }
}