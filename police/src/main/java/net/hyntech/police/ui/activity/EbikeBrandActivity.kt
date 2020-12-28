package net.hyntech.police.ui.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_ebike_brand.*
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.base.BaseViewActivity
import net.hyntech.common.model.entity.EbikeBrandEntity
import net.hyntech.common.widget.view.ClearEditText
import net.hyntech.police.R
import net.hyntech.common.R as CR
import net.hyntech.police.databinding.ActivityEbikeBrandBinding
import net.hyntech.police.ui.adapter.EbikeBrandAdapter
import net.hyntech.police.vm.EbikeRegisterViewModel

//车辆品牌
class EbikeBrandActivity :BaseViewActivity<ActivityEbikeBrandBinding, EbikeRegisterViewModel>(){
    private lateinit var etInput: ClearEditText
    private lateinit var rvEbike:RecyclerView
    private val brandAdapter by lazy {
        EbikeBrandAdapter(this).apply {
            this.setListener(object :EbikeBrandAdapter.OnClickListener{
                override fun onItemClick(item: EbikeBrandEntity.EbikeTypeListBean?) {
                    item?.let {

                    }
                }
            })
        }
    }

    private val viewModel by viewModels<EbikeRegisterViewModel>()

    override fun getLayoutId(): Int = R.layout.activity_ebike_brand


    override fun initData(savedInstanceState: Bundle?) {
        setRightTxt<EbikeBrandActivity>(UIUtils.getString(CR.string.common_search)).onBack<EbikeBrandActivity>{
            onFinish(true)
        }.onSide<EbikeBrandActivity> {
            val input:String = etInput.text?.trim().toString()
            if(!TextUtils.isEmpty(input)){
                searchBrand(input)
            }else{
                ToastUtil.showToast("请输入品牌名称")
            }
        }

        etInput = findViewById(net.hyntech.common.R.id.et_input)
        etInput.hint = "请输入品牌"
        etInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let {
                    searchBrand(s.toString())
                } } })

        rvEbike = this.findViewById(R.id.rv_ebike)

        viewModel.defUI.showDialog.observe(this, Observer {
            showLoading()
        })

        viewModel.defUI.dismissDialog.observe(this, Observer {
            dismissLoading()
        })

        viewModel.defUI.toastEvent.observe(this, Observer {
            ToastUtil.showToast(it)
        })


        rvEbike.layoutManager = LinearLayoutManager(this)
        rvEbike.adapter = brandAdapter

        viewModel.ebikeBrandList.observe(this, Observer {
            if(it.isNullOrEmpty()){
                showEmpty(true)
            }else{
                showEmpty(false)
                brandAdapter.setData(it)
            }
        })

        viewModel.serchBrandList.observe(this, Observer {
            showEmpty(false)
            brandAdapter.setData(it)
        })

        viewModel.defUI.emptyEvent.observe(this, Observer {
            showEmpty(true)
        })

        viewModel.getEbikeBrand()
    }


    private fun searchBrand(input: String) {
        viewModel.searchByBrand(input)
    }


    override fun bindViewModel() {
        binding.viewModel = viewModel
    }

    private fun showEmpty(isShow:Boolean){
        if(isShow){
            if(rvEbike.visibility == View.VISIBLE) rvEbike.visibility = View.GONE
            if(vsEmpty.visibility == View.GONE) vsEmpty.visibility = View.VISIBLE
        }else{
            if(rvEbike.visibility == View.GONE) rvEbike.visibility = View.VISIBLE
            if(vsEmpty.visibility == View.VISIBLE) vsEmpty.visibility = View.GONE
        }
    }

}