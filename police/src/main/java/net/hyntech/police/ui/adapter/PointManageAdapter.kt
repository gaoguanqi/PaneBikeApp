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
import net.hyntech.common.model.entity.CollectorListEntity
import net.hyntech.police.R
import net.hyntech.common.R as CR

class PointManageAdapter(val context: Context) : BaseAdapter<PointManageAdapter.ViewHolder>() {

    private val installColor = UIUtils.getColor(CR.color.common_color_green)
    private val unInstallColor = UIUtils.getColor(CR.color.common_color_badge)

    private val list: MutableList<CollectorListEntity.AtCollectorListBean> = arrayListOf()

    private var listener: OnClickListener? = null

    fun setListener(listener: OnClickListener?) {
        this.listener = listener
    }

    fun setData(data: List<CollectorListEntity.AtCollectorListBean>) {
        this.list.clear()
        this.list.addAll(data)
        notifyDataSetChanged()
    }

    fun updataList(data: List<CollectorListEntity.AtCollectorListBean>) {
        this.list.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = context.layoutInflater.inflate(R.layout.item_point_manage, parent, false)
        val holder: ViewHolder = ViewHolder(view)
        holder.tvEdit.setOnClickListener {
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
        val itemRoot: LinearLayout = itemView.findViewById(R.id.item_root)
        private val tvCollectorNo: TextView = itemView.findViewById(R.id.tv_collector_no)
        private val tvCollectorState: TextView = itemView.findViewById(R.id.tv_collector_state)
        private val tvLookNo: TextView = itemView.findViewById(R.id.tv_look_no)
        private val tvAddress: TextView = itemView.findViewById(R.id.tv_address)
        private val tvLatLng: TextView = itemView.findViewById(R.id.tv_latlng)
        private val tvTimeTitle: TextView = itemView.findViewById(R.id.tv_time_title)
        private val tvTime: TextView = itemView.findViewById(R.id.tv_upload_time)
        val tvEdit: TextView = itemView.findViewById(R.id.tv_edit)

        fun setData(entity: CollectorListEntity.AtCollectorListBean?) {
            entity?.let {
                tvCollectorNo.text = "${it.collectorNo}"
                tvLookNo.text = "${it.collectorNo110}"
                tvAddress.text = "${it.addr}"
                tvLatLng.text = "(${it.lat},${it.lng})"


                val installFlag = it.installFlag
                if (installFlag == 1) {
                    tvCollectorState.text = "已安装"
                    tvCollectorState.setTextColor(installColor)
                    tvTimeTitle.text = "安装时间"
                } else {
                    tvCollectorState.text = "未安装"
                    tvCollectorState.setTextColor(unInstallColor)
                    tvTimeTitle.text = "上传时间"
                }
                tvTime.text = "${it.createTime}"
            }
        }
    }

    interface OnClickListener {
        fun onEditClick(item: CollectorListEntity.AtCollectorListBean?)
    }
}