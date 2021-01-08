package net.hyntech.usual.ui.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.lifecycle.Observer
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import net.hyntech.baselib.utils.LogUtils
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.base.BaseViewActivity
import net.hyntech.common.global.Constants
import net.hyntech.common.model.entity.SafeInfoEntity
import net.hyntech.common.utils.CommonUtils
import net.hyntech.usual.R
import net.hyntech.common.R as CR
import net.hyntech.usual.databinding.ActivitySafeInfoBinding
import net.hyntech.usual.vm.ControllerViewModel

class SafeInfoActivity:BaseViewActivity<ActivitySafeInfoBinding,ControllerViewModel>() {

    private var id:String ? = ""
    private var content:String ? = ""

    private val viewModel by viewModels<ControllerViewModel>()

    private val span: ForegroundColorSpan by lazy { ForegroundColorSpan(Color.parseColor("#333333")) }

    private val pickerList:MutableList<String> = mutableListOf()
    private val pickerView by lazy {
        OptionsPickerBuilder(this, OnOptionsSelectListener { options1, options2, options3, v ->
            LogUtils.logGGQ("options1-->${options1}")
            viewModel.safeInfoList.value?.let {
                if(it.size > 1){
                    setData(it.get(options1))
                }else{
                    LogUtils.logGGQ("只有一辆车，不用更新")
                }
            }
        }).apply {
            this.setContentTextSize(22)
            this.setTitleColor(UIUtils.getColor(CR.color.common_color_text))
            this.setCancelColor(UIUtils.getColor(CR.color.common_color_text))
            this.setSubmitColor(UIUtils.getColor(CR.color.common_color_text))
        }.build<String>().apply {
            this.setTitleText("选择车辆")
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_safe_info

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }

    override fun initData(savedInstanceState: Bundle?) {
        setTitle<SafeInfoActivity>(UIUtils.getString(CR.string.common_title_safe_info)).onBack<SafeInfoActivity> {
            onFinish()
        }

        id = getBundleString(Constants.BundleKey.EXTRA_ID)
        content = getBundleString(Constants.BundleKey.EXTRA_CONTENT)

        viewModel.defUI.showDialog.observe(this, Observer {
            showLoading()
        })

        viewModel.defUI.dismissDialog.observe(this, Observer {
            dismissLoading()
        })

        viewModel.defUI.toastEvent.observe(this, Observer {
            ToastUtil.showToast(it)
        })

        viewModel.safeInfoList.observe(this, Observer {
            it?.let {
                pickerList.clear()
                it.forEach {item ->
                    pickerList.add(item.ebike.ebikeNo)
                }
                setData(it.first())
            }
        })

        binding.btnNoDown.setOnClickListener {
            if(!UIUtils.isFastDoubleClick() && pickerList.isNotEmpty()){
                showPicker()
            }
        }
        viewModel.getSafeInfo(id)
    }

    private val priceTip:String = "服务费用："
    private fun setData(entity: SafeInfoEntity.ListBean?) {
        entity?.let {
            binding.tvEbikeNo.text = "${it.ebike.ebikeNo}"
            binding.tvLabel.text = "${it.ebike.locatorNo}"
            binding.tvName.text = "${it.ebike.userName}"
            binding.tvPhone.text = "${it.ebike.phone}"
            binding.tvIdcard.text = CommonUtils.coverIDCard(it.ebike.idNo)
            binding.tvFrameNo.text = "${it.ebike.frameNo}"
            binding.tvMotor.text = "${it.ebike.engineNo}"
            binding.tvBrand.text = "${it.ebike.ebikeType}"
            binding.tvColor.text = "${it.ebike.ebikeColor}"
            binding.tvType.text = "${it.ebike.typeName}"
            binding.tvBuyPrice.text = "${it.ebike.price/100}"
            binding.tvBuyTime.text = CommonUtils.splitDate(it.ebike.buyTime)
            binding.tvRemark.text = "${it.ebike.remark}"
            binding.tvSafe.text = "${it.insuranceProductName}"
            binding.tvRange.text = "${it.beginTimeInsurance}至${it.endTimeInsurance}"
            binding.tvLimit.text = "${content}"
            //服务费用 insurancePrice/100
            val content:String = "${priceTip}￥${it.insurancePrice/100}"
            val ss: SpannableString = SpannableString(content)
            ss.setSpan(span,0,priceTip.length,Spannable.SPAN_INCLUSIVE_INCLUSIVE)
            binding.tvPrice.text = ss

            if(it.ebike.payState == 1){
                //点击投保
                binding.tvSubmit.setBackgroundColor(UIUtils.getColor(CR.color.common_color_text))
                binding.tvSubmit.setOnClickListener { v->
                    onSubmit(it)
                }
            }else{
                //不可点击 灰色
                binding.tvSubmit.setBackgroundColor(UIUtils.getColor(CR.color.common_color_gray))
                binding.tvSubmit.setOnClickListener {
                    LogUtils.logGGQ("当前状态->>不可投保")
                }
            }
        }
    }

    private fun showPicker() {
        pickerView.setPicker(pickerList)
        if(!pickerView.isShowing){
            pickerView.show()
        }
    }

    private fun onSubmit(item: SafeInfoEntity.ListBean) {

        //val price = (bean.insurancePrice/100).toString()
        //startActivity(Intent(this,PayActivity::class.java).putExtra(Constants.BundleKey.EXTRA_PRICE,price))

//        val ebikeId = item.ebike.ebikeId
//        val orderId = item.ebike.orderId
//        val valueId = item.s
//        startActivity(Intent(this@SafeInfoActivity,PayActivity::class.java).putExtra(Constants.BundleKey.EXTRA_PAY_EBIKEID,ebikeId).putExtra(Constants.BundleKey.EXTRA_PAY_ORDERID,orderId).putExtra(Constants.BundleKey.EXTRA_PAY_VALUEID,valueId))

        ToastUtil.showToast("去支付")
    }
}