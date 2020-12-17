package net.hyntech.common.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import net.hyntech.common.R
import net.hyntech.common.base.BaseAdapter
import net.hyntech.common.base.BaseViewHolder
import net.hyntech.common.ext.layoutInflater

class SearchAdapter(val context: Context) : BaseAdapter<SearchAdapter.ViewHolder>(){

    private val list: MutableList<String> = mutableListOf()

    private var listener: OnClickListener? = null

    fun setListener(listener: OnClickListener?){
        this.listener = listener
    }

    fun setData(l:List<String>){
        list.clear()
        list.addAll(l)
        this.notifyDataSetChanged()
    }

    fun clearList(){
        list.clear()
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = context.layoutInflater.inflate(R.layout.item_claim_process, parent, false)
        val holder: ViewHolder = ViewHolder(view)
        return holder
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(list.get(position))
    }

    inner class ViewHolder(itemView: View) : BaseViewHolder(itemView) {
        fun setData(s: String) {

        }
    }


    interface OnClickListener{
        fun onItemClick(item: String)
    }
}