package net.hyntech.police.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.base.BaseAdapter
import net.hyntech.common.base.BaseViewHolder
import net.hyntech.common.ext.layoutInflater
import net.hyntech.common.model.entity.AlarmInfoEntity
import net.hyntech.common.utils.CommonUtils
import net.hyntech.common.widget.view.DotView
import net.hyntech.police.R
import net.hyntech.common.R as CR

class AlarmInfoAdapter(val context: Context) : BaseAdapter<AlarmInfoAdapter.ViewHolder>() {

    private val redColor = UIUtils.getColor(CR.color.common_color_badge)
    private val grayColor = UIUtils.getColor(CR.color.common_color_gray)


    private val list: MutableList<AlarmInfoEntity.AlarmInfoListBean> = arrayListOf()

    private var listener: OnClickListener? = null

    fun setListener(listener: OnClickListener?) {
        this.listener = listener
    }

    fun setData(data: List<AlarmInfoEntity.AlarmInfoListBean>) {
        this.list.clear()
        this.list.addAll(data)
        notifyDataSetChanged()
    }

    fun updataList(data: List<AlarmInfoEntity.AlarmInfoListBean>) {
        this.list.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = context.layoutInflater.inflate(R.layout.item_alarm_info, parent, false)
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
        holder.setData(position,list.get(position))
    }


    inner class ViewHolder(itemView: View) : BaseViewHolder(itemView) {
        val itemRoot:LinearLayout = itemView.findViewById(R.id.item_root)
        private val tvEbikeNo:TextView = itemView.findViewById(R.id.tv_ebike_no)
        private val tvDate:TextView = itemView.findViewById(R.id.tv_date)
        private val tvTime:TextView = itemView.findViewById(R.id.tv_time)
        private val tvState:TextView = itemView.findViewById(R.id.tv_ebike_state)
        private val tvAddress:TextView = itemView.findViewById(R.id.tv_address)
        private val tvDes:TextView = itemView.findViewById(R.id.tv_des)
        private val dot:DotView = itemView.findViewById(R.id.dot)
        private val lineTop:View = itemView.findViewById(R.id.view_line_top)
        private val lineBottom:View = itemView.findViewById(R.id.view_line_bottom)


        fun setData(pos:Int,entity: AlarmInfoEntity.AlarmInfoListBean?) {
            entity?.let {
                if(pos == 0){
                    lineTop.visibility = View.INVISIBLE
                    lineBottom.visibility = View.VISIBLE
                }else if(pos == list.size - 1){
                    lineTop.visibility = View.VISIBLE
                    lineBottom.visibility = View.INVISIBLE
                }else{
                    lineTop.visibility = View.VISIBLE
                    lineBottom.visibility = View.VISIBLE
                }

                tvEbikeNo.text = "${it.ebikeNo}"
                val timeArray = CommonUtils.splitDateArray(it.createTime)
                if(!timeArray.isEmpty() && timeArray.size >= 2){
                    val data= timeArray.get(0)
                    if(data.length >= 10){
                        tvDate.text = "${data.substring(5,10)}"
                    }else{
                        tvDate.text = "${data}"
                    }
                    tvTime.text = "${timeArray.get(1)}"
                }else{
                    tvDate.text = ""
                    tvTime.text = ""
                }
                tvAddress.text = "${it.addr}"
                tvDes.text = "${it.remark}"

                val state = it.state
                if(state == 0){
                    dot.setColor(redColor)
                }else{
                    dot.setColor(grayColor)
                }

                val type = it.messageType
                when(type){
                    "alarm" ->{
                        tvState.text = "车主报警"
                    }
                    "move" ->{
                        tvState.text = "位移变化"
                    }
                    "enclosure" ->{
                        tvState.text = "围栏报警"
                    }
                    else ->{
                        tvState.text = "未知状态"
                    }
                }
            }
        }
    }

    interface OnClickListener {
        fun onItemClick(item: AlarmInfoEntity.AlarmInfoListBean?)
    }

}