package net.hyntech.police.ui.fragment

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.PhoneUtils
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.base.BaseFragment
import net.hyntech.police.R
import net.hyntech.common.R as CR
import net.hyntech.police.databinding.FragmentSiteAddBinding
import net.hyntech.police.ui.activity.ShopSiteActivity
import net.hyntech.police.vm.ShopSiteViewModel

//增加网点
class SiteAddFragment(val viewModel: ShopSiteViewModel):BaseFragment<FragmentSiteAddBinding, ShopSiteViewModel>() {

    private lateinit var act: ShopSiteActivity

    companion object {
        fun getInstance(viewModel: ShopSiteViewModel): SiteAddFragment {
            return SiteAddFragment(viewModel)
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_site_add


    override fun bindViewModel() {
        binding.viewModel = viewModel
    }


    //shopType     | String|  是  |服务类型|1-销售门店，2-维修站，3-充电站（多个以逗号隔开）|
    private var shopType1:String = ""
    private var shopType2:String = ""
    private var shopType3:String = ""

    override fun initData(savedInstanceState: Bundle?) {
        act = activity as ShopSiteActivity
        view?.apply {
            this.findViewById<RecyclerView>(R.id.rv)?.apply {
                this.layoutManager = LinearLayoutManager(requireContext()).apply {
                    this.orientation = LinearLayoutManager.HORIZONTAL
                }
                this.adapter = act.photoAdapter
            }
            this.findViewById<TextView>(R.id.tv_title).text = "增加网点"
            this.findViewById<LinearLayout>(R.id.ll_left).setOnClickListener {
                onClickProxy {
                    //返回
                    act.onFinish()
                }
            }

            this.findViewById<TextView>(R.id.tv_store).apply {
                this.setOnClickListener {
                    if(TextUtils.isEmpty(shopType1)){
                        shopType1 = "1"
                        this.background = viewModel.sefDrawable
                        this.setTextColor(viewModel.sefColor)
                    }else{
                        shopType1 = ""
                        this.background = viewModel.defDrawable
                        this.setTextColor(viewModel.defColor)
                    }
                }
            }

            this.findViewById<TextView>(R.id.tv_fix).apply {
                this.setOnClickListener {
                    if(TextUtils.isEmpty(shopType2)){
                        shopType2 = "2"
                        this.background = viewModel.sefDrawable
                        this.setTextColor(viewModel.sefColor)
                    }else{
                        shopType2 = ""
                        this.background = viewModel.defDrawable
                        this.setTextColor(viewModel.defColor)
                    }
                }
            }

            this.findViewById<TextView>(R.id.tv_power).apply {
                this.setOnClickListener {
                    if(TextUtils.isEmpty(shopType3)){
                        shopType3 = "3"
                        this.background = viewModel.sefDrawable
                        this.setTextColor(viewModel.sefColor)
                    }else{
                        shopType3 = ""
                        this.background = viewModel.defDrawable
                        this.setTextColor(viewModel.defColor)
                    }
                }
            }

            this.findViewById<ImageButton>(R.id.btn_position)?.setOnClickListener {
                onClickProxy {
                    act.startMap()
                }
            }


            this.findViewById<ImageView>(R.id.iv_add)?.apply {
                this.setOnClickListener {
                    onClickProxy {
                        act.applyCamera(this)
                    }
                }
            }

            this.findViewById<Button>(R.id.btn_commit)?.setOnClickListener {
                onClickProxy {
                    onCommit()
                }
            }
        }


        viewModel.position.observe(this, Observer {
            binding.etAddress.setText(it.address)
            binding.tvLatlng.text = "(${it.lat},${it.lng})"
        })

    }

    private fun onCommit() {
        val name:String? = binding.etSiteName.text.toString().trim()
        if(TextUtils.isEmpty(name)){
            ToastUtil.showToast("请输入便民服务点名称")
            return
        }

        if(name?.length?:0 > 50){
            ToastUtil.showToast("网点名称请控制在50字以内！")
            return
        }

        if(TextUtils.isEmpty(shopType1) && TextUtils.isEmpty(shopType2) && TextUtils.isEmpty(shopType3)){
            ToastUtil.showToast("请选择服务类型")
            return
        }

        val phone:String? = binding.etPhone.text.toString().trim()
        if(TextUtils.isEmpty(phone)){
            ToastUtil.showToast("请输入联系电话！")
            return
        }

        val address:String? = binding.etAddress.text.toString().trim()
        if(TextUtils.isEmpty(address)){
            ToastUtil.showToast("请输入网点地址！")
            return
        }

        if(act.photoList.isEmpty()){
            ToastUtil.showToast("请选择照片！")
            return
        }

        ToastUtil.showToast("通过")




    }

}