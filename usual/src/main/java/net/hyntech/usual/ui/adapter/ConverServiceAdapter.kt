package net.hyntech.usual.ui.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.*
import android.text.style.DynamicDrawableSpan.ALIGN_BOTTOM
import android.text.style.ImageSpan
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import net.hyntech.baselib.app.BaseApp
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
import net.hyntech.usual.databinding.ItemConverServiceBinding

class ConverServiceAdapter(val context: Context):BaseAdapter<ConverServiceAdapter.ViewHolder>() {

    private val draWzDark:Drawable = UIUtils.getDrawable(CR.drawable.ic_wz_dark)
    private val draWzLight:Drawable = UIUtils.getDrawable(CR.drawable.ic_wz_light)

    private lateinit var binding: ItemConverServiceBinding

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
        binding = DataBindingUtil.inflate<ItemConverServiceBinding>(parent.context.layoutInflater,
            R.layout.item_conver_service,
            parent,
            false)
        val holder: ViewHolder = ViewHolder(binding.root)
        binding.itemRoot.setOnClickListener {
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

    inner class ViewHolder(itemView: View): BaseViewHolder(itemView){

        fun setData(entity: ConverServiceEntity.AtServiceShopListBean?) {
            entity?.let {
                binding.tvName.text = "${it.shopName}"
                binding.tvAddress.text = TextSpanUtils.spanIconEnd(it.addr,draWzLight)
                binding.tvDistance.text = "相距${it.distance}km"
                binding.tvStore.visibility = if(CommonUtils.filterShopType("销售门店",it.shopType)) View.VISIBLE else View.GONE
                binding.tvFix.visibility = if(CommonUtils.filterShopType("维修站",it.shopType)) View.VISIBLE else View.GONE
                binding.tvPower.visibility = if(CommonUtils.filterShopType("充电站",it.shopType)) View.VISIBLE else View.GONE
                ImageLoader.getInstance().loadImage(
                    BaseApp.instance,
                    GlideImageConfig(CommonUtils.splitPic(it.relevantPic), binding.ivImg).also { config-> config.type = TransType.NORMAL })
            }
        }
    }

    interface OnClickListener{
        fun onItemClick(item: ConverServiceEntity.AtServiceShopListBean?)
    }
}