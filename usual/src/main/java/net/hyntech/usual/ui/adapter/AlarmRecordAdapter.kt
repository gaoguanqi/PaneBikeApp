package net.hyntech.usual.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import net.hyntech.common.ext.layoutInflater
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.base.BaseAdapter
import net.hyntech.common.base.BaseViewHolder
import net.hyntech.common.model.entity.AlarmRecordEntity
import net.hyntech.usual.R

class AlarmRecordAdapter(val context: Context):BaseAdapter<AlarmRecordAdapter.ViewHolder>() {

    private val list: MutableList<AlarmRecordEntity.AtAlarmListBean> = arrayListOf()

    private var listener: OnClickListener? = null

    fun setListener(listener: OnClickListener?){
        this.listener = listener
    }

    fun setData(data:List<AlarmRecordEntity.AtAlarmListBean>){
        this.list.clear()
        this.list.addAll(data)
        notifyDataSetChanged()
    }

    fun updataList(data:List<AlarmRecordEntity.AtAlarmListBean>){
        this.list.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view:View = context.layoutInflater.inflate(R.layout.item_alarm_record,parent,false)
        val holder:ViewHolder = ViewHolder(view)
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


    inner class ViewHolder(itemView: View):BaseViewHolder(itemView){

        val itemRoot:LinearLayout = itemView.findViewById(R.id.item_root)
        private val tvEbikeNo: TextView = itemView.findViewById(R.id.tv_ebike_no)
        private val tvEbikeName: TextView = itemView.findViewById(R.id.tv_ebike_name)
        private val tvEbikeState: TextView = itemView.findViewById(R.id.tv_ebike_state)
        private val tvEbikeTel: TextView = itemView.findViewById(R.id.tv_ebike_tel)
        private val tvAlarmAddress: TextView = itemView.findViewById(R.id.tv_alarm_address)
        private val tvAlarmTime: TextView = itemView.findViewById(R.id.tv_alarm_time)
        private val tvFindTip: TextView = itemView.findViewById(R.id.tv_find_tip)
        private val tvFindTime: TextView = itemView.findViewById(R.id.tv_find_time)


        private val llFindTime: LinearLayout = itemView.findViewById(R.id.ll_find_time)

        fun setData(entity: AlarmRecordEntity.AtAlarmListBean?) {
            entity?.let {
                tvEbikeNo.text = it.ebikeNo
                tvEbikeName.text = it.alarmName
                tvEbikeTel.text = it.alarmPhone
                tvAlarmAddress.text = it.stolenAddr
                tvAlarmTime.text = it.createTime

                if(it.closeCaseFlag == 1){
                    tvEbikeState.text = "已销案"
                    tvEbikeState.setTextColor(UIUtils.getColor(R.color.common_color_pass))
                    llFindTime.visibility = View.VISIBLE
                }else{
                    tvEbikeState.text = "未销案"
                    tvEbikeState.setTextColor(UIUtils.getColor(R.color.common_color_badge))
                    llFindTime.visibility = View.GONE
                }

                if(it.closeCaseType == 1){
                    tvFindTip.text = "找回时间"
                    tvFindTime.text = it.foundTime
                }else if(it.closeCaseType == 0){
                    tvFindTip.text = "赔付时间"
                    tvFindTime.text = it.closeCaseTime
                }
            }
        }
    }

    interface OnClickListener{
        fun onItemClick(item:AlarmRecordEntity.AtAlarmListBean?)
    }
}