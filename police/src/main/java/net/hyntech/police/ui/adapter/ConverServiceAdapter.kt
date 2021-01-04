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
import net.hyntech.common.model.entity.ConverServiceEntity
import net.hyntech.police.R

class ConverServiceAdapter(val context: Context) : BaseAdapter<ConverServiceAdapter.ViewHolder>() {


    private val list: MutableList<ConverServiceEntity.AtServiceShopListBean> = arrayListOf()

    private var listener: OnClickListener? = null

    fun setListener(listener: OnClickListener?) {
        this.listener = listener
    }

    fun setData(data: List<ConverServiceEntity.AtServiceShopListBean>) {
        this.list.clear()
        this.list.addAll(data)
        notifyDataSetChanged()
    }

    fun updataList(data: List<ConverServiceEntity.AtServiceShopListBean>) {
        this.list.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = context.layoutInflater.inflate(R.layout.item_conver_service, parent, false)
        val holder: ViewHolder = ViewHolder(view)
        holder.tvDelete.setOnClickListener {
            if (!UIUtils.isFastDoubleClick()) {
                listener?.onDeleteClick(list.get(holder.adapterPosition))
            }
        }

        holder.tvEdit.setOnClickListener {
            if (!UIUtils.isFastDoubleClick()) {
                listener?.onEditClick(list.get(holder.adapterPosition))
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
        private val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        private val tvType: TextView = itemView.findViewById(R.id.tv_type)
        private val tvAddress: TextView = itemView.findViewById(R.id.tv_address)
        private val tvPhone: TextView = itemView.findViewById(R.id.tv_phone)
        private val tvName: TextView = itemView.findViewById(R.id.tv_name)
        val tvDelete: TextView = itemView.findViewById(R.id.tv_delete)
        val tvEdit: TextView = itemView.findViewById(R.id.tv_edit)


        fun setData(entity: ConverServiceEntity.AtServiceShopListBean?) {
            entity?.let {
                tvTitle.text = "${it.shopName}"
                tvType.text = "${it.shopType}"
                tvAddress.text = "${it.addr}"
                tvPhone.text = "${it.phone}"
                tvName.text = "${it.createName}"
            }
        }
    }

    interface OnClickListener {
        fun onDeleteClick(item: ConverServiceEntity.AtServiceShopListBean?)
        fun onEditClick(item: ConverServiceEntity.AtServiceShopListBean?)
    }


}