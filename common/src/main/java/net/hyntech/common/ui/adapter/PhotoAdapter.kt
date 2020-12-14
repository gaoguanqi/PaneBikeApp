package net.hyntech.common.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.annotation.LayoutRes
import net.hyntech.baselib.app.BaseApp
import net.hyntech.common.ext.layoutInflater
import net.hyntech.baselib.utils.UIUtils
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

    fun updataList(l:List<PhotoEntity>){
        list.addAll(l)
        this.notifyDataSetChanged()
    }

    fun getListSize():Int = list.size

    fun getDataList():MutableList<PhotoEntity> = list

    fun removeAtPosition(pos: Int){
        this.list.removeAt(pos)//删除数据源,移除集合中当前下标的数据
        this.notifyItemRemoved(pos)//刷新被删除的地方
        this.notifyItemRangeChanged(pos, itemCount) //刷新被删除数据，以及其后面的数据
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
            if(!UIUtils.isFastDoubleClick()){
                listener?.onItemClick(position,list.get(position),list)
            }
        }

        holder.btnDel?.setOnClickListener {
            if(!UIUtils.isFastDoubleClick()){
                listener?.onItemDel(position,list.get(position))
            }
        }
    }


    inner class ViewHolder(itemView: View) : BaseViewHolder(itemView) {
        val ivImg:ImageView? = itemView.findViewById(R.id.iv_img)
        var btnDel:ImageButton? = null

        fun setData(entity: PhotoEntity?) {
           entity?.let {
               if(it.isDelete){
                   btnDel = itemView.findViewById(R.id.btn_del)
                   btnDel?.visibility = View.VISIBLE
               }
               if(ivImg != null){
                   ImageLoader.getInstance().loadImage(
                       BaseApp.instance,
                       GlideImageConfig(it.url,ivImg).also { config -> config.type = TransType.NORMAL })
               }
           }
        }
    }

    interface OnClickListener{
        fun onItemClick(pos:Int,item: PhotoEntity?,list:MutableList<PhotoEntity>)
        fun onItemDel(pos:Int,item: PhotoEntity?)
    }

}