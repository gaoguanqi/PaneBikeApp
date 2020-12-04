package net.hyntech.common.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.youth.banner.adapter.BannerAdapter
import kotlinx.android.synthetic.main.item_banner.view.*
import kotlinx.android.synthetic.main.item_org.view.*
import net.hyntech.baselib.app.BaseApp
import net.hyntech.baselib.ext.layoutInflater
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.R
import net.hyntech.common.base.BaseViewHolder
import net.hyntech.common.model.entity.BannerEntity
import net.hyntech.common.widget.imgloader.ImageLoader
import net.hyntech.common.widget.imgloader.TransType
import net.hyntech.common.widget.imgloader.glide.GlideImageConfig

class MyBannerAdapter(val context: Context,val list:List<BannerEntity>) : BannerAdapter<BannerEntity, MyBannerAdapter.BannerViewHolder>(list) {

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): BannerViewHolder {
        val view: View = context.layoutInflater.inflate(R.layout.item_banner,parent,false)
        val holder = BannerViewHolder(view)
        return holder
    }

    override fun onBindView(
        holder: BannerViewHolder?,
        data: BannerEntity?,
        position: Int,
        size: Int
    ) {
        holder?.setData(list.get(position))
    }

    inner class BannerViewHolder(itemView: View) : BaseViewHolder(itemView) {
        fun setData(entity: BannerEntity?) {
            entity?.let {
                itemView.findViewById<ImageView>(R.id.iv_img)?.let {iv->
                    ImageLoader.getInstance().loadImage(
                        BaseApp.instance,
                        GlideImageConfig(UIUtils.getDrawable(it.resId), iv).also { it.type = TransType.NORMAL })
                }
            }
        }
    }

}