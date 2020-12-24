package net.hyntech.common.widget.dialog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.baselib.widget.dialog.BaseDialogFragment
import net.hyntech.common.R
import net.hyntech.common.databinding.DialogTipsBinding

class TipsDialog(private val title:String = UIUtils.getString(R.string.common_tip)
,private val cancle:String = UIUtils.getString(R.string.common_text_cancle)
,private val confirm:String = UIUtils.getString(R.string.common_text_confirm)
,private val content:String = ""
,private val listener: OnClickListener
) :BaseDialogFragment<DialogTipsBinding>() {

    override fun getLayoutId(): Int = R.layout.dialog_tips

    override fun initFragment(view: View, savedInstanceState: Bundle?) {
        binding.tvTitle.text = title
        binding.btnCancle.apply {
            this.text = cancle
            this.setOnClickListener {
                this@TipsDialog.dialog?.cancel()
                listener.onCancleClick()
            }
        }
        binding.btnConfirm.apply {
            this.text = confirm
            this.setOnClickListener {
                this@TipsDialog.dialog?.cancel()
                listener.onConfirmClick()
            }
        }
        binding.tvContent.text = content
    }


    fun setContentText(s:String){
        binding.tvContent.text = s
    }


    interface OnClickListener{
        fun onCancleClick()
        fun onConfirmClick()
    }
}