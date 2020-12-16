package net.hyntech.usual.ui.adapter

import android.content.Context
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.base.BaseAdapter
import net.hyntech.common.base.BaseViewHolder
import net.hyntech.common.ext.layoutInflater
import net.hyntech.common.model.entity.MyOrderEntity
import net.hyntech.common.utils.DateUtils
import net.hyntech.usual.R
import net.hyntech.common.R as CR
import net.hyntech.usual.databinding.ItemMyOrderBinding

class MyOrderAdapter(val context: Context): BaseAdapter<MyOrderAdapter.ViewHolder>() {

    private lateinit var binding: ItemMyOrderBinding

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
        binding = DataBindingUtil.inflate<ItemMyOrderBinding>(parent.context.layoutInflater,
            R.layout.item_my_order,
            parent,
            false)
        val holder: ViewHolder = ViewHolder(binding.root)
        binding.tvBuyNow.setOnClickListener {
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

        fun setData(pos:Int,entity: MyOrderEntity.ListBean?) {
            entity?.let {
                binding.tvEbikeNo.text = it.ebikeNo
                binding.tvTitle.text = "${it.insuranceProductName}  ${it.termRange}年版"
                binding.tvPrice.text = "总价：￥${it.insurancePrice/100}"
                val state = it.state
                if(TextUtils.equals("not_pay",state)){
                    binding.tvNotPay.visibility = View.VISIBLE
                    binding.ivState.visibility = View.GONE
                    binding.tvBuyNow.visibility = View.VISIBLE
                    binding.tvTime.visibility = View.GONE
                }else{
                    binding.ivState.visibility = View.VISIBLE
                    binding.tvNotPay.visibility = View.GONE
                    binding.tvBuyNow.visibility = View.GONE
                    binding.tvTime.visibility = View.VISIBLE

                    if(TextUtils.equals("not_work",state) || TextUtils.equals("working",state)){
                        binding.ivState.background = UIUtils.getDrawable(CR.drawable.pic_ygm)
                    }else if(TextUtils.equals("insurance_expired",state) || TextUtils.equals("compensate",state)){
                        binding.ivState.background = UIUtils.getDrawable(CR.drawable.pic_ysx)
                    }else if(DateUtils.isExpire(it.endTimeInsurance)){
                        binding.ivState.background = UIUtils.getDrawable(CR.drawable.pic_jjdq)
                    }
                }

                if(TextUtils.equals("not_work",state) || TextUtils.equals("working",state) || TextUtils.equals("insurance_expired",state)){
                    binding.tvTime.text = "服务周期${it.beginTimeInsurance}至${it.endTimeInsurance}"
                }else if(TextUtils.equals("compensate",state)){
                    binding.tvTime.text = "车辆丢失，已赔付"
                }
            }
            binding.viewLine.visibility = if(pos + 1 == list.size) View.GONE else View.VISIBLE
        }
    }

    interface OnClickListener{
        fun onBuyNowClick(item:MyOrderEntity.ListBean?)
    }
}