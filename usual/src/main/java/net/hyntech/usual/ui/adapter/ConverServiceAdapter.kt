package net.hyntech.usual.ui.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import net.hyntech.baselib.app.BaseApp
import net.hyntech.baselib.utils.LogUtils
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.base.BaseAdapter
import net.hyntech.common.base.BaseViewHolder
import net.hyntech.common.ext.layoutInflater
import net.hyntech.common.model.entity.ConverServiceEntity
import net.hyntech.common.utils.CommonUtils
import net.hyntech.common.utils.TextSpanUtils
import net.hyntech.common.widget.imgloader.ImageLoader
import net.hyntech.common.widget.imgloader.TransType
import net.hyntech.common.widget.imgloader.glide.GlideImageConfig
import net.hyntech.usual.R
import net.hyntech.common.R as CR

class ConverServiceAdapter(val context: Context):BaseAdapter<ConverServiceAdapter.ViewHolder>() {

    private val draWzDark:Drawable = UIUtils.getDrawable(CR.drawable.ic_wz_dark)
    private val draWzLight:Drawable = UIUtils.getDrawable(CR.drawable.ic_wz_light)

    private val list: MutableList<ConverServiceEntity.AtServiceShopListBean> = arrayListOf()

    private var listener: OnClickListener? = null

    fun setListener(listener: OnClickListener?){
        this.listener = listener
    }

    fun setData(data:List<ConverServiceEntity.AtServiceShopListBean>){
        this.list.clear()
        this.list.addAll(data)
        notifyDataSetChanged()
    }

    fun updataList(data:List<ConverServiceEntity.AtServiceShopListBean>){
        this.list.addAll(data)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view:View = context.layoutInflater.inflate(R.layout.item_conver_service,parent,false)
        val holder: ViewHolder = ViewHolder(view)
        holder.itemRoot.setOnClickListener {
            if(!UIUtils.isFastDoubleClick()){
                listener?.onItemClick(list.get(holder.adapterPosition))
            }
        }
        holder.ivCallPhone.setOnClickListener {
            if(!UIUtils.isFastDoubleClick()){
                listener?.onCallPhoneClick(list.get(holder.adapterPosition).phone)
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

    inner class ViewHolder(itemView: View): BaseViewHolder(itemView){
        private val tvName:TextView = itemView.findViewById(R.id.tv_name)
        private val tvAddress:TextView = itemView.findViewById(R.id.tv_address)
        private val tvDistance:TextView = itemView.findViewById(R.id.tv_distance)
        private val tvStore:TextView = itemView.findViewById(R.id.tv_store)
        private val tvFix:TextView = itemView.findViewById(R.id.tv_fix)
        private val tvPower:TextView = itemView.findViewById(R.id.tv_power)
        private val ivImg:ImageView = itemView.findViewById(R.id.iv_img)
        val ivCallPhone:ImageView = itemView.findViewById(R.id.iv_call_phone)
        val itemRoot:LinearLayout = itemView.findViewById(R.id.item_root)

        fun setData(entity: ConverServiceEntity.AtServiceShopListBean?) {
            entity?.let {
                tvName.text = "${it.shopName}"
                tvAddress.text = TextSpanUtils.spanIconEnd(it.addr,draWzLight)
                tvDistance.text = "相距${it.distance}km"
                tvStore.visibility = if(CommonUtils.filterShopType("销售门店",it.shopType)) View.VISIBLE else View.GONE
                tvFix.visibility = if(CommonUtils.filterShopType("维修站",it.shopType)) View.VISIBLE else View.GONE
                tvPower.visibility = if(CommonUtils.filterShopType("充电站",it.shopType)) View.VISIBLE else View.GONE
                ImageLoader.getInstance().loadImage(
                    BaseApp.instance,
                    GlideImageConfig(CommonUtils.splitPic(it.relevantPic), ivImg).also { config-> config.type = TransType.NORMAL })
            }
        }
    }

    interface OnClickListener{
        fun onItemClick(item: ConverServiceEntity.AtServiceShopListBean?)
        fun onCallPhoneClick(phone: String)
    }
}