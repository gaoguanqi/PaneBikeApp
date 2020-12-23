package net.hyntech.common.model.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.hyntech.baselib.base.BaseRepository
import net.hyntech.common.model.RetrofitClient
import net.hyntech.common.utils.HttpParamsUtils
import okhttp3.MultipartBody
import java.util.*

open class CommonRepository:BaseRepository() {

    private val retrofitClient = RetrofitClient.service

    override fun getPublicParams(addToken:Boolean): WeakHashMap<String, Any> {
        return HttpParamsUtils.addPublicRequestParams(addToken)
    }

    suspend fun getOrgList() = withContext(Dispatchers.IO) {
        retrofitClient.getOrgList()
    }

    suspend fun loginPhone(params:WeakHashMap<String, Any>) = withContext(Dispatchers.IO) {
        retrofitClient.loginPhone(params2Body(params))
    }

    suspend fun getVerifyCode(params: WeakHashMap<String, Any>) = withContext(Dispatchers.IO) {
        retrofitClient.getVerifyCode(params2Body(params,true,false))
    }

    suspend fun getMessageCount(params: WeakHashMap<String, Any>) = withContext(Dispatchers.IO) {
        retrofitClient.getMessageCount(params2Body(params))
    }

    suspend fun getUserInfo(params: WeakHashMap<String, Any>) = withContext(Dispatchers.IO) {
        retrofitClient.getUserInfo(params2Body(params))
    }

    suspend fun ebikeLock(params: WeakHashMap<String, Any>) = withContext(Dispatchers.IO) {
        retrofitClient.ebikeLock(params2Body(params))
    }

    suspend fun uploadImageList(partList:List<MultipartBody.Part>) = withContext(Dispatchers.IO){
        retrofitClient.uploadImageList(partList)
    }

    suspend fun editHeadImage(params: WeakHashMap<String, Any>) = withContext(Dispatchers.IO){
        retrofitClient.editHeadImage(params2Body(params))
    }

    suspend fun getEbikeErrorList(params: WeakHashMap<String, Any>) = withContext(Dispatchers.IO){
        retrofitClient.getEbikeErrorList(params2Body(params))
    }

    suspend fun ignoreEbikeError(params: WeakHashMap<String, Any>) = withContext(Dispatchers.IO){
        retrofitClient.ignoreEbikeError(params2Body(params))
    }

    suspend fun getAlarmRecordList(params: WeakHashMap<String, Any>) = withContext(Dispatchers.IO){
        retrofitClient.getAlarmRecordList(params2Body(params))
    }

    suspend fun submitAlarm(params: WeakHashMap<String, Any>) = withContext(Dispatchers.IO) {
        retrofitClient.submitAlarm(params2Body(params))
    }

    suspend fun getMyOrderList(params: WeakHashMap<String, Any>) = withContext(Dispatchers.IO) {
        retrofitClient.getMyOrderList(params2Body(params))
    }

    suspend fun getServiceList(params: WeakHashMap<String, Any>) = withContext(Dispatchers.IO) {
        retrofitClient.getServiceList(params2Body(params))
    }

    suspend fun getServiceDetails(params: WeakHashMap<String, Any>) = withContext(Dispatchers.IO) {
        retrofitClient.getServiceDetails(params2Body(params))
    }

    suspend fun getClaimProcess(params: WeakHashMap<String, Any>) = withContext(Dispatchers.IO) {
        retrofitClient.getClaimProcess(params2Body(params))
    }

    suspend fun getInsuranceCoverange(params: WeakHashMap<String, Any>) = withContext(Dispatchers.IO) {
        retrofitClient.getInsuranceCoverange(params2Body(params))
    }

    suspend fun getSafeInfo(params: WeakHashMap<String, Any>) = withContext(Dispatchers.IO) {
        retrofitClient.getSafeInfo(params2Body(params))
    }

    suspend fun getServiceShopList(params: WeakHashMap<String, Any>) = withContext(Dispatchers.IO) {
        retrofitClient.getServiceShopList(params2Body(params))
    }

    suspend fun locationSearch(params: WeakHashMap<String, Any>) = withContext(Dispatchers.IO) {
        retrofitClient.locationSearch(params2Body(params))
    }

    suspend fun getAddValServiceList(params: WeakHashMap<String, Any>) = withContext(Dispatchers.IO) {
        retrofitClient.getAddValServiceList(params2Body(params))
    }

    suspend fun getAddValServiceDetails(params: WeakHashMap<String, Any>) = withContext(Dispatchers.IO) {
        retrofitClient.getAddValServiceDetails(params2Body(params))
    }

    suspend fun getAddValInfo(params: WeakHashMap<String, Any>) = withContext(Dispatchers.IO) {
        retrofitClient.getAddValInfo(params2Body(params))
    }

    //--------警用端--------------------------------
    suspend fun searchDecive(params: WeakHashMap<String, Any>) = withContext(Dispatchers.IO) {
        retrofitClient.searchDecive(params2Body(params))
    }
}