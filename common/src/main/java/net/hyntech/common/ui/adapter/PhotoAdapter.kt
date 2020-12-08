package net.hyntech.common.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import net.hyntech.baselib.app.BaseApp
import net.hyntech.baselib.ext.layoutInflater
import net.hyntech.baselib.http.t
import net.hyntech.common.R
import net.hyntech.common.base.BaseAdapter
import net.hyntech.common.base.BaseViewHolder
import net.hyntech.common.model.entity.PhotoEntity
import net.hyntech.common.widget.imgloader.ImageLoader
import net.hyntech.common.widget.imgloader.TransType
import net.hyntech.common.widget.imgloader.glide.GlideImageConfig

class PhotoAdapter(val context: Context,@LayoutRes val resId:Int):BaseAdapter<PhotoAdapter.ViewHolder>() {

    private var list:MutableList<PhotoEntity> = mutableListOf()
    private var listener: OnClickListener? = null

    fun setData(l:List<PhotoEntity>){
        list.clear()
        list.addAll(l)
        this.notifyDataSetChanged()
    }

    fun setListener(listener: OnClickListener?){
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder = ViewHolder(context.layoutInflater.inflate(resId,parent,false))
        return viewHolder
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(list.get(position))
        holder.itemView.setOnClickListener {
            listener?.onItemClick(position,list.get(position))
        }
    }


    inner class ViewHolder(itemView: View) : BaseViewHolder(itemView) {
        fun setData(entity: PhotoEntity?) {
            val ivImg:ImageView? = itemView.findViewById(R.id.iv_img)
            if(entity != null && ivImg != null){
                ImageLoader.getInstance().loadImage(
                    BaseApp.instance,
                    GlideImageConfig(entity.url.toString(),ivImg).also { it.type = TransType.NORMAL })
            }
        }
    }

    interface OnClickListener{
        fun onItemClick(pos:Int,item: PhotoEntity?)
    }

}