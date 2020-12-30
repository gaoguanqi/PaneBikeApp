package net.hyntech.police.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import net.hyntech.baselib.app.BaseApp
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.base.BaseAdapter
import net.hyntech.common.base.BaseViewHolder
import net.hyntech.common.ext.layoutInflater
import net.hyntech.common.model.entity.UserInfoEntity
import net.hyntech.common.utils.CommonUtils
import net.hyntech.common.widget.imgloader.ImageLoader
import net.hyntech.common.widget.imgloader.TransType
import net.hyntech.common.widget.imgloader.glide.GlideImageConfig
import net.hyntech.police.R

class EbikeInfoAdapter (val context: Context) : BaseAdapter<EbikeInfoAdapter.ViewHolder>() {

    private val list: MutableList<UserInfoEntity.EbikeListBean> = arrayListOf()

    private var listener: OnClickListener? = null

    fun setListener(listener: OnClickListener?) {
        this.listener = listener
    }


    fun setData(data: List<UserInfoEntity.EbikeListBean>) {
        this.list.clear()
        this.list.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = context.layoutInflater.inflate(R.layout.item_ebike_info, parent, false)
        val holder: ViewHolder = ViewHolder(view)
        holder.tvEditEbike.setOnClickListener {
            if (!UIUtils.isFastDoubleClick()) {
                listener?.onEditClick(holder.adapterPosition,list)
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
        private val tvEbikeNo: TextView = itemView.findViewById(R.id.tv_ebike_no)
        private val tvLabelNo: TextView = itemView.findViewById(R.id.tv_label_no)
        private val tvFrameNo: TextView = itemView.findViewById(R.id.tv_frame_no)
        private val tvBrand: TextView = itemView.findViewById(R.id.tv_brand)
        private val tvEngine: TextView = itemView.findViewById(R.id.tv_engine)
        private val tvColor: TextView = itemView.findViewById(R.id.tv_color)
        private val tvEbikeType: TextView = itemView.findViewById(R.id.tv_ebike_type)
        private val tvBuyPrice: TextView = itemView.findViewById(R.id.tv_buy_price)
        private val tvBuyTime: TextView = itemView.findViewById(R.id.tv_buy_time)
        private val tvService: TextView = itemView.findViewById(R.id.tv_service)
        private val ivEbikeA: ImageView = itemView.findViewById(R.id.iv_ebike_a)
        private val ivEbikeB: ImageView = itemView.findViewById(R.id.iv_ebike_b)
        private val ivLabelLoc: ImageView = itemView.findViewById(R.id.iv_label_loc)
        private val ivInvoice: ImageView = itemView.findViewById(R.id.iv_invoice)
        val tvEditEbike: TextView = itemView.findViewById(R.id.tv_edit_ebike)

        fun setData(entity: UserInfoEntity.EbikeListBean?) {
            entity?.let {
                tvEbikeNo.text = "${it.ebikeNo}"
                tvLabelNo.text = "${it.locatorNo}"
                tvFrameNo.text = "${it.frameNo}"
                tvBrand.text = "${it.ebikeType}"
                tvEngine.text = "${it.engineNo}"
                tvColor.text = "${it.ebikeColor}"
                tvEbikeType.text = "${it.typeName}"
                val price = it.price/100.0
                tvBuyPrice.text = "${price}"
                tvBuyTime.text = "${CommonUtils.splitDate(it.buyTime)}"
                tvService.text = "${it.insuranceProductName} ${it.termRange}年版(${it.insurancePrice/100}元)"

                ImageLoader.getInstance().apply {
                    this.loadImage(BaseApp.instance,
                        GlideImageConfig(it.ebikePic1, ivEbikeA).also { config-> config.type = TransType.NORMAL })
                    this.loadImage(BaseApp.instance,
                        GlideImageConfig(it.ebikePic2, ivEbikeB).also { config-> config.type = TransType.NORMAL })
                    this.loadImage(BaseApp.instance,
                        GlideImageConfig(it.locatorPic, ivLabelLoc).also { config-> config.type = TransType.NORMAL })
                    this.loadImage(BaseApp.instance,
                        GlideImageConfig(it.invoicePic, ivInvoice).also { config-> config.type = TransType.NORMAL })
                }
            }
        }
    }

    interface OnClickListener {
        fun onEditClick(pos:Int,list: List<UserInfoEntity.EbikeListBean>)
    }


}