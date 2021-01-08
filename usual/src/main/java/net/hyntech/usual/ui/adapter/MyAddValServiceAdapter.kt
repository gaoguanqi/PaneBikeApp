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
import net.hyntech.common.model.entity.MyAddValServiceEntity
import net.hyntech.common.utils.CommonUtils
import net.hyntech.usual.R
import net.hyntech.common.R as CR

class MyAddValServiceAdapter (val context: Context): BaseAdapter<MyAddValServiceAdapter.ViewHolder>() {

    private val list: MutableList<MyAddValServiceEntity.ListBean> = arrayListOf()

    private var listener: OnClickListener? = null

    fun setListener(listener: OnClickListener?) {
        this.listener = listener
    }

    fun setData(data: List<MyAddValServiceEntity.ListBean>) {
        this.list.clear()
        this.list.addAll(data)
        notifyDataSetChanged()
    }

    fun updataList(data: List<MyAddValServiceEntity.ListBean>) {
        this.list.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = context.layoutInflater.inflate(R.layout.item_my_addval_service, parent, false)
        val holder: ViewHolder = ViewHolder(view)
        holder.tvBuyNow.setOnClickListener {
            if (!UIUtils.isFastDoubleClick()) {
                listener?.onBuyNowClick(list.get(holder.adapterPosition))
            }
        }
        return holder
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(position, list.get(position))
    }


    inner class ViewHolder(itemView: View) : BaseViewHolder(itemView) {
        private val tvEbikeNo: TextView = itemView.findViewById(R.id.tv_ebike_no)
        private val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        private val tvPrice: TextView = itemView.findViewById(R.id.tv_price)
        private val tvNotPay: TextView = itemView.findViewById(R.id.tv_not_pay)
        private val ivState: ImageView = itemView.findViewById(R.id.iv_state)
        val tvBuyNow: TextView = itemView.findViewById(R.id.tv_buy_now)
        private val tvTime: TextView = itemView.findViewById(R.id.tv_time)

        fun setData(pos: Int, entity: MyAddValServiceEntity.ListBean?) {
            entity?.let {
                tvEbikeNo.text = "${it.ebikeNo}"
                tvTitle.text = "${it.valueAddedServiceName}"
                tvPrice.text = "总价：￥${it.payMoney / 100}"
                tvBuyNow.visibility = View.GONE
                ivState.visibility = View.GONE
                tvNotPay.visibility = View.GONE
                tvTime.visibility = View.INVISIBLE

                val state = it.state
                if(TextUtils.equals("not_pay",state)){
                    tvBuyNow.visibility = View.VISIBLE
                    tvNotPay.visibility = View.VISIBLE
                    tvBuyNow.text = "立即付款"
                }else if(TextUtils.equals("expired",state)){
                    ivState.visibility = View.VISIBLE
                    ivState.background = UIUtils.getDrawable(CR.drawable.pic_ysx)
                    tvTime.visibility = View.VISIBLE
                    tvTime.text = "${CommonUtils.splitDate(it.beginTime)} 至 ${CommonUtils.splitDate(it.endTime)}"
                    tvTime.setTextColor(UIUtils.getColor(CR.color.common_color_badge))
                }else if(TextUtils.equals("working",state)){
                    ivState.visibility = View.VISIBLE
                    ivState.background = UIUtils.getDrawable(CR.drawable.pic_ygm)
                    tvTime.visibility = View.VISIBLE
                    tvTime.text = "${CommonUtils.splitDate(it.beginTime)} 至 ${CommonUtils.splitDate(it.endTime)}"
                    tvTime.setTextColor(UIUtils.getColor(CR.color.common_gray_text))
                }else if(TextUtils.equals("soon_to_expire",state)){
                    ivState.visibility = View.VISIBLE
                    ivState.background = UIUtils.getDrawable(CR.drawable.pic_jjdq)
                    tvTime.text = "${CommonUtils.splitDate(it.beginTime)} 至 ${CommonUtils.splitDate(it.endTime)}"
                    tvTime.setTextColor(UIUtils.getColor(CR.color.common_color_badge))
                }
            }
        }
    }

    interface OnClickListener {
        fun onBuyNowClick(item: MyAddValServiceEntity.ListBean?)
    }
}