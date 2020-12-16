package net.hyntech.usual.ui.activity

import android.os.Bundle
import android.widget.ImageView
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

    private var ivZfb:ImageView? = null
    private var ivWx:ImageView? = null
    private var payOption:Int = 0
    private val viewModel by viewModels<ControllerViewModel>()
    private val draUnselect by lazy { UIUtils.getDrawable(CR.drawable.ic_pay_unselect) }
    private val draSelected by lazy { UIUtils.getDrawable(CR.drawable.ic_pay_selected) }

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }

    override fun getLayoutId(): Int = R.layout.activity_pay

    override fun initData(savedInstanceState: Bundle?) {
        setTitle<PayActivity>(UIUtils.getString(CR.string.common_title_pay)).onBack<PayActivity> {
            onFinish()
        }

        val price = getBundleString(Constants.BundleKey.EXTRA_PRICE)
        ivZfb = findViewById(R.id.iv_zfb)
        ivWx = findViewById(R.id.iv_wx)
        binding.tvPrice.text = "￥${price}"
        //默认选中
        ivZfb?.background = draSelected
        payOption = 0

        binding.llPayZfb?.setOnClickListener {
            ivWx?.background = draUnselect
            ivZfb?.background = draSelected
            payOption = 0
        }

        binding.llPayWx?.setOnClickListener {
            ivZfb?.background = draUnselect
            ivWx?.background = draSelected
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