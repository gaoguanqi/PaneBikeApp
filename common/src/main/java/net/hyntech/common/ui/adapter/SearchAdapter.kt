package net.hyntech.common.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.R
import net.hyntech.common.base.BaseAdapter
import net.hyntech.common.base.BaseViewHolder
import net.hyntech.common.db.dao.Search
import net.hyntech.common.ext.layoutInflater

class SearchAdapter(val context: Context) : BaseAdapter<SearchAdapter.ViewHolder>(){

    private val list: MutableList<Search> = mutableListOf()

    private var listener: OnClickListener? = null

    fun setListener(listener: OnClickListener?){
        this.listener = listener
    }

    fun setData(l:List<Search>){
        list.clear()
        list.addAll(l)
        this.notifyDataSetChanged()
    }

    fun clearList(){
        list.clear()
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = context.layoutInflater.inflate(R.layout.item_search, parent, false)
        val holder: ViewHolder = ViewHolder(view)
        holder.itemRoot.setOnClickListener {
            if(!UIUtils.isFastDoubleClick()){
                listener?.onItemClick(list.get(holder.adapterPosition))
            }
        }
        return holder
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(list.get(position))
    }

    inner class ViewHolder(itemView: View) : BaseViewHolder(itemView) {
        private val tvTxt:TextView = itemView.findViewById(R.id.tv_txt)
        val itemRoot: LinearLayout = itemView.findViewById(R.id.item_root)
        fun setData(search: Search) {
            tvTxt.text = search.content
        }
    }


    interface OnClickListener{
        fun onItemClick(item: Search)
    }
}