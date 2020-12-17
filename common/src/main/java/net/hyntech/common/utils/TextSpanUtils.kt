package net.hyntech.common.utils

import android.graphics.drawable.Drawable
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ImageSpan

class TextSpanUtils {
    companion object {
        fun spanIconEnd(s: String, d: Drawable): SpannableString {
            val ss:SpannableString = SpannableString("${s}-  ")
            val len = ss.length
            d.setBounds(0, 0,d.intrinsicWidth,d.intrinsicHeight)
            val span :ImageSpan = ImageSpan(d,ImageSpan.ALIGN_BASELINE)
            ss.setSpan(span,len - 1, len, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
            return ss
        }
    }
}