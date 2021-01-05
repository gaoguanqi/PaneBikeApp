package net.hyntech.police.vm

import android.graphics.drawable.Drawable
import androidx.lifecycle.MutableLiveData
import net.hyntech.baselib.base.BaseViewModel
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.R
import net.hyntech.common.model.entity.PositionData
import net.hyntech.common.model.entity.ShopSiteEntity
import net.hyntech.common.model.repository.CommonRepository
import java.util.*

class ShopSiteViewModel:BaseViewModel() {

    private val repository by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { CommonRepository() }

    val defDrawable: Drawable by lazy { UIUtils.getDrawable(R.drawable.shape_solid) }
    val sefDrawable: Drawable by lazy { UIUtils.getDrawable(R.drawable.shape_stroke) }
    val defColor:Int by lazy { UIUtils.getColor(R.color.common_black) }
    val sefColor:Int by lazy { UIUtils.getColor(R.color.common_color_text) }


    val position: MutableLiveData<PositionData> = MutableLiveData()

    val shopDetails: MutableLiveData<ShopSiteEntity.AtServiceShopBean> = MutableLiveData()



    fun getShopDetails(serviceShopId: String) {
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("serviceShopId",serviceShopId)
            repository.getShopDetails(params)
        }, success = {
            it?.let {data ->
                shopDetails.postValue(data.atServiceShop)
            }
        })
    }



}