package net.hyntech.common.ui.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import androidx.lifecycle.SavedStateHandle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.SPUtils
import net.hyntech.baselib.app.BaseApp
import net.hyntech.baselib.utils.*
import net.hyntech.common.R
import net.hyntech.common.base.BaseActivity
import net.hyntech.common.db.AppDatabase
import net.hyntech.common.db.dao.Search
import net.hyntech.common.global.Constants
import net.hyntech.common.global.EventCode
import net.hyntech.common.ui.adapter.SearchAdapter
import net.hyntech.common.widget.view.ClearEditText

class SearchActivity:BaseActivity() {

    private lateinit var etInput:ClearEditText
    private lateinit var rv:RecyclerView
    private lateinit var tvClear:TextView
    private val searchDao by lazy { AppDatabase.getInstance(BaseApp.instance).searchDao() }

    private val searchAdapter by lazy { SearchAdapter(this).apply {
        this.setListener(object :SearchAdapter.OnClickListener{
            override fun onItemClick(search: Search) {
                ToastUtil.showToast("${search.content}")
                sendEvent(search.content?:"")
            }
        })
    } }

    override fun getLayoutId(): Int = R.layout.activity_search

    override fun initData(savedInstanceState: Bundle?) {
        setRightTxt<SearchActivity>(UIUtils.getString(R.string.common_search)).onBack<SearchActivity>{
            onFinish()
        }.onSide<SearchActivity> {
            val input:String = etInput.text?.trim().toString()
            if(!TextUtils.isEmpty(input)){
                saveContent(input)
            }
        }
        etInput = findViewById(R.id.et_input)
        etInput.hint = getBundleString(Constants.BundleKey.EXTRA_CONTENT)
        tvClear = findViewById(R.id.tv_clear)
        rv = findViewById(R.id.rv)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = searchAdapter
        tvClear.setOnClickListener {
            tvClear.visibility = View.GONE
            searchAdapter.clearList()
            searchDao.deleteAll()
        }

        val searchList = searchDao.getAllSearch()
        if(searchList.isEmpty()){
            tvClear.visibility = View.GONE
            LogUtils.logGGQ("--空--")
        }else{
            tvClear.visibility = View.VISIBLE
            searchAdapter.setData(searchList)
            LogUtils.logGGQ("--有数据--")
        }
    }

    private fun saveContent(content:String)  {
        val list = searchDao.getAllSearch()
        if(list.isNotEmpty()){
            list.forEach {
                if(TextUtils.equals(content,it.content))return
            }
        }
        val search:Search = Search()
        search.content = content
        searchDao.insertSearch(search)
        sendEvent(content)
    }

    private fun sendEvent(content:String){
        val event: Event<String> = Event(EventCode.EVENT_CODE_SEARCH,content)
        EventBusUtils.sendEvent(event)
        onFinish()
    }
}