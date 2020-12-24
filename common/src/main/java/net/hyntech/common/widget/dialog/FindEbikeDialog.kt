package net.hyntech.common.widget.dialog

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.baselib.widget.dialog.BaseDialogFragment
import net.hyntech.common.R
import net.hyntech.common.databinding.DialogFindEbikeBinding

class FindEbikeDialog(private val listener: OnClickListener
) : BaseDialogFragment<DialogFindEbikeBinding>() {

    private val span: ForegroundColorSpan by lazy { ForegroundColorSpan(Color.parseColor("#38B7FE")) }

    lateinit var id:String
    lateinit var address: String
    override fun getLayoutId(): Int = R.layout.dialog_find_ebike

    override fun initFragment(view: View, savedInstanceState: Bundle?) {
        binding.btnConfirm.setOnClickListener {
            listener.onConfirmClick()
        }
        binding.llAddress.setOnClickListener {
            if(!UIUtils.isFastDoubleClick()){
                listener.onAddressClick()
            }
        }
    }

    fun setNoText(no:String){
        val title:String = "车牌号为${no}已找到"
        val ss: SpannableString = SpannableString(title)
        ss.setSpan(span,0,no.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        binding.tvTitle.text = ss
    }

    fun setAddText(address:String){
        this.address = address
        binding.tvAddress.text = address
    }

    interface OnClickListener{
        fun onConfirmClick()
        fun onAddressClick()
    }
}