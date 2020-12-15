package net.hyntech.usual.ui.adapter

import android.content.Context
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
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
import net.hyntech.usual.databinding.ItemTheSafeBinding

class TheSafeAdapter(val context: Context): BaseAdapter<TheSafeAdapter.ViewHolder>()  {


    private lateinit var binding: ItemTheSafeBinding

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
        binding = DataBindingUtil.inflate<ItemTheSafeBinding>(parent.context.layoutInflater,
            R.layout.item_the_safe,
            parent,
            false)
        val holder: ViewHolder = ViewHolder(binding.root)
        binding.itemRoot.setOnClickListener {
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
        fun setData(entity: ServiceSafeEntity.ServicePackageListBean?) {
             entity?.let {
                 val remark:String = if(TextUtils.isEmpty(it.remark)) "" else "(${it.remark})"

                 binding.tvTitle.text = "${it.insuranceName}${remark} ${it.termRange}年版"
                 binding.tvPrice.text = "￥${it.insurancePrice/100}"
                 binding.tvContent.text = if(TextUtils.isEmpty(it.insuranceDesc)) "暂无详情" else "${it.insuranceDesc}"
                 ImageLoader.getInstance().loadImage(
                     BaseApp.instance,
                     GlideImageConfig(it.insurancePic, binding.ivPic).also { it.type = TransType.NORMAL })
            }
        }
    }

    interface OnClickListener{
        fun onItemClick(item:ServiceSafeEntity.ServicePackageListBean?)
    }
}