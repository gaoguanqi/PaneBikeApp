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
import net.hyntech.common.model.entity.ServiceSafeEntity
import net.hyntech.common.widget.imgloader.ImageLoader
import net.hyntech.common.widget.imgloader.TransType
import net.hyntech.common.widget.imgloader.glide.GlideImageConfig
import net.hyntech.usual.R

class TheSafeAdapter(val context: Context): BaseAdapter<TheSafeAdapter.ViewHolder>()  {

    private val list: MutableList<ServiceSafeEntity.ServicePackageListBean> = arrayListOf()

    private var listener: OnClickListener? = null

    fun setListener(listener: OnClickListener?){
        this.listener = listener
    }

    fun setData(data:List<ServiceSafeEntity.ServicePackageListBean>){
        this.list.clear()
        this.list.addAll(data)
        notifyDataSetChanged()
    }

    fun updataList(data:List<ServiceSafeEntity.ServicePackageListBean>){
        this.list.addAll(data)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view:View = context.layoutInflater.inflate(R.layout.item_the_safe,parent,false)
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

        private val tvTitle:TextView = itemView.findViewById(R.id.tv_title)
        private val tvPrice:TextView = itemView.findViewById(R.id.tv_price)
        private val tvContent:TextView = itemView.findViewById(R.id.tv_content)
        private val ivPic:ImageView = itemView.findViewById(R.id.iv_pic)
        val itemRoot: LinearLayout = itemView.findViewById(R.id.item_root)

        fun setData(entity: ServiceSafeEntity.ServicePackageListBean?) {
             entity?.let {
                 val remark:String = if(TextUtils.isEmpty(it.remark)) "" else "(${it.remark})"

                 tvTitle.text = "${it.insuranceName}${remark} ${it.termRange}年版"
                 tvPrice.text = "￥${it.insurancePrice/100}"
                 tvContent.text = if(TextUtils.isEmpty(it.insuranceDesc)) "暂无详情" else "${it.insuranceDesc}"
                 ImageLoader.getInstance().loadImage(
                     BaseApp.instance,
                     GlideImageConfig(it.insurancePic, ivPic).also { it.type = TransType.NORMAL })
            }
        }
    }

    interface OnClickListener{
        fun onItemClick(item:ServiceSafeEntity.ServicePackageListBean?)
    }
}