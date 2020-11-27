package net.hyntech.common.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import net.hyntech.baselib.app.BaseApp
import net.hyntech.baselib.utils.UIUtils

import net.hyntech.common.R
import net.hyntech.common.widget.imgloader.ImageLoader
import net.hyntech.common.widget.imgloader.TransType
import net.hyntech.common.widget.imgloader.glide.GlideImageConfig

class WeclomeAdapter(val context:Context,val data:Array<Int>,val listener:OnClickListener) : RecyclerView.Adapter<WeclomeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_weclome,parent,false))

    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(position,data.get(position))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setData(pos:Int,item: Int) {
            itemView.findViewById<ImageView>(R.id.iv_img)?.let {
                ImageLoader.getInstance().loadImage(
                    BaseApp.instance,
                    GlideImageConfig(UIUtils.getDrawable(item), it).also { it.type = TransType.NORMAL })
                it.setOnClickListener {
                    if(!UIUtils.isFastDoubleClick()){
                        listener.onItemClick(pos)
                    }
                }
            }
        }
    }

    interface OnClickListener{
        fun onItemClick(position:Int)
    }
}