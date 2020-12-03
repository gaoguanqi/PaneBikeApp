package net.hyntech.common.widget.popu

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.hyntech.common.R
import net.hyntech.common.base.BaseAdapter
import razerdp.basepopup.BasePopupWindow

class EBikeListPopu<T : BaseAdapter<RecyclerView.ViewHolder>>(
    val context: Context,
    val adapter: T
) : BasePopupWindow(context) {


    override fun onCreateContentView(): View {
        return createPopupById(R.layout.popu_ebike_list)
    }

    override fun onViewCreated(contentView: View) {
        super.onViewCreated(contentView)
        contentView.findViewById<RecyclerView>(R.id.rv_ebike)?.apply {
            this.layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }
    }

}