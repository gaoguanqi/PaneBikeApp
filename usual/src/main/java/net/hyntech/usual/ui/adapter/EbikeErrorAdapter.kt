package net.hyntech.usual.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import net.hyntech.common.ext.layoutInflater
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.base.BaseAdapter
import net.hyntech.common.base.BaseViewHolder
import net.hyntech.common.model.entity.EbikeErrorEntity
import net.hyntech.usual.R

class EbikeErrorAdapter(val context: Context):BaseAdapter<EbikeErrorAdapter.ViewHolder>() {

    private val list: MutableList<EbikeErrorEntity.AlarmExceptionLogListBean> = arrayListOf()

    private var listener: OnClickListener? = null

    fun setListener(listener: OnClickListener?){
        this.listener = listener
    }

    fun setData(data:List<EbikeErrorEntity.AlarmExceptionLogListBean>){
        this.list.clear()
        this.list.addAll(data)
        notifyDataSetChanged()
    }

    fun updataList(data:List<EbikeErrorEntity.AlarmExceptionLogListBean>){
        this.list.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view:View = context.layoutInflater.inflate(R.layout.item_ebike_error,parent,false)
        val holder:ViewHolder = ViewHolder(view)
        holder.itemRoot.setOnClickListener {
            if(!UIUtils.isFastDoubleClick()){
                listener?.onItemClick(list.get(holder.adapterPosition))
            }
        }

        holder.tvIgnore.setOnClickListener {
            if(!UIUtils.isFastDoubleClick()){
                listener?.onIgnoreClick(list.get(holder.adapterPosition))
            }
        }

        holder.tvAlarm.setOnClickListener {
            if(!UIUtils.isFastDoubleClick()){
                listener?.onAlarmClick(list.get(holder.adapterPosition))
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


    inner class ViewHolder(itemView: View):BaseViewHolder(itemView){

        val itemRoot:LinearLayout = itemView.findViewById(R.id.item_root)
        private val tvEbikeNo:TextView = itemView.findViewById(R.id.tv_ebike_no)
        private val tvEbikeName:TextView = itemView.findViewById(R.id.tv_ebike_name)
        private val tvEbikeState:TextView = itemView.findViewById(R.id.tv_ebike_state)
        private val tvEbikeTel:TextView = itemView.findViewById(R.id.tv_ebike_tel)
        private val tvAlarmAddress:TextView = itemView.findViewById(R.id.tv_alarm_address)
        private val tvAlarmTime:TextView = itemView.findViewById(R.id.tv_alarm_time)
        val tvIgnore:TextView = itemView.findViewById(R.id.tv_ignore)
        val tvAlarm:TextView = itemView.findViewById(R.id.tv_alarm)

        fun setData(entity: EbikeErrorEntity.AlarmExceptionLogListBean?) {
            entity?.let {
                tvEbikeNo.text = it.ebikeNo
                tvEbikeName.text = it.name
                tvEbikeTel.text = it.phone
                tvAlarmAddress.text = it.addr
                tvAlarmTime.text = it.createTime
                if(it.state == 1){
                    tvAlarm.text = "已报警"
                }else{
                    tvAlarm.text = "报警"
                }
            }
        }
    }

    interface OnClickListener{
        fun onItemClick(item:EbikeErrorEntity.AlarmExceptionLogListBean?)
        fun onIgnoreClick(item:EbikeErrorEntity.AlarmExceptionLogListBean?)
        fun onAlarmClick(item:EbikeErrorEntity.AlarmExceptionLogListBean?)
    }
}