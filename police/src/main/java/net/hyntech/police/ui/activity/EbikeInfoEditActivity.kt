package net.hyntech.police.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.view.TimePickerView
import com.blankj.utilcode.util.TimeUtils
import com.king.zxing.Intents
import net.hyntech.baselib.app.BaseApp
import net.hyntech.baselib.utils.Event
import net.hyntech.baselib.utils.LogUtils
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.base.BaseViewActivity
import net.hyntech.common.global.Constants
import net.hyntech.common.global.EventCode
import net.hyntech.common.model.entity.EbikeRegInfoEntity
import net.hyntech.common.model.entity.ServiceSafeEntity
import net.hyntech.common.model.entity.UserInfoEntity
import net.hyntech.common.model.vo.BundleEbikeEditVo
import net.hyntech.common.utils.CommonUtils
import net.hyntech.common.widget.imgloader.ImageLoader
import net.hyntech.common.widget.imgloader.TransType
import net.hyntech.common.widget.imgloader.glide.GlideImageConfig
import net.hyntech.police.R
import net.hyntech.common.R as CR
import net.hyntech.police.databinding.ActivityEbikeInfoEditBinding
import net.hyntech.police.vm.RegisterEditViewModel
import java.util.*

/**
 * 车辆信息修改
 */
class EbikeInfoEditActivity:BaseViewActivity<ActivityEbikeInfoEditBinding, RegisterEditViewModel>() {

    //车牌号
    private val ebikeList:MutableList<UserInfoEntity.EbikeListBean> = mutableListOf()
    private val ebikePickerView by lazy {
        OptionsPickerBuilder(this, OnOptionsSelectListener { options1, options2, options3, v ->
            LogUtils.logGGQ("options1-->${options1}")
            val ebike = ebikeList.get(options1)
            setEbikeInfo(ebike)
        }).apply {
            this.setContentTextSize(22)
            this.setTitleColor(UIUtils.getColor(CR.color.common_color_text))
            this.setCancelColor(UIUtils.getColor(CR.color.common_color_text))
            this.setSubmitColor(UIUtils.getColor(CR.color.common_color_text))
        }.build<UserInfoEntity.EbikeListBean>().apply {
            this.setTitleText("选择车牌号")
            this.setPicker(ebikeList)
        }
    }


    //车辆颜色
    private val ebikeColorList:MutableList<String> = mutableListOf()
    private val ebikeColorPickerView by lazy {
        OptionsPickerBuilder(this, OnOptionsSelectListener { options1, options2, options3, v ->
            LogUtils.logGGQ("options1-->${options1}")
            val color = ebikeColorList.get(options1)
            binding.tvColor.text = color

        }).apply {
            this.setContentTextSize(22)
            this.setTitleColor(UIUtils.getColor(CR.color.common_color_text))
            this.setCancelColor(UIUtils.getColor(CR.color.common_color_text))
            this.setSubmitColor(UIUtils.getColor(CR.color.common_color_text))
        }.build<String>().apply {
            this.setTitleText("选择车辆颜色")
            this.setPicker(ebikeColorList)
        }
    }

    //车辆类型
    private val ebikeTypeList:MutableList<EbikeRegInfoEntity.TypeBean> = mutableListOf()
    private val ebikeTypePickerView by lazy {
        OptionsPickerBuilder(this, OnOptionsSelectListener { options1, options2, options3, v ->
            LogUtils.logGGQ("options1-->${options1}")
            val entity = ebikeTypeList.get(options1)
            binding.tvEbikeType.text = entity.name

        }).apply {
            this.setContentTextSize(22)
            this.setTitleColor(UIUtils.getColor(CR.color.common_color_text))
            this.setCancelColor(UIUtils.getColor(CR.color.common_color_text))
            this.setSubmitColor(UIUtils.getColor(CR.color.common_color_text))
        }.build<EbikeRegInfoEntity.TypeBean>().apply {
            this.setTitleText("选择车辆类型")
            this.setPicker(ebikeTypeList)
        }
    }

    //购买日期
    private val buyTimePickerView: TimePickerView by lazy {
        TimePickerBuilder(this, object : OnTimeSelectListener {
            override fun onTimeSelect(date: Date?, v: View?) {
                if(date != null){
                    val time = TimeUtils.date2String(date, TimeUtils.getSafeDateFormat("yyyy-MM-dd"))
                    binding.tvBuyTime.text = time

                }
            }
        }).setType(booleanArrayOf(true, true, true, false, false, false))
            .setTitleText("选择购买日期")
            .setCancelColor(UIUtils.getColor(CR.color.common_color_gray))
            .setSubmitColor(UIUtils.getColor(CR.color.common_colorTheme))
            .setTitleBgColor(UIUtils.getColor(CR.color.common_white))
            .setTitleColor(UIUtils.getColor(CR.color.common_black))
            .setTextColorOut(UIUtils.getColor(CR.color.common_color_gray))
            .setBgColor(UIUtils.getColor(CR.color.common_default_background))
            .build()
    }


    //保障服务
    private val serviceList:MutableList<ServiceSafeEntity.ServicePackageListBean> = mutableListOf()
    private val servicePickerView by lazy {
        OptionsPickerBuilder(this, OnOptionsSelectListener { options1, options2, options3, v ->
            LogUtils.logGGQ("options1-->${options1}")
            binding.tvService.text = serviceList.get(options1).pickerViewText

        }).apply {
            this.setContentTextSize(20)
            this.setTitleColor(UIUtils.getColor(CR.color.common_color_text))
            this.setCancelColor(UIUtils.getColor(CR.color.common_color_text))
            this.setSubmitColor(UIUtils.getColor(CR.color.common_color_text))
        }.build<ServiceSafeEntity.ServicePackageListBean>().apply {
            this.setTitleText("请选择保障服务")
            this.setPicker(serviceList)
        }
    }

