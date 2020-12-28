package net.hyntech.police.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import net.hyntech.baselib.utils.*
import net.hyntech.common.base.BaseFragment
import net.hyntech.common.global.EventCode
import net.hyntech.common.model.entity.EbikeRegInfoEntity
import net.hyntech.common.model.entity.GenderEntity
import net.hyntech.common.model.entity.UserInfoEntity
import net.hyntech.police.R
import net.hyntech.common.R as CR
import net.hyntech.police.databinding.FragmentRegEbikeInfoBinding
import net.hyntech.police.ui.activity.EbikeBrandActivity
import net.hyntech.police.ui.activity.EbikeRegisterActivity
import net.hyntech.police.vm.EbikeRegisterViewModel

class RegEbikeInfoFragment(val viewModel: EbikeRegisterViewModel):BaseFragment<FragmentRegEbikeInfoBinding,EbikeRegisterViewModel>() {
    private lateinit var act: EbikeRegisterActivity

    //车辆颜色
    private val ebikeColorList:MutableList<String> = mutableListOf()
    private val ebikeColorPickerView by lazy {
        OptionsPickerBuilder(act, OnOptionsSelectListener { options1, options2, options3, v ->
            LogUtils.logGGQ("options1-->${options1}")

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

            this.findViewById<LinearLayout>(R.id.ll_branch).setOnClickListener {
                onClickProxy {
                    startActivity(Intent(requireContext(),EbikeBrandActivity::class.java))
                }
            }

            this.findViewById<LinearLayout>(R.id.ll_color).setOnClickListener {
                onClickProxy {
                    if(ebikeColorList.isNotEmpty() && !ebikeColorPickerView.isShowing){
                        ebikeColorPickerView.show()
                    }
                }
            }

            this.findViewById<LinearLayout>(R.id.ll_type).setOnClickListener {
                onClickProxy {
                    if(ebikeTypeList.isNotEmpty() && !ebikeTypePickerView.isShowing){
                        ebikeTypePickerView.show()
                    }
                }
            }

            this.findViewById<Button>(R.id.btn_commit).setOnClickListener {
                onClickProxy {

                }
            }
        }
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
        }

        viewModel.servicePackage?.let { service ->

        }
    }

}