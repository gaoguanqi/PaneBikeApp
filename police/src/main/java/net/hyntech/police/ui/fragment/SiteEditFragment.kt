package net.hyntech.police.ui.fragment

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.hyntech.baselib.utils.LogUtils
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.base.BaseFragment
import net.hyntech.common.model.entity.PhotoEntity
import net.hyntech.common.utils.CommonUtils
import net.hyntech.police.R
import net.hyntech.common.R as CR
import net.hyntech.police.databinding.FragmentSiteEditBinding
import net.hyntech.police.ui.activity.ShopSiteActivity
import net.hyntech.police.vm.ShopSiteViewModel

//网点编辑
class SiteEditFragment (val viewModel: ShopSiteViewModel):
    BaseFragment<FragmentSiteEditBinding, ShopSiteViewModel>() {

    private lateinit var act: ShopSiteActivity

    companion object {
        fun getInstance(viewModel: ShopSiteViewModel): SiteEditFragment {
            return SiteEditFragment(viewModel)
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_site_edit


    override fun bindViewModel() {
        binding.viewModel = viewModel
    }


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
            this.findViewById<TextView>(R.id.tv_title).text = "编辑"
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

        viewModel.shopDetails.observe(this, Observer {
            binding.etSiteName.setText("${it.shopName}")
            binding.etPhone.setText("${it.phone}")
            binding.etAddress.setText("${it.addr}")
            binding.tvLatlng.text = "${it.lat},${it.lng}"

            val shopType = it.shopType
            if(shopType.contains("1")){
                shopType1 = "1"
                binding.tvStore.background = viewModel.sefDrawable
                binding.tvStore.setTextColor(viewModel.sefColor)
            }
            if(shopType.contains("2")){
                shopType2 = "2"
                binding.tvFix.background = viewModel.sefDrawable
                binding.tvFix.setTextColor(viewModel.sefColor)
            }
            if(shopType.contains("3")){
                shopType3 = "3"
                binding.tvPower.background = viewModel.sefDrawable
                binding.tvPower.setTextColor(viewModel.sefColor)
            }


            val imgList = CommonUtils.splitPicList(it.relevantPic)
            if(imgList.isNotEmpty()){
                act.photoList.clear()
                imgList.forEach { img ->
                    val photo = PhotoEntity(img,true)
                    act.photoList.add(photo)
                }
                act.photoAdapter.setData(act.photoList)
            }
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


    override fun lazyLoadData() {
        super.lazyLoadData()
        act.getShopDetails()
    }
}