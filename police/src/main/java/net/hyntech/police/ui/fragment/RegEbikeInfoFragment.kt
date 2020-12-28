package net.hyntech.police.ui.fragment

import android.os.Bundle
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
            binding.tvColor.text = ebikeColorList.get(options1)
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
            binding.tvType.text = ebikeTypeList.get(options1).name
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

                }
            }
        }

        viewModel.brandName.observe(this, Observer {
            binding.tvBrand.text = it
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



}