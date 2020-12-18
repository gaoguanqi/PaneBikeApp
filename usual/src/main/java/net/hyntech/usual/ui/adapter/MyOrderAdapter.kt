package net.hyntech.usual.ui.adapter

import android.content.Context
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.base.BaseAdapter
import net.hyntech.common.base.BaseViewHolder
import net.hyntech.common.ext.layoutInflater
import net.hyntech.common.model.entity.MyOrderEntity
import net.hyntech.common.utils.DateUtils
import net.hyntech.usual.R
import net.hyntech.common.R as CR

class MyOrderAdapter(val context: Context): BaseAdapter<MyOrderAdapter.ViewHolder>() {

    private val list: MutableList<MyOrderEntity.ListBean> = arrayListOf()

    private var listener: OnClickListener? = null

    fun setListener(listener: OnClickListener?){
        this.listener = listener
    }

    fun setData(data:List<MyOrderEntity.ListBean>){
        this.list.clear()
        this.list.addAll(data)
        notifyDataSetChanged()
    }

    fun updataList(data:List<MyOrderEntity.ListBean>){
        this.list.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view:View = context.layoutInflater.inflate(R.layout.item_my_order,parent,false)
        val holder: ViewHolder = ViewHolder(view)
        holder.tvBuyNow.setOnClickListener {
            if(!UIUtils.isFastDoubleClick()){
                listener?.onBuyNowClick(list.get(holder.adapterPosition))
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


    inner class ViewHolder(itemView: View): BaseViewHolder(itemView){
        private val tvEbikeNo:TextView = itemView.findViewById(R.id.tv_ebike_no)
        private val tvTitle:TextView = itemView.findViewById(R.id.tv_title)
        private val tvPrice:TextView = itemView.findViewById(R.id.tv_price)
        private val tvNotPay:TextView = itemView.findViewById(R.id.tv_not_pay)
        private val ivState:ImageView = itemView.findViewById(R.id.iv_state)
        val tvBuyNow:TextView = itemView.findViewById(R.id.tv_buy_now)
        private val tvTime:TextView = itemView.findViewById(R.id.tv_time)
        private val viewLine:View = itemView.findViewById(R.id.view_line)

        fun setData(pos:Int,entity: MyOrderEntity.ListBean?) {
            entity?.let {
                tvEbikeNo.text = it.ebikeNo
                tvTitle.text = "${it.insuranceProductName}  ${it.termRange}年版"
                tvPrice.text = "总价：￥${it.insurancePrice/100}"
                val state = it.state
                if(TextUtils.equals("not_pay",state)){
                    tvNotPay.visibility = View.VISIBLE
                    ivState.visibility = View.GONE
                    tvBuyNow.visibility = View.VISIBLE
                    tvTime.visibility = View.GONE
                }else{
                    ivState.visibility = View.VISIBLE
                    tvNotPay.visibility = View.GONE
                    tvBuyNow.visibility = View.GONE
                    tvTime.visibility = View.VISIBLE

                    if(TextUtils.equals("not_work",state) || TextUtils.equals("working",state)){
                        ivState.background = UIUtils.getDrawable(CR.drawable.pic_ygm)
                    }else if(TextUtils.equals("insurance_expired",state) || TextUtils.equals("compensate",state)){
                        ivState.background = UIUtils.getDrawable(CR.drawable.pic_ysx)
                    }else if(DateUtils.isExpire(it.endTimeInsurance)){
                        ivState.background = UIUtils.getDrawable(CR.drawable.pic_jjdq)
                    }
                }

                if(TextUtils.equals("not_work",state) || TextUtils.equals("working",state) || TextUtils.equals("insurance_expired",state)){
                    tvTime.text = "服务周期${it.beginTimeInsurance}至${it.endTimeInsurance}"
                }else if(TextUtils.equals("compensate",state)){
                    tvTime.text = "车辆丢失，已赔付"
                }
            }
            viewLine.visibility = if(pos + 1 == list.size) View.GONE else View.VISIBLE
        }
    }

    interface OnClickListener{
        fun onBuyNowClick(item:MyOrderEntity.ListBean?)
    }
}