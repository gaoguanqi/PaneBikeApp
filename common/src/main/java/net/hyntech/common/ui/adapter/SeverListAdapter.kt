package net.hyntech.common.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import net.hyntech.baselib.app.BaseApp
import net.hyntech.common.ext.layoutInflater
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.R
import net.hyntech.common.base.BaseAdapter
import net.hyntech.common.base.BaseViewHolder
import net.hyntech.common.model.entity.SeverInfoEntity
import net.hyntech.common.widget.imgloader.ImageLoader
import net.hyntech.common.widget.imgloader.TransType
import net.hyntech.common.widget.imgloader.glide.GlideImageConfig

class  SeverListAdapter(val context: Context,val layoutRes:Int,val list:List<SeverInfoEntity>): BaseAdapter<SeverListAdapter.ViewHolder>() {

    private var listener: OnClickListener? = null

    fun setListener(listener: OnClickListener?){
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = context.layoutInflater.inflate(layoutRes,parent,false)
        val holder: ViewHolder = ViewHolder(view)
        holder.itemRoot.setOnClickListener {
            if(!UIUtils.isFastDoubleClick()){
                val pos = holder.adapterPosition
                listener?.onItemClick(pos,list.get(pos))
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
        val itemRoot:LinearLayout = itemView.findViewById(R.id.item_root)
        fun setData(entity: SeverInfoEntity?) {
            entity?.let {
                itemView.findViewById<TextView>(R.id.tv_name)?.text = it.name
                itemView.findViewById<ImageView>(R.id.iv_icon)?.let {iv->
                    ImageLoader.getInstance().loadImage(
                        BaseApp.instance,
                        GlideImageConfig(UIUtils.getDrawable(it.resId), iv).also { config-> config.type = TransType.NORMAL })
                }
            }
        }
    }

    interface OnClickListener{
        fun onItemClick(pos:Int,item: SeverInfoEntity?)
    }
}