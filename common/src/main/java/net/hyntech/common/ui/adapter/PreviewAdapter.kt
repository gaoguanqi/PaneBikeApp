package net.hyntech.common.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.github.chrisbanes.photoview.PhotoView
import net.hyntech.baselib.app.BaseApp
import net.hyntech.baselib.ext.layoutInflater

import net.hyntech.common.R
import net.hyntech.common.base.BaseAdapter
import net.hyntech.common.base.BaseViewHolder
import net.hyntech.common.widget.imgloader.ImageLoader
import net.hyntech.common.widget.imgloader.TransType
import net.hyntech.common.widget.imgloader.glide.GlideImageConfig

class PreviewAdapter(val context:Context, val data:List<String>) : BaseAdapter<PreviewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder = ViewHolder(context.layoutInflater.inflate(R.layout.item_preview,parent,false))
        return viewHolder
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(data.get(position))
    }

    inner class ViewHolder(itemView: View) : BaseViewHolder(itemView) {
        fun setData(item: String) {
            itemView.findViewById<PhotoView>(R.id.pv_img)?.let {iv ->
                ImageLoader.getInstance().loadImage(
                    BaseApp.instance,
                    GlideImageConfig(item, iv).also { it.type = TransType.NORMAL })
            }
        }
    }
}