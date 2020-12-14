package net.hyntech.common.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import kotlinx.android.synthetic.main.item_org.view.*
import net.hyntech.common.ext.layoutInflater
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.R
import net.hyntech.common.base.BaseAdapter
import net.hyntech.common.base.BaseViewHolder
import net.hyntech.common.databinding.ItemOrgBinding
import net.hyntech.common.model.entity.CenterEntity

class OrgAdapter(val context: Context):BaseAdapter<OrgAdapter.ViewHolder>() {

    private lateinit var binding: ItemOrgBinding

    private val list: MutableList<CenterEntity.OrgListBean> = arrayListOf()
    private var listener:OnClickListener? = null

    fun setListener(listener:OnClickListener?){
        this.listener = listener
    }

    fun setData(data:List<CenterEntity.OrgListBean>){
        this.list.clear()
        this.list.addAll(data)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = DataBindingUtil.inflate<ItemOrgBinding>(
            parent.context.layoutInflater,
            R.layout.item_org,
            parent,
            false
        )
        val holder: ViewHolder = ViewHolder(binding.root)
        holder.itemView.item_root.setOnClickListener {
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

        fun setData(entity: CenterEntity.OrgListBean?) {
            entity?.let {
                itemView.findViewById<TextView>(R.id.tv_name).text = it.orgName
            }
        }
    }

    interface OnClickListener{
        fun onItemClick(item:CenterEntity.OrgListBean?)
    }

}