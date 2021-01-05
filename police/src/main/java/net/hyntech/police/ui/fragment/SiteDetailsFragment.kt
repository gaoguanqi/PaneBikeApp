package net.hyntech.police.ui.fragment

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.hyntech.common.base.BaseFragment
import net.hyntech.common.model.entity.PhotoEntity
import net.hyntech.common.utils.CommonUtils
import net.hyntech.police.R
import net.hyntech.police.databinding.FragmentSiteDetailsBinding
import net.hyntech.police.ui.activity.ShopSiteActivity
import net.hyntech.police.vm.ShopSiteViewModel

//网点详情
class SiteDetailsFragment(val viewModel: ShopSiteViewModel):BaseFragment<FragmentSiteDetailsBinding, ShopSiteViewModel>() {

    private lateinit var act: ShopSiteActivity

    companion object {
        fun getInstance(viewModel: ShopSiteViewModel): SiteDetailsFragment {
            return SiteDetailsFragment(viewModel)
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_site_details

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }

    override fun initData(savedInstanceState: Bundle?) {
        act = activity as ShopSiteActivity
        view?.apply {
            this.findViewById<RecyclerView>(R.id.rv)?.apply {
                this.layoutManager = LinearLayoutManager(requireContext()).apply {
                    this.orientation = LinearLayoutManager.HORIZONTAL
                }
                this.adapter = act.photoAdapter
            }

            this.findViewById<TextView>(R.id.tv_title).text = "详情"
            this.findViewById<LinearLayout>(R.id.ll_left).setOnClickListener {
                onClickProxy {
                    //返回
                    act.onFinish()
                }
            }
        }

        viewModel.shopDetails.observe(this, Observer {
            binding.tvSiteName.text = ("${it.shopName}")
            binding.tvPhone.text = "${it.phone}"
            binding.tvAddress.text = "${it.addr}"
            binding.tvLatlng.text = "${it.lat},${it.lng}"

            val shopType = it.shopType
            if(shopType.contains("1")){
                binding.tvStore.background = viewModel.sefDrawable
                binding.tvStore.setTextColor(viewModel.sefColor)
            }
            if(shopType.contains("2")){
                binding.tvFix.background = viewModel.sefDrawable
                binding.tvFix.setTextColor(viewModel.sefColor)
            }
            if(shopType.contains("3")){
                binding.tvPower.background = viewModel.sefDrawable
                binding.tvPower.setTextColor(viewModel.sefColor)
            }

            val imgList = CommonUtils.splitPicList(it.relevantPic)
            if(imgList.isNotEmpty()){
                act.photoList.clear()
                imgList.forEach { img ->
                    val photo = PhotoEntity(img,false)
                    act.photoList.add(photo)
                }
                act.photoAdapter.setData(act.photoList)
            }
        })

    }

    override fun lazyLoadData() {
        super.lazyLoadData()
        act.getShopDetails()
    }


}