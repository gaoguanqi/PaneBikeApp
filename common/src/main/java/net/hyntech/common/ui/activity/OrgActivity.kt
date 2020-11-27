package net.hyntech.common.ui.activity

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_org.*
import net.hyntech.baselib.utils.LogUtils
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.R
import net.hyntech.common.base.BaseViewActivity
import net.hyntech.common.databinding.ActivityOrgBinding
import net.hyntech.common.vm.AccountViewModel

class OrgActivity:BaseViewActivity<ActivityOrgBinding, AccountViewModel>() {

    private val viewModel by viewModels<AccountViewModel>()


    override fun getLayoutId(): Int = R.layout.activity_org

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }

    override fun initData(savedInstanceState: Bundle?) {

        setTitle<OrgActivity>(UIUtils.getString(R.string.common_title_org)).setLeftTxt<OrgActivity>(UIUtils.getString(R.string.common_back)).onBack<OrgActivity>{
            onFinish()
        }

        refreshLayout.setEnableRefresh(true)//是否启用下拉刷新功能
        refreshLayout.setEnableLoadMore(false)//是否启用上拉加载功能
        refreshLayout.setOnRefreshListener { ref ->
            finishRefresh()
        }

        refreshLayout.setOnLoadMoreListener { ref ->
            finishLoadMore()
        }
    }


    //结束下拉刷新
    private fun finishRefresh(){
        refreshLayout?.finishRefresh(2000)
    }

    //结束加载更多
    private fun finishLoadMore(){
        refreshLayout?.finishLoadMore(2000)
    }


}