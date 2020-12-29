package net.hyntech.police.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.lifecycle.Observer
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.view.TimePickerView
import com.blankj.utilcode.util.TimeUtils
import net.hyntech.baselib.app.BaseApp
import net.hyntech.baselib.utils.*
import net.hyntech.common.base.BaseFragment
import net.hyntech.common.model.entity.EbikeRegInfoEntity
import net.hyntech.common.model.entity.ServiceSafeEntity
import net.hyntech.common.widget.imgloader.ImageLoader
import net.hyntech.common.widget.imgloader.TransType
import net.hyntech.common.widget.imgloader.glide.GlideImageConfig
import net.hyntech.police.R
import net.hyntech.common.R as CR
import net.hyntech.police.databinding.FragmentRegEbikeInfoBinding
import net.hyntech.police.ui.activity.EbikeRegisterActivity
import net.hyntech.police.vm.EbikeRegisterViewModel
import java.util.*

class RegEbikeInfoFragment(val viewModel: EbikeRegisterViewModel):BaseFragment<FragmentRegEbikeInfoBinding,EbikeRegisterViewModel>() {
    private lateinit var act: EbikeRegisterActivity

    //车辆颜色
    private val ebikeColorList:MutableList<String> = mutableListOf()
    private val ebikeColorPickerView by lazy {
        OptionsPickerBuilder(act, OnOptionsSelectListener { options1, options2, options3, v ->
            LogUtils.logGGQ("options1-->${options1}")
            val color = ebikeColorList.get(options1)
            binding.tvColor.text = color
            viewModel.ebikeInfoMap.put("ebikeColor",color)
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
        OptionsPickerBuilder(act, OnOptionsSelectListener { options1, options2, options3, v ->
            LogUtils.logGGQ("options1-->${options1}")
            val entity = ebikeTypeList.get(options1)
            binding.tvType.text = entity.name
            viewModel.ebikeInfoMap.put("type",entity.value)
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
        TimePickerBuilder(act, object : OnTimeSelectListener {
            override fun onTimeSelect(date: Date?, v: View?) {
                if(date != null){
                    val time = TimeUtils.date2String(date, TimeUtils.getSafeDateFormat("yyyy-MM-dd"))
                    binding.tvBuyTime.text = time
                    viewModel.ebikeInfoMap.put("buyTime",time)
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
        OptionsPickerBuilder(act, OnOptionsSelectListener { options1, options2, options3, v ->
            LogUtils.logGGQ("options1-->${options1}")
            binding.tvService.text = serviceList.get(options1).pickerViewText
            //servicePackageOrgId
            viewModel.ebikeInfoMap.put("servicePackageOrgId",serviceList.get(options1).servicePackageOrgId)

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

    companion object {
        fun getInstance(viewModel: EbikeRegisterViewModel): RegEbikeInfoFragment {
            return RegEbikeInfoFragment(viewModel)
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_reg_ebike_info

    override fun initData(savedInstanceState: Bundle?) {
        act = activity as EbikeRegisterActivity

        view?.apply {
            this.findViewById<TextView>(R.id.tv_title).text = UIUtils.getString(CR.string.common_title_ebike_info)
            this.findViewById<LinearLayout>(R.id.ll_left).setOnClickListener {
                onClickProxy {
                    //返回
                    act.onBack()
                }
            }

            //扫一扫
            this.findViewById<ImageButton>(R.id.btn_scan).setOnClickListener {
                onClickProxy {
                    act.startScan()
                }
            }

            //品牌型号
            this.findViewById<LinearLayout>(R.id.ll_brand).setOnClickListener {
                onClickProxy {
                    act.startEbikeBrand()
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
            this.findViewById<LinearLayout>(R.id.ll_type).setOnClickListener {
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

            //车辆正面照
            this.findViewById<ImageView>(R.id.iv_ebike_a).setOnClickListener {
                onClickProxy {
                    act.applyCamera(3)
                }
            }

            //车辆反面照
            this.findViewById<ImageView>(R.id.iv_ebike_b).setOnClickListener {
                onClickProxy {
                    act.applyCamera(4)
                }
            }

            //标签安装位置
            this.findViewById<ImageView>(R.id.iv_label_loc).setOnClickListener {
                onClickProxy {
                    act.applyCamera(5)
                }
            }

            //购车发票
            this.findViewById<ImageView>(R.id.iv_invoice).setOnClickListener {
                onClickProxy {
                    act.applyCamera(6)
                }
            }


            this.findViewById<Button>(R.id.btn_commit).setOnClickListener {
                onClickProxy {
                    onEbikeInfoCommit()
                }
            }
        }

        viewModel.brandName.observe(this, Observer {
            binding.tvBrand.text = it
        })

        viewModel.scanCode.observe(this, Observer {
            binding.etLabel.setText(it)
        })

        viewModel.ebikeAPath.observe(this, Observer {
            ImageLoader.getInstance().loadImage(BaseApp.instance, GlideImageConfig(it, binding.ivEbikeA).also { config-> config.type = TransType.NORMAL })
        })
        viewModel.ebikeBPath.observe(this, Observer {
            ImageLoader.getInstance().loadImage(BaseApp.instance, GlideImageConfig(it, binding.ivEbikeB).also { config-> config.type = TransType.NORMAL })
        })
        viewModel.labelPath.observe(this, Observer {
            ImageLoader.getInstance().loadImage(BaseApp.instance, GlideImageConfig(it, binding.ivLabelLoc).also { config-> config.type = TransType.NORMAL })
        })
        viewModel.invoicePath.observe(this, Observer {
            ImageLoader.getInstance().loadImage(BaseApp.instance, GlideImageConfig(it, binding.ivInvoice).also { config-> config.type = TransType.NORMAL })
        })
    }

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }

    override fun lazyLoadData() {
        super.lazyLoadData()
        LogUtils.logGGQ("----lazyLoadData------")
        viewModel.userInfo.value?.let {user ->

        }

        viewModel.ebikeRegInfo?.let { ebike ->
            ebikeColorList.addAll(ebike.ebikeColor)
            ebikeTypeList.addAll(ebike.type)
            binding.tvLimit.text = ebike.insurance_coverange
        }

        viewModel.servicePackage?.let { service ->
            serviceList.addAll(service.servicePackageList)
        }
    }


    private val imegList = mutableListOf<String>()
    private fun onEbikeInfoCommit(){
        val label =  binding.etLabel.text.toString().trim()
        if(TextUtils.isEmpty(label)){
            ToastUtil.showToast("请输入绑定的标签号")
            return
        }
        viewModel.ebikeInfoMap.put("locatorNo",label)

        val ebikeNo =  binding.etEbikeNo.text.toString().trim()
        if(TextUtils.isEmpty(ebikeNo)){
            ToastUtil.showToast("请输入车牌号")
            return
        }
        viewModel.ebikeInfoMap.put("ebikeNo",ebikeNo)

        val frameNo =  binding.etFrameNo.text.toString().trim()
        if(TextUtils.isEmpty(frameNo)){
            ToastUtil.showToast("请输入车架号")
            return
        }
        viewModel.ebikeInfoMap.put("frameNo",frameNo)


        val brandName = viewModel.brandName.value
        if(TextUtils.isEmpty(brandName)){
            ToastUtil.showToast("请选择品牌型号")
            return
        }
        viewModel.ebikeInfoMap.put("ebikeType",brandName!!)



        val engine =  binding.etEngine.text.toString().trim()
        if(TextUtils.isEmpty(engine)){
            ToastUtil.showToast("请输入电机号")
            return
        }
        viewModel.ebikeInfoMap.put("engineNo",engine)


        if(TextUtils.isEmpty(viewModel.ebikeInfoMap.get("ebikeColor"))){
            ToastUtil.showToast("请选择车辆颜色")
            return
        }

        if(TextUtils.isEmpty(viewModel.ebikeInfoMap.get("type"))){
            ToastUtil.showToast("请选择车辆类型")
            return
        }

        val buyPrice = binding.etPrice.text.toString().trim()
        if(TextUtils.isEmpty(buyPrice)){
            ToastUtil.showToast("请输入购买价格")
            return
        }
        //用户输入的购买价格(用户输入的是元,后台需要传分)
        val price = buyPrice.toDouble()*100
        viewModel.ebikeInfoMap.put("price",(price.toLong()).toString())


        if(TextUtils.isEmpty(viewModel.ebikeInfoMap.get("buyTime"))){
            ToastUtil.showToast("请选择购买日期")
            return
        }

        if(TextUtils.isEmpty(viewModel.ebikeInfoMap.get("servicePackageOrgId"))){
            ToastUtil.showToast("请选择保障服务")
            return
        }

        val remark= binding.etRemark.text.toString().trim()
        if(TextUtils.isEmpty(remark)){
            viewModel.ebikeInfoMap.put("remark","")
        }else{
            viewModel.ebikeInfoMap.put("remark",remark)
        }

        val ebikeAPath = viewModel.ebikeAPath.value
        if(TextUtils.isEmpty(ebikeAPath)){
            ToastUtil.showToast("请上传车辆正面照")
            return
        }
        val ebikeBPath = viewModel.ebikeBPath.value
        if(TextUtils.isEmpty(ebikeBPath)){
            ToastUtil.showToast("请上传车辆反面照")
            return
        }

        val labelPath = viewModel.labelPath.value
        if(TextUtils.isEmpty(labelPath)){
            ToastUtil.showToast("请上传标签安装位置照片")
            return
        }

        val invoicePath = viewModel.invoicePath.value
        if(TextUtils.isEmpty(invoicePath)){
            ToastUtil.showToast("请上传购车发票/其他凭证")
            return
        }

        imegList.clear()
        imegList.add(ebikeAPath!!)
        imegList.add(ebikeBPath!!)
        imegList.add(labelPath!!)
        imegList.add(invoicePath!!)

        viewModel.uploadEbikeImageList(imegList.toList())
    }


}