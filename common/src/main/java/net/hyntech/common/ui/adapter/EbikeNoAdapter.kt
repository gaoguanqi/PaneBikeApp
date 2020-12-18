package net.hyntech.common.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.R
import net.hyntech.common.base.BaseAdapter
import net.hyntech.common.base.BaseViewHolder
import net.hyntech.common.ext.layoutInflater
import net.hyntech.common.model.vo.BundleEbikeVo

class EbikeNoAdapter(val context: Context) : BaseAdapter<EbikeNoAdapter.ViewHolder>() {

    private var listener: OnClickListener? = null
    private val list: MutableList<BundleEbikeVo> = mutableListOf()
    fun setListener(listener: OnClickListener?){
        this.listener = listener
    }

    fun setData(l:List<BundleEbikeVo>){
        list.clear()
        list.addAll(l)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = context.layoutInflater.inflate(R.layout.item_ebike_list, parent, false)
        val holder: ViewHolder = ViewHolder(view)
        holder.itemView.setOnClickListener {
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
        fun setData(entity: BundleEbikeVo?) {
            entity?.let {
                itemView.findViewById<TextView>(R.id.tv_name)?.text = it.ebikeNo
                itemView.findViewById<ImageView>(R.id.iv_icon)?.visibility = if(it.isSelected) View.VISIBLE else View.GONE
            }
        }
    }

    interface OnClickListener{
        fun onItemClick(item: BundleEbikeVo?)
    }

}