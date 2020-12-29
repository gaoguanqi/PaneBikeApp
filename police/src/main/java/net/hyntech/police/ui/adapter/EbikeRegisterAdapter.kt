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
import net.hyntech.common.model.entity.RegisterListEntity
import net.hyntech.common.utils.CommonUtils
import net.hyntech.police.R
import net.hyntech.common.R as CR
//type = 0 已安装，1待缴费
class EbikeRegisterAdapter(val context: Context,val type:Int = 0) : BaseAdapter<EbikeRegisterAdapter.ViewHolder>() {

    private val installColor = UIUtils.getColor(CR.color.common_color_green)
    private val pendingColor = UIUtils.getColor(CR.color.common_color_badge)

    private val list: MutableList<RegisterListEntity.AtEbikeListBean> = arrayListOf()

    private var listener: OnClickListener? = null

    fun setListener(listener: OnClickListener?) {
        this.listener = listener
    }

    fun setData(data: List<RegisterListEntity.AtEbikeListBean>) {
        this.list.clear()
        this.list.addAll(data)
        notifyDataSetChanged()
    }

    fun updataList(data: List<RegisterListEntity.AtEbikeListBean>) {
        this.list.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = context.layoutInflater.inflate(R.layout.item_register_list, parent, false)
        val holder: ViewHolder = ViewHolder(view)
        holder.tvPay.setOnClickListener {
            if (!UIUtils.isFastDoubleClick()) {
                listener?.onEditClick(list.get(holder.adapterPosition))
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
        val tvPay: TextView = itemView.findViewById(R.id.tv_pay)
        private val tvEbikeNo: TextView = itemView.findViewById(R.id.tv_ebike_no)
        private val tvEbikeState: TextView = itemView.findViewById(R.id.tv_ebike_state)
        private val tvEbikeName: TextView = itemView.findViewById(R.id.tv_ebike_name)
        private val tvEbikeTel: TextView = itemView.findViewById(R.id.tv_ebike_tel)
        private val tvIdCard: TextView = itemView.findViewById(R.id.tv_idcard)
        private val tvService: TextView = itemView.findViewById(R.id.tv_service)
        private val tvTimeTitle: TextView = itemView.findViewById(R.id.tv_time_title)
        private val tvTimeContent: TextView = itemView.findViewById(R.id.tv_time_content)
        private val viewLine: View = itemView.findViewById(R.id.view_line)
        private val llPay: LinearLayout = itemView.findViewById(R.id.ll_pay)


        fun setData(entity: RegisterListEntity.AtEbikeListBean?) {
            entity?.let {
                tvEbikeNo.text = "${it.ebikeNo}"
                tvEbikeName.text = "${it.userName}"
                tvEbikeTel.text = "${it.phone}"
                tvIdCard.text = "${it.idNo}"
                tvService.text = "${it.insuranceProductName}"
                if(type == 0){
                    tvEbikeState.text = "已安装"
                    tvEbikeState.setTextColor(installColor)
                    tvTimeTitle.text = "安装日期"
                    viewLine.visibility = View.GONE
                    llPay.visibility = View.GONE
                }else{
                    tvEbikeState.text = "待付费"
                    tvEbikeState.setTextColor(pendingColor)
                    tvTimeTitle.text = "登记日期"
                    viewLine.visibility = View.VISIBLE
                    llPay.visibility = View.VISIBLE
                }
                tvTimeContent.text = "${CommonUtils.splitDate(it.createTime)}"
            }
        }
    }

    interface OnClickListener {
        fun onEditClick(item: RegisterListEntity.AtEbikeListBean?)
    }
}