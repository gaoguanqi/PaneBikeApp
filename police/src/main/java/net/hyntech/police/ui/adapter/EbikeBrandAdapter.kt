package net.hyntech.police.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.base.BaseAdapter
import net.hyntech.common.base.BaseViewHolder
import net.hyntech.common.ext.layoutInflater
import net.hyntech.common.model.entity.CollectorListEntity
import net.hyntech.common.model.entity.EbikeBrandEntity
import net.hyntech.common.model.entity.EbikeRegInfoEntity
import net.hyntech.police.R

class EbikeBrandAdapter(val context: Context) : BaseAdapter<EbikeBrandAdapter.ViewHolder>() {

    private val list: MutableList<EbikeBrandEntity.EbikeTypeListBean> = arrayListOf()

    private var listener: OnClickListener? = null

    fun setListener(listener: OnClickListener?) {
        this.listener = listener
    }


    fun setData(data: List<EbikeBrandEntity.EbikeTypeListBean>) {
        this.list.clear()
        this.list.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = context.layoutInflater.inflate(R.layout.item_brand, parent, false)
        val holder: ViewHolder = ViewHolder(view)
        holder.itemRoot.setOnClickListener {
            if (!UIUtils.isFastDoubleClick()) {
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
        val itemRoot: LinearLayout = itemView.findViewById(R.id.item_root)
        val tvName:TextView = itemView.findViewById(R.id.tv_name)
        fun setData(entity: EbikeBrandEntity.EbikeTypeListBean?) {
            entity?.let {
                tvName.text = it.name
            }
        }
    }

    interface OnClickListener {
        fun onItemClick(item:EbikeBrandEntity.EbikeTypeListBean?)
    }


}