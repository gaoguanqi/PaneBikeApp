package net.hyntech.police.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import net.hyntech.baselib.app.BaseApp
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.base.BaseFragment
import net.hyntech.common.widget.imgloader.ImageLoader
import net.hyntech.common.widget.imgloader.TransType
import net.hyntech.common.widget.imgloader.glide.GlideImageConfig
import net.hyntech.police.R
import net.hyntech.common.R as CR
import net.hyntech.police.databinding.FragmentRegIdcardBinding
import net.hyntech.police.ui.activity.EbikeRegisterActivity
import net.hyntech.police.ui.activity.RecordPendingActivity
import net.hyntech.police.vm.EbikeRegisterViewModel

class RegIdcardFragment(val viewModel: EbikeRegisterViewModel):BaseFragment<FragmentRegIdcardBinding,EbikeRegisterViewModel>(){

    private lateinit var act: EbikeRegisterActivity
    private val imgList = java.util.ArrayList<String>()

    override fun getLayoutId(): Int = R.layout.fragment_reg_idcard

    companion object {
        fun getInstance(viewModel: EbikeRegisterViewModel): RegIdcardFragment {
            return RegIdcardFragment(viewModel)
        }
    }


    override fun initData(savedInstanceState: Bundle?) {
        act = activity as EbikeRegisterActivity

        view?.apply {
            this.findViewById<TextView>(R.id.tv_title).text = UIUtils.getString(CR.string.common_title_ebike_register)
            this.findViewById<TextView>(R.id.tv_right).text = "已有信息在册"
            this.findViewById<LinearLayout>(R.id.ll_left).setOnClickListener {
                onClickProxy {
                    //返回
                    act.onFinish()
                }
            }
            this.findViewById<LinearLayout>(R.id.ll_right).setOnClickListener {
                onClickProxy {
                    ToastUtil.showToast("已有信息在册")
                }
            }
        }

        binding.ivIdcardA.setOnClickListener {
            onClickProxy {
                act.applyCamera(1)
            }
        }
        binding.ivIdcardB.setOnClickListener {
            onClickProxy {
                act.applyCamera(2)
            }
        }
        binding.btnNext.setOnClickListener {
            onClickProxy {
                imgList.clear()
                if(viewModel.servicePackage == null || viewModel.ebikeRegInfo == null){
                    ToastUtil.showToast("用户字典获取失败,请返回重试")
                    return@onClickProxy
                }

                if(TextUtils.isEmpty(viewModel.idcardAPath.value)){
                    ToastUtil.showToast("请选择身份证正面照")
                    return@onClickProxy
                }

                if(TextUtils.isEmpty(viewModel.idcardBPath.value)){
                    ToastUtil.showToast("请选择身份证反面照")
                    return@onClickProxy
                }

                imgList.add(viewModel.idcardAPath.value!!)
                imgList.add(viewModel.idcardBPath.value!!)
                viewModel.uploadIDCardImageList(imgList)
            }
        }

        viewModel.idcardAPath.observe(this, Observer {
            ImageLoader.getInstance().loadImage(BaseApp.instance, GlideImageConfig(it, binding.ivIdcardA).also { config-> config.type = TransType.NORMAL })
        })

        viewModel.idcardBPath.observe(this, Observer {
            ImageLoader.getInstance().loadImage(BaseApp.instance, GlideImageConfig(it, binding.ivIdcardB).also { config-> config.type = TransType.NORMAL })
        })

        viewModel.idCardNextEvent.observe(this, Observer {
            act.onNextByIndex(1)
        })
    }

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }
}