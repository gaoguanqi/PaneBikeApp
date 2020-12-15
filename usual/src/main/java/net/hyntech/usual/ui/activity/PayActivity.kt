package net.hyntech.usual.ui.activity

import android.os.Bundle
import android.widget.RadioButton
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.base.BaseViewActivity
import net.hyntech.common.global.Constants
import net.hyntech.usual.R
import net.hyntech.common.R as CR
import net.hyntech.usual.databinding.ActivityPayBinding
import net.hyntech.usual.vm.ControllerViewModel

class PayActivity:BaseViewActivity<ActivityPayBinding,ControllerViewModel>() {

    private var radioZfb:RadioButton? = null
    private var radioWx:RadioButton? = null
    private var payOption:Int = 0
    private val viewModel by viewModels<ControllerViewModel>()


    override fun bindViewModel() {
        binding.viewModel = viewModel
    }

    override fun getLayoutId(): Int = R.layout.activity_pay

    override fun initData(savedInstanceState: Bundle?) {
        setTitle<PayActivity>(UIUtils.getString(CR.string.common_title_pay)).onBack<PayActivity> {
            onFinish()
        }

        val price = getBundleString(Constants.BundleKey.EXTRA_PRICE)
        radioZfb = findViewById(R.id.rbtn_zfb)
        radioWx = findViewById(R.id.rbtn_wx)
        radioZfb?.isChecked = true
        radioWx?.isChecked = false
        binding.tvPrice.text = "￥${price}"

        binding.llPayZfb?.setOnClickListener {
            radioWx?.isChecked = false
            radioZfb?.isChecked = true
            payOption = 0
        }

        binding.llPayWx?.setOnClickListener {
            radioZfb?.isChecked = false
            radioWx?.isChecked = true
            payOption = 1
        }

        binding.btnPay.setOnClickListener {
            if(!UIUtils.isFastDoubleClick()){
                if(payOption == 1){
                    //微信支付
                    ToastUtil.showToast("微信支付${price}")
                }else{
                    //支付宝支付
                    ToastUtil.showToast("支付宝支付${price}")
                }
            }
        }
    }
}