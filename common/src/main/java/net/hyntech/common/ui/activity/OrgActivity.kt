package net.hyntech.common.ui.activity

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.SPUtils
import kotlinx.android.synthetic.main.activity_org.*
import net.hyntech.baselib.app.BaseApp
import net.hyntech.baselib.utils.LogUtils
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.R
import net.hyntech.common.base.BaseViewActivity
import net.hyntech.common.databinding.ActivityOrgBinding
import net.hyntech.common.db.AppDatabase
import net.hyntech.common.global.Constants
import net.hyntech.common.global.Global
import net.hyntech.common.model.entity.CenterEntity
import net.hyntech.common.ui.adapter.OrgAdapter
import net.hyntech.common.vm.AccountViewModel
import net.hyntech.common.widget.view.ClearEditText

class OrgActivity : BaseViewActivity<ActivityOrgBinding, AccountViewModel>() {


    private var etInput:ClearEditText? = null

    private val viewModel by viewModels<AccountViewModel>()


    override fun getLayoutId(): Int = R.layout.activity_org

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }

    override fun initData(savedInstanceState: Bundle?) {

        ToastUtil.showToast(BaseApp.instance.getBuildType())
        setTitle<OrgActivity>(UIUtils.getString(R.string.common_title_org)).setLeftTxt<OrgActivity>(
            UIUtils.getString(R.string.common_back)
        ).onBack<OrgActivity> {
            onFinish()
        }

        etInput = findViewById(R.id.et_input)
//        etInput?.setListener(object :ClearEditText.OnClickListener{
//            override fun onClearClick() {
//                viewModel.searchRecover()
//            }
//        })

        etInput?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(TextUtils.isEmpty(s)){
                    viewModel.searchRecover()
                }
            }
        })


        refreshLayout.setEnableRefresh(true)//是否启用下拉刷新功能
        refreshLayout.setEnableLoadMore(false)//是否启用上拉加载功能
        refreshLayout.setOnRefreshListener { ref ->
            finishRefresh()
        }

        refreshLayout.setOnLoadMoreListener { ref ->
            finishLoadMore()
        }
        rv.layoutManager = LinearLayoutManager(this)
        val adapter: OrgAdapter = OrgAdapter(this)
        adapter.setListener(object :OrgAdapter.OnClickListener{
            override fun onItemClick(item: CenterEntity.OrgListBean?) {
                item?.let { org ->
                    AppDatabase.getInstance(BaseApp.instance).userDao().apply {
                        this.getCurrentUser()?.let {
                            it.orgId = org.orgId
                            it.orgName = org.orgName
                            var mURL:String = org.api_url
                            org.api_url?.let {url ->
                                mURL = url.substring(0,url.lastIndexOf("/"))
                            }
                            it.apiUrl = mURL
                            LogUtils.logGGQ("-->>apiUrl:${it.apiUrl}")
                            it.appwebUrl = org.appweb_url
                            Global.BASE_URL = it.apiUrl!!
                            Global.BASE_WEB_URL = it.appwebUrl!!

                            Global.BASE_URL = "http://39.98.135.139:8080"
//                            Global.BASE_URL = "http://192.168.0.112:8080"
//                            Global.BASE_WEB_URL = ""
                            this.updateUser(it)
                        }
                    }
                    onFinish(true)
                }
            }
        })
        rv.adapter = adapter
        viewModel.getOrgList()
        viewModel.orgData.observe(this, androidx.lifecycle.Observer {
            adapter.setData(it)
        })

        findViewById<Button>(R.id.btn_search).setOnClickListener {
            onClickProxy {
                val input:String? = etInput?.getText().toString().trim()
                if(TextUtils.isEmpty(input)){
                    ToastUtil.showToast(UIUtils.getString(R.string.common_tip_search))
                }else{
                    viewModel.searchInput(input!!)
                }
            }
        }
    }


    //结束下拉刷新
    private fun finishRefresh() {
        refreshLayout?.finishRefresh(2000)
    }

    //结束加载更多
    private fun finishLoadMore() {
        refreshLayout?.finishLoadMore(2000)
    }


}