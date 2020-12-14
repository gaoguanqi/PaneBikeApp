package net.hyntech.common.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.baidu.mapapi.search.core.PoiInfo
import net.hyntech.common.ext.layoutInflater
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.R
import net.hyntech.common.base.BaseAdapter
import net.hyntech.common.base.BaseViewHolder

class MapPointAdapter(val context: Context):BaseAdapter<MapPointAdapter.ViewHolder>() {
    private val list:MutableList<PoiInfo> = mutableListOf()
    private var index:Int = 0

    private var listener: OnClickListener? = null
    fun setListener(listener: OnClickListener?){
        this.listener = listener
    }

    fun setData(l:List<PoiInfo>){
        list.clear()
        list.addAll(l)
        this.notifyDataSetChanged()
    }

    fun getDataList():MutableList<PoiInfo>{
        return list
    }

    fun getIndex():Int = index
    fun setIndex(i:Int){
        this.index = i
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val holder = ViewHolder(context.layoutInflater.inflate(R.layout.item_map_point,parent,false))
        holder.itemRoot?.setOnClickListener {
            if(!UIUtils.isFastDoubleClick()){
                listener?.onItemClick(holder.adapterPosition,list.get(holder.adapterPosition))
            }
        }
        return holder
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(position,list.get(position))
    }

    inner class ViewHolder(itemView: View) : BaseViewHolder(itemView){
        private var tvTitle:TextView? = itemView.findViewById(R.id.tv_title)
        private var tvContent:TextView? = itemView.findViewById(R.id.tv_content)
        private var ivPointState:ImageView? = itemView.findViewById(R.id.iv_point_state)
        var itemRoot:LinearLayout? = itemView.findViewById(R.id.item_root)
        fun setData(pos:Int,info: PoiInfo?) {
            info?.let {
                tvTitle?.text = it.name
                tvContent?.text = it.address
                if(pos == index){
                    ivPointState?.background =  UIUtils.getDrawable(R.drawable.ic_point_selected)
                }else{
                    ivPointState?.background =  UIUtils.getDrawable(R.drawable.ic_point_default)
                }
            }
        }
    }

    interface OnClickListener{
        fun onItemClick(pos: Int,item: PoiInfo?)
    }
}