    private val viewModel by viewModels<RegisterEditViewModel>()



    override fun getLayoutId(): Int = R.layout.activity_ebike_info_edit

    override fun hasUsedEventBus(): Boolean = true

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }


    override fun initData(savedInstanceState: Bundle?) {
        setTitle<EbikeInfoEditActivity>(UIUtils.getString(CR.string.common_title_edit_info)).onBack<EbikeInfoEditActivity> {
            onFinish()
        }


        viewModel.defUI.showDialog.observe(this, Observer {
            showLoading()
        })

        viewModel.defUI.dismissDialog.observe(this, Observer {
            dismissLoading()
        })

        viewModel.defUI.toastEvent.observe(this, Observer {
            ToastUtil.showToast(it)
        })

        //车牌号
        this.findViewById<LinearLayout>(R.id.ll_ebike_no).setOnClickListener {
            onClickProxy {
                if(ebikeList.isNotEmpty() && !ebikePickerView.isShowing){
                    ebikePickerView.show()
                }
            }
        }

        //品牌型号
        this.findViewById<LinearLayout>(R.id.ll_brand).setOnClickListener {
            onClickProxy {
                startActivityForResult(Intent(this,EbikeBrandActivity::class.java),EventCode.EVENT_CODE_BRAND)
            }
        }

        //车辆颜色
        this.findViewById<LinearLayout>(R.id.ll_color).setOnClickListener {
            onClickProxy {
                if(ebikeColorList.isNotEmpty() && !ebikeColorPickerView.isShowing){
                    ebikeColorPickerView.show()
                }
            }
        }


        //车辆类型
        this.findViewById<LinearLayout>(R.id.ll_ebike_type).setOnClickListener {
            onClickProxy {
                if(ebikeTypeList.isNotEmpty() && !ebikeTypePickerView.isShowing){
                    ebikeTypePickerView.show()
                }
            }
        }

        //购买日期
        this.findViewById<LinearLayout>(R.id.ll_buy_time).setOnClickListener {
            onClickProxy {
                if(!buyTimePickerView.isShowing){
                    buyTimePickerView.show()
                }
            }
        }

        //保障服务
        this.findViewById<LinearLayout>(R.id.ll_service).setOnClickListener {
            onClickProxy {
                if(serviceList.isNotEmpty() && !servicePickerView.isShowing){
                    servicePickerView.show()
                }
            }
        }

    }


    override fun <T> onStickyEventBusDispense(event: Event<T>) {
        super.onStickyEventBusDispense(event)
        when(event.code){
            EventCode.EVENT_CODE_EDIT ->{
                LogUtils.logGGQ("--event code:--${event.code}")
                (event.data as BundleEbikeEditVo)?.let {
                    setDataList(it)
                }
            }
        }
    }

    private fun setDataList(vo: BundleEbikeEditVo) {
        ebikeColorList.clear()
        ebikeTypeList.clear()
        serviceList.clear()
        ebikeList.clear()

        ebikeColorList.addAll(vo.colorList)
        ebikeTypeList.addAll(vo.typeList)
        serviceList.addAll(vo.serviceList)
        ebikeList.addAll(vo.ebikeList)


        setEbikeInfo(ebikeList.get(vo.pos))

    }


    private fun setEbikeInfo(info:UserInfoEntity.EbikeListBean){
        binding.tvEbikeNo.text = "${info.ebikeNo}"
        binding.etLabelNo.setText("${info.locatorNo}")
        binding.etFrameNo.setText("${info.frameNo}")
        binding.tvBrand.text = "${info.ebikeType}"
        binding.etEngineNo.setText("${info.engineNo}")
        binding.tvColor.text = "${info.ebikeColor}"
        binding.tvEbikeType.text = "${info.typeName}"
        binding.etRemark.setText("${info.remark}")
        binding.etBuyPrice.setText("${info.price/100}")
        binding.tvBuyTime.text = "${CommonUtils.splitDate(info.buyTime)}"
        binding.tvService.text = "${info.insuranceProductName} ${info.termRange}年版(${info.insurancePrice/100}元)"
        ImageLoader.getInstance().apply {
            //车辆照片
            this.loadImage(BaseApp.instance, GlideImageConfig(info.ebikePic1, binding.ivEbikeA).also { config-> config.type = TransType.NORMAL })
            this.loadImage(BaseApp.instance, GlideImageConfig(info.ebikePic2, binding.ivEbikeB).also { config-> config.type = TransType.NORMAL })
            //标签照片
            this.loadImage(BaseApp.instance, GlideImageConfig(info.locatorPic, binding.ivLabelLoc).also { config-> config.type = TransType.NORMAL })
            //发票照片
            this.loadImage(BaseApp.instance, GlideImageConfig(info.invoicePic, binding.ivInvoice).also { config-> config.type = TransType.NORMAL })
        }

    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            when(requestCode){
                EventCode.EVENT_CODE_BRAND ->{
                    val name = data?.getStringExtra(Constants.BundleKey.EXTRA_CONTENT)
                    name?.let {
                        binding.tvBrand.text = name
                    }
                }
            }
        }
    }

}