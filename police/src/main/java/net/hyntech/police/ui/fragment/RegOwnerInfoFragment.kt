package net.hyntech.police.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.view.TimePickerView
import com.blankj.utilcode.util.RegexUtils
import com.blankj.utilcode.util.TimeUtils
import net.hyntech.baselib.utils.LogUtils
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.base.BaseFragment
import net.hyntech.common.model.entity.EbikeRegInfoEntity
import net.hyntech.common.model.entity.GenderEntity
import net.hyntech.police.R
import net.hyntech.common.R as CR
import net.hyntech.police.databinding.FragmentRegOwnerInfoBinding
import net.hyntech.police.ui.activity.EbikeRegisterActivity
import net.hyntech.police.vm.EbikeRegisterViewModel
import java.util.*

class RegOwnerInfoFragment(val viewModel: EbikeRegisterViewModel):BaseFragment<FragmentRegOwnerInfoBinding,EbikeRegisterViewModel>() {

    private lateinit var act: EbikeRegisterActivity
    //性别
    private val genderList:List<GenderEntity> = listOf(GenderEntity("男","1"),GenderEntity("女","2"))
    private val genderPickerView by lazy {
        OptionsPickerBuilder(act, OnOptionsSelectListener { options1, options2, options3, v ->
            LogUtils.logGGQ("options1-->${options1}")
            val entity:GenderEntity = genderList.get(options1)
            binding.tvGender.text = entity.key.toString()
            viewModel.ownerInfoMap.put("gender",entity.value)
        }).apply {
            this.setContentTextSize(22)
            this.setTitleColor(UIUtils.getColor(CR.color.common_color_text))
            this.setCancelColor(UIUtils.getColor(CR.color.common_color_text))
            this.setSubmitColor(UIUtils.getColor(CR.color.common_color_text))
        }.build<GenderEntity>().apply {
            this.setTitleText("选择性别")
            this.setPicker(genderList)
        }
    }

    //出生年月日
    private val birthdayPickerView: TimePickerView by lazy {
        TimePickerBuilder(act, object : OnTimeSelectListener {
            override fun onTimeSelect(date: Date?, v: View?) {
                if(date != null){
                    val birthday = TimeUtils.date2String(date,TimeUtils.getSafeDateFormat("yyyy-MM-dd"))
                    binding.tvBirthday.text = birthday
                    viewModel.ownerInfoMap.put("birthday",birthday)
                }
            }
        }).setType(booleanArrayOf(true, true, true, false, false, false))
            .setTitleText("选择出生年月日")
            .setCancelColor(UIUtils.getColor(CR.color.common_color_gray))
            .setSubmitColor(UIUtils.getColor(CR.color.common_colorTheme))
            .setTitleBgColor(UIUtils.getColor(CR.color.common_white))
            .setTitleColor(UIUtils.getColor(CR.color.common_black))
            .setTextColorOut(UIUtils.getColor(CR.color.common_color_gray))
            .setBgColor(UIUtils.getColor(CR.color.common_default_background))
            .build()
    }

    private val userTypeList:MutableList<EbikeRegInfoEntity.UserIdTypeBean> = mutableListOf()
    private val userTypePickerView by lazy {
        OptionsPickerBuilder(act, OnOptionsSelectListener { options1, options2, options3, v ->
            LogUtils.logGGQ("options1-->${options1}")
            val entity:EbikeRegInfoEntity.UserIdTypeBean = userTypeList.get(options1)
            binding.tvType.text = entity.name
            viewModel.ownerInfoMap.put("userType",entity.value)
        }).apply {
            this.setContentTextSize(22)
            this.setTitleColor(UIUtils.getColor(CR.color.common_color_text))
            this.setCancelColor(UIUtils.getColor(CR.color.common_color_text))
            this.setSubmitColor(UIUtils.getColor(CR.color.common_color_text))
        }.build<EbikeRegInfoEntity.UserIdTypeBean>().apply {
            this.setTitleText("选择车主身份")
            this.setPicker(userTypeList)
        }
    }

