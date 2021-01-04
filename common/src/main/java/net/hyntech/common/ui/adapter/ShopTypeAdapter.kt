package net.hyntech.common.ui.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.R
import net.hyntech.common.base.BaseAdapter
import net.hyntech.common.base.BaseViewHolder
import net.hyntech.common.ext.layoutInflater
import net.hyntech.common.model.entity.ServiceTypeEntity

class ShopTypeAdapter (val context: Context) :
    BaseAdapter<ShopTypeAdapter.ViewHolder>() {

    private val defDrawable:Drawable = UIUtils.getDrawable(R.drawable.shape_solid)
    private val selDrawable:Drawable = UIUtils.getDrawable(R.drawable.shape_stroke)

    private val defColor:Int = UIUtils.getColor(R.color.common_gray_text)
    private val selColor:Int = UIUtils.getColor(R.color.common_color_text)

    private var listener: OnClickListener? = null
    private val list: MutableList<ServiceTypeEntity> = mutableListOf()
    fun setListener(listener: OnClickListener?){
        this.listener = listener
    }

    fun setData(l:List<ServiceTypeEntity>){
        list.clear()
        list.addAll(l)
        this.notifyDataSetChanged()
    }

    fun resetList(){
        list.forEach {
            it.isSelected = false
        }
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = context.layoutInflater.inflate(R.layout.item_service_type, parent, false)
        val holder: ViewHolder = ViewHolder(view)
        holder.itemView.setOnClickListener {
            list.forEach {
                it.isSelected = false
            }
            val item = list.get(holder.adapterPosition)
            item.isSelected = true
            listener?.onItemClick(item)
            this.notifyDataSetChanged()
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
        val tvText:TextView = itemView.findViewById(R.id.tv_text)
        fun setData(entity: ServiceTypeEntity) {
            tvText.text = entity.name
            tvText.setTextColor(if(entity.isSelected) selColor else defColor)
            tvText.background = if(entity.isSelected) selDrawable else defDrawable
        }
    }

    interface OnClickListener{
        fun onItemClick(item: ServiceTypeEntity)
    }
}