package net.hyntech.common.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_org.view.*
import net.hyntech.baselib.ext.layoutInflater
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.R
import net.hyntech.common.databinding.ItemOrgBinding
import net.hyntech.common.model.entity.CenterEntity

class OrgAdapter(val context: Context):RecyclerView.Adapter<OrgAdapter.ViewHolder>() {

    private lateinit var binding: ItemOrgBinding

    private var list: List<CenterEntity.OrgListBean>? = null
    private var listener:OnClickListener? = null

    fun setListener(listener:OnClickListener?){
        this.listener = listener
    }

    fun setData(data:List<CenterEntity.OrgListBean>){
        this.list = data
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
                listener?.onItemClick(list?.get(holder.adapterPosition))
            }
        }
        return holder
    }

    override fun getItemCount(): Int {
        return list?.size?:0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(list?.get(position))
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setData(entity: CenterEntity.OrgListBean?) {
            entity?.let {
                binding.data = it
            }
        }
    }

    interface OnClickListener{
        fun onItemClick(item:CenterEntity.OrgListBean?)
    }

}