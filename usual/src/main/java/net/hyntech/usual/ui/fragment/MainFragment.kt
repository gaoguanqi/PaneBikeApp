package net.hyntech.usual.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.blankj.utilcode.util.ScreenUtils
import net.hyntech.baselib.app.BaseApp
import net.hyntech.baselib.utils.LogUtils
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.base.BaseFragment
import net.hyntech.common.db.AppDatabase
import net.hyntech.common.model.entity.SeverInfoEntity
import net.hyntech.common.model.entity.UserInfoEntity
import net.hyntech.common.ui.adapter.EBikeListAdapter
import net.hyntech.common.ui.adapter.SeverListAdapter
import net.hyntech.common.widget.popu.EBikeListPopu
import net.hyntech.usual.R
import net.hyntech.usual.databinding.FragmentMainBinding
import net.hyntech.common.R as CR
import net.hyntech.usual.vm.HomeViewModel

class MainFragment(viewModel: HomeViewModel):BaseFragment<FragmentMainBinding,HomeViewModel>(viewModel) {

    private var tvTitle:TextView? = null
    private var ebikeList: MutableList<UserInfoEntity.EbikeListBean>? = null
    private val ebikeAdapter by lazy { EBikeListAdapter(requireContext()) }
    private val ebikePopu by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { EBikeListPopu<EBikeListAdapter.ViewHolder,EBikeListAdapter>(requireContext(),ebikeAdapter).apply {
        this.popupGravity = Gravity.BOTTOM } }

    companion object {
        fun getInstance(viewModel: HomeViewModel): MainFragment {
            return MainFragment(viewModel)
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_main

    override fun initData(savedInstanceState: Bundle?) {

        tvTitle = view?.findViewById(R.id.tv_main_title)

        val list:List<SeverInfoEntity> = arrayListOf(
            SeverInfoEntity(UIUtils.getString(CR.string.common_car_info),CR.drawable.icon_car_locus),
            SeverInfoEntity(UIUtils.getString(CR.string.common_conve_service),CR.drawable.icon_conver_service),
            SeverInfoEntity(UIUtils.getString(CR.string.common_call_police),CR.drawable.icon_call_police),
            SeverInfoEntity(UIUtils.getString(CR.string.common_the_safe),CR.drawable.icon_the_safe))

        binding.rvMain.layoutManager = GridLayoutManager(requireContext(),4)
        val adapter: SeverListAdapter = SeverListAdapter(requireContext(),CR.layout.item_sever_usual,list)
        adapter.setListener(object :SeverListAdapter.OnClickListener{
            override fun onItemClick(item: SeverInfoEntity?) {
                item?.let {
                    ToastUtil.showToast(it.name)
                }
            }
        })
        binding.rvMain.adapter = adapter
        ebikeAdapter.setListener(object :EBikeListAdapter.OnClickListener{
            override fun onItemClick(entity: UserInfoEntity.EbikeListBean?) {
                entity?.let {
                    AppDatabase.getInstance(BaseApp.instance).userDao().apply {
                        this.getCurrentUser()?.let {user ->
                            if(!TextUtils.equals(user.ebikeNo,entity.ebikeNo)){
                                user.ebikeNo = entity.ebikeNo
                                this.updateUser(user)
                                ebikeList?.forEach { item ->
                                     item.isSelected = false
                                }
                                tvTitle?.setText(entity.ebikeNo)
                                entity.isSelected = true
                                viewModel.currentEbike.set(entity)
                                ebikeAdapter.notifyDataSetChanged()
                            }
                        }
                    }
                }
                ebikePopu.dismiss()
            }
        })

        //用户数据
        viewModel.userInfo.observe(this, Observer {
            viewModel.currentEbike.get()?.let {
                    tvTitle?.setText(it.ebikeNo)
            }
            ebikeList = it.ebike_list
        })

        tvTitle?.setOnClickListener {
            showEBikePopu()
        }

        viewModel.getUserInfo()
    }


    private fun showEBikePopu(){
        ebikeList?.let {list ->
            ebikeAdapter.setData(list)
            ebikePopu.showPopupWindow(tvTitle)
        }
    }

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }


    override fun lazyLoadData() {
        super.lazyLoadData()
        LogUtils.logGGQ("lazyLoadData--->>")
        viewModel.getMessageCount()
    }

    override fun refReshData() {
        super.refReshData()
        LogUtils.logGGQ("refReshData--->>")
        viewModel.getUserInfo(true)
        viewModel.getMessageCount()
    }




}