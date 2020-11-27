package net.hyntech.baselib.utils

import android.text.TextUtils
import com.blankj.utilcode.util.ToastUtils as Toast

class ToastUtil {
    companion object {
        @JvmStatic
        fun showToast(s: String?) {
            if (!TextUtils.isEmpty(s)) {
//                Toast.setBgColor(ColorUtils.getColor(R.color.color_toast))
                Toast.showShort(s)
            }
        }
    }
}


