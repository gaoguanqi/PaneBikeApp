package net.hyntech.common.model.api

import net.hyntech.common.model.entity.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService:BaseApi {

    @Headers("urlname:center")
    @GET("/index.json")
    suspend fun getOrgList(): CenterEntity

    @Headers("urlname:hyntech")
    @POST(ApiURL.URL_USER_LOGIN)
    suspend fun loginPhone(@Body requestBody: RequestBody): UserEntity

    @Headers("urlname:hyntech")
    @POST(ApiURL.URL_SMS_VERIFY_CODE)
    suspend fun getVerifyCode(@Body requestBody: RequestBody): CommonEntity

    @Headers("urlname:hyntech")
    @POST(ApiURL.URL_MESSAGE_COUNT)
    suspend fun getMessageCount(@Body requestBody: RequestBody): MessageCountEntity

    @Headers("urlname:hyntech")
    @POST(ApiURL.URL_USER_INFO)
    suspend fun getUserInfo(@Body requestBody: RequestBody): UserInfoEntity

    @Headers("urlname:hyntech")
    @POST(ApiURL.URL_EBIKE_LOCK)
    suspend fun ebikeLock(@Body requestBody: RequestBody): CommonEntity

    @Headers("urlname:hyntech")
    @Multipart
    @POST(ApiURL.URL_UPLOAD_IMG)
    suspend fun uploadImageList(@Part partList: List<MultipartBody.Part>): UploadImgEntity

    @Headers("urlname:hyntech")
    @POST(ApiURL.URL_IDCARD_DISTINGUISH)
    suspend fun idCardDistinguish(@Body requestBody: RequestBody): UserInfoEntity

    @Headers("urlname:hyntech")
    @POST(ApiURL.URL_EDIT_HEADIMGURL)
    suspend fun editHeadImage(@Body requestBody: RequestBody): CommonEntity

    @Headers("urlname:hyntech")
    @POST(ApiURL.URL_EBIKE_ERROR)
    suspend fun getEbikeErrorList(@Body requestBody: RequestBody): EbikeErrorEntity

    @Headers("urlname:hyntech")
    @POST(ApiURL.URL_EBIKE_IGNORE)
    suspend fun ignoreEbikeError(@Body requestBody: RequestBody): CommonEntity

    @Headers("urlname:hyntech")
    @POST(ApiURL.URL_ALARM_RECORD)
    suspend fun getAlarmRecordList(@Body requestBody: RequestBody): AlarmRecordEntity

    @Headers("urlname:hyntech")
    @POST(ApiURL.URL_SUBMIT_ALARM)
    suspend fun submitAlarm(@Body requestBody: RequestBody): CommonEntity

    @Headers("urlname:hyntech")
    @POST(ApiURL.URL_MY_ORDER)
    suspend fun getMyOrderList(@Body requestBody: RequestBody): MyOrderEntity

    @Headers("urlname:hyntech")
    @POST(ApiURL.URL_USUAL_SERVICE_PACKAGE)
    suspend fun getServiceList(@Body requestBody: RequestBody): ServiceSafeEntity

    @Headers("urlname:hyntech")
    @POST(ApiURL.URL_SERVICE_DETAIL)
    suspend fun getServiceDetails(@Body requestBody: RequestBody): ServiceDetailEntity

    @Headers("urlname:hyntech")
    @POST(ApiURL.URL_CLAIM_PROCESS)
    suspend fun getClaimProcess(@Body requestBody: RequestBody): ClaimProcessEntity

    @Headers("urlname:hyntech")
    @POST(ApiURL.URL_INSURANCE_COVERANGE)
    suspend fun getInsuranceCoverange(@Body requestBody: RequestBody): InsuranceCoverangeEntity

    @Headers("urlname:hyntech")
    @POST(ApiURL.URL_TO_PAY)
    suspend fun getSafeInfo(@Body requestBody: RequestBody): SafeInfoEntity

    @Headers("urlname:hyntech")
    @POST(ApiURL.URL_CONVER_SERVICE)
    suspend fun getServiceShopList(@Body requestBody: RequestBody): ConverServiceEntity

    @Headers("urlname:hyntech")
    @POST(ApiURL.URL_LOCATION_SEARCH)
    suspend fun locationSearch(@Body requestBody: RequestBody): EbikeTrackEntity

    @Headers("urlname:hyntech")
    @POST(ApiURL.URL_ADDVAL_SERVICE)
    suspend fun getAddValServiceList(@Body requestBody: RequestBody): AddValServiceEntity

    @Headers("urlname:hyntech")
    @POST(ApiURL.URL_ADDVAL_DETAIL)
    suspend fun getAddValServiceDetails(@Body requestBody: RequestBody): AddValDetailEntity

    @Headers("urlname:hyntech")
    @POST(ApiURL.URL_ADDVAL_INFO)
    suspend fun getAddValInfo(@Body requestBody: RequestBody): AddValInfoEntity

    //---------警用端开始-----------------------
    @Headers("urlname:hyntech")
    @POST(ApiURL.URL_SEARCH_DEVICE)
    suspend fun searchDecive(@Body requestBody: RequestBody): DeviceInfoEntity

    @Headers("urlname:hyntech")
    @POST(ApiURL.URL_FIND_EBIKE)
    suspend fun findEbike(@Body requestBody: RequestBody): EbikeTrackEntity

    @Headers("urlname:hyntech")
    @POST(ApiURL.URL_EBIKE_FOUND)
    suspend fun commitFindEbike(@Body requestBody: RequestBody): CommonEntity

    @Headers("urlname:hyntech")
    @POST(ApiURL.URL_COLLECTOR_LIST)
    suspend fun getCollectorList(@Body requestBody: RequestBody): CollectorListEntity

    @Headers("urlname:hyntech")
    @POST(ApiURL.URL_COLLECTOR_SAVE)
    suspend fun collectorSave(@Body requestBody: RequestBody): CommonEntity

    @Headers("urlname:hyntech")
    @POST(ApiURL.URL_POLICE_SERVICE_PACKAGE)
    suspend fun getServicePackage(@Body requestBody: RequestBody): ServiceSafeEntity

    @Headers("urlname:hyntech")
    @POST(ApiURL.URL_EBIKE_REG_INFO)
    suspend fun getEbikeRegInfo(@Body requestBody: RequestBody): EbikeRegInfoEntity

    @Headers("urlname:hyntech")
    @POST(ApiURL.URL_EBIKE_BRAND)
    suspend fun getEbikeBrand(@Body requestBody: RequestBody): EbikeBrandEntity

    @Headers("urlname:hyntech")
    @POST(ApiURL.URL_EBIKE_REG)
    suspend fun registerEbike(@Body requestBody: RequestBody): EbikeRegEntity

    @Headers("urlname:hyntech")
    @POST(ApiURL.URL_MARK_PAY)
    suspend fun paymentConfirm(@Body requestBody: RequestBody): PaymentConfirmEntity

    @Headers("urlname:hyntech")
    @POST(ApiURL.URL_REG_LIST)
    suspend fun getRegisterList(@Body requestBody: RequestBody): RegisterListEntity

    @Headers("urlname:hyntech")
    @POST(ApiURL.URL_ALARM_LIST)
    suspend fun getAlarmInfoList(@Body requestBody: RequestBody): AlarmInfoEntity

    @Headers("urlname:hyntech")
    @POST(ApiURL.URL_ALARM_MARK_READ)
    suspend fun alarmMarkRead(@Body requestBody: RequestBody): CommonEntity

    //---------警用端结束-----------------------

}