    companion object {
        fun getInstance(viewModel: EbikeRegisterViewModel): RegOwnerInfoFragment {
            return RegOwnerInfoFragment(viewModel)
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_reg_owner_info

    override fun initData(savedInstanceState: Bundle?) {
        act = activity as EbikeRegisterActivity
        view?.apply {
            this.findViewById<TextView>(R.id.tv_title).text = UIUtils.getString(CR.string.common_title_owner_info)
            this.findViewById<LinearLayout>(R.id.ll_left).setOnClickListener {
                onClickProxy {
                    //返回
                    act.onBack()
                }
            }

            this.findViewById<LinearLayout>(R.id.ll_gender).setOnClickListener {
                onClickProxy {
                    if(!genderPickerView.isShowing){
                        genderPickerView.show()
                    }
                }
            }
            this.findViewById<LinearLayout>(R.id.ll_birthday).setOnClickListener {
                onClickProxy {
                    if(!birthdayPickerView.isShowing){
                        birthdayPickerView.show()
                    }
                }
            }
            this.findViewById<LinearLayout>(R.id.ll_type).setOnClickListener {
                onClickProxy {
                    if(userTypeList.isNotEmpty() && !userTypePickerView.isShowing){
                        userTypePickerView.show()
                    }
                }
            }


            this.findViewById<Button>(R.id.btn_next).setOnClickListener {
                onClickProxy {
                  onOwnerInfoNext()
                }
            }
        }


    }

    private fun onOwnerInfoNext() {
       val idCard =  binding.etIdcard.text.toString().trim()
        if(TextUtils.isEmpty(idCard) || idCard.length < 15){
            ToastUtil.showToast("请输入有效的身份证号码")
            return
        }

        viewModel.ownerInfoMap.put("idCard",idCard)


        val userName =  binding.etName.text.toString().trim()
        if(TextUtils.isEmpty(userName)){
            ToastUtil.showToast("请输入车主姓名")
            return
        }
        viewModel.ownerInfoMap.put("userName",userName)


        if(TextUtils.isEmpty(viewModel.ownerInfoMap.get("gender"))){
            ToastUtil.showToast("请选择车主性别")
            return
        }

        val address =  binding.etAddress.text.toString().trim()
        if(TextUtils.isEmpty(address)){
            ToastUtil.showToast("请输入现居住地址")
            return
        }
        viewModel.ownerInfoMap.put("address",address)

        val userPhone =  binding.etPhone.text.toString().trim()
        if(TextUtils.isEmpty(userPhone) || !RegexUtils.isMobileSimple(userPhone)){
            ToastUtil.showToast("请输入有效的手机号码")
            return
        }
        viewModel.ownerInfoMap.put("userPhone",userPhone)


        if(TextUtils.isEmpty(viewModel.ownerInfoMap.get("userType"))){
            ToastUtil.showToast("请选择车主身份")
            return
        }


        val userWork =  binding.etWork.text.toString().trim()
        if(!TextUtils.isEmpty(userWork)){
            viewModel.ownerInfoMap.put("userWork",userWork)
        }

        val crashName =  binding.etCrashName.text.toString().trim()
        if(!TextUtils.isEmpty(userWork)){
            viewModel.ownerInfoMap.put("crashName",crashName)
        }

        val crashPhone =  binding.etCrashPhone.text.toString().trim()
        if(!TextUtils.isEmpty(crashPhone)){
            viewModel.ownerInfoMap.put("crashPhone",crashPhone)
        }

        viewModel.ownerInfoMap.forEach {
            LogUtils.logGGQ("--->${it.key}---${it.value}")
        }

        act.onNextByIndex(2)
    }

    override fun lazyLoadData() {
        super.lazyLoadData()
        LogUtils.logGGQ("----lazyLoadData------")
        viewModel.userInfo.value?.let {user ->
            binding.tvOrgName.text = user.policeName
        }


        viewModel.ebikeRegInfo?.let { ebike ->
            val userTpye = ebike.userIdType.first()
            binding.tvType.text = userTpye.name
            viewModel.ownerInfoMap.put("userType",userTpye.value)

            userTypeList.addAll(ebike.userIdType)
        }

        viewModel.servicePackage?.let { service ->

        }
    }

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }
}