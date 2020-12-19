package net.hyntech.usual.ui.adapter

import android.content.Context
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import net.hyntech.baselib.app.BaseApp
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.base.BaseAdapter
import net.hyntech.common.base.BaseViewHolder
import net.hyntech.common.ext.layoutInflater
import net.hyntech.common.model.entity.AddValServiceEntity
import net.hyntech.common.widget.imgloader.ImageLoader
import net.hyntech.common.widget.imgloader.TransType
import net.hyntech.common.widget.imgloader.glide.GlideImageConfig
import net.hyntech.usual.R

class AddValServiceAdapter (val context: Context): BaseAdapter<AddValServiceAdapter.ViewHolder>() {
    private val list: MutableList<AddValServiceEntity.ValueAddedServiceListBean> = arrayListOf()

    private var listener: OnClickListener? = null

    fun setListener(listener: OnClickListener?){
        this.listener = listener
    }

    fun setData(data:List<AddValServiceEntity.ValueAddedServiceListBean>){
        this.list.clear()
        this.list.addAll(data)
        notifyDataSetChanged()
    }

    fun updataList(data:List<AddValServiceEntity.ValueAddedServiceListBean>){
        this.list.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view:View = context.layoutInflater.inflate(R.layout.item_addval_service,parent,false)
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




    inner class ViewHolder(itemView: View): BaseViewHolder(itemView){
        private val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        private val tvPrice: TextView = itemView.findViewById(R.id.tv_price)
        private val tvContent: TextView = itemView.findViewById(R.id.tv_content)
        private val ivPic: ImageView = itemView.findViewById(R.id.iv_pic)
        val itemRoot: LinearLayout = itemView.findViewById(R.id.item_root)

        fun setData(entity: AddValServiceEntity.ValueAddedServiceListBean?) {
            entity?.let {

                tvTitle.text = "${it.serviceName}"
                tvPrice.text = "￥${it.servicePrice/100}"
                tvContent.text = if(TextUtils.isEmpty(it.serviceDesc)) "暂无详情" else "${it.serviceDesc}"
                ImageLoader.getInstance().loadImage(
                    BaseApp.instance,
                    GlideImageConfig(it.servicePic, ivPic).also { it.type = TransType.NORMAL })
            }
        }

    }


    interface OnClickListener{
        fun onItemClick(item: AddValServiceEntity.ValueAddedServiceListBean?)
    }
}