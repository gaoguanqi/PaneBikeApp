package net.hyntech.common.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import net.hyntech.common.R
import net.hyntech.common.base.BaseAdapter
import net.hyntech.common.base.BaseViewHolder
import net.hyntech.common.ext.layoutInflater
import net.hyntech.common.model.entity.ClaimProcessEntity

class ClaimProcessAdapter(val context: Context) : BaseAdapter<ClaimProcessAdapter.ViewHolder>(){

    private val list: MutableList<ClaimProcessEntity.ClaimProcessBean> = mutableListOf()


    fun setData(l:List<ClaimProcessEntity.ClaimProcessBean>){
        list.clear()
        list.addAll(l)
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
        holder.setData(position,list.get(position))
    }


    inner class ViewHolder(itemView: View) : BaseViewHolder(itemView) {
         val tvTitle:TextView = itemView.findViewById(R.id.tv_title)
         val tvContent:TextView = itemView.findViewById(R.id.tv_content)
         val tvIndex:TextView = itemView.findViewById(R.id.tv_index)
         val tvLine:TextView = itemView.findViewById(R.id.tv_line)
        fun setData(pos:Int,entity: ClaimProcessEntity.ClaimProcessBean?) {
            entity?.let {
                tvTitle.text = "${it.name}"
                tvContent.text = "${it.model}"
                tvIndex.text = "${pos+1}"
                val params = tvLine.layoutParams
                params.height = tvContent.height
                tvLine.layoutParams = params
            }
        }
    }
}
