package net.hyntech.common.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.SPUtils
import net.hyntech.baselib.app.BaseApp
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.R
import net.hyntech.common.base.BaseActivity
import net.hyntech.common.global.Constants
import net.hyntech.common.ui.adapter.SearchAdapter
import net.hyntech.common.widget.view.ClearEditText

class SearchActivity:BaseActivity() {

    private lateinit var etInput:ClearEditText
    private lateinit var rv:RecyclerView
    private lateinit var tvClear:TextView

    private val searchList:MutableList<String> = mutableListOf()
    private val sp by lazy { SPUtils.getInstance(BaseApp.instance.getAppPackage()) }
    private val defaultSet:MutableSet<String> by lazy { sp.getStringSet(Constants.SaveInfoKey.SEARCH)?: mutableSetOf() }

    private val searchAdapter by lazy { SearchAdapter(this).apply {
        this.setListener(object :SearchAdapter.OnClickListener{
            override fun onItemClick(item: String) {
                saveContent(item)
                ToastUtil.showToast("${item}")
            }
        })
    } }

    override fun getLayoutId(): Int = R.layout.activity_search

    override fun initData(savedInstanceState: Bundle?) {
        setRightTxt<SearchActivity>(UIUtils.getString(R.string.common_search)).onBack<SearchActivity>{
            onFinish()
        }.onSide<SearchActivity> {
            ToastUtil.showToast("搜索")
        }
        etInput = findViewById(R.id.et_input)
        etInput.hint = getBundleString(Constants.BundleKey.EXTRA_CONTENT)
        tvClear = findViewById(R.id.tv_clear)
        rv = findViewById(R.id.rv)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = searchAdapter
        tvClear.setOnClickListener {
            searchAdapter.clearList()
        }

        if(defaultSet.isEmpty()){
            tvClear.visibility = View.GONE
        }else{
            searchList.addAll(defaultSet)
            tvClear.visibility = View.VISIBLE
            searchAdapter.setData(searchList)
        }
    }


    private fun saveContent(content:String){
        defaultSet.add(content)
        sp.put(Constants.SaveInfoKey.SEARCH,defaultSet)
    }
}