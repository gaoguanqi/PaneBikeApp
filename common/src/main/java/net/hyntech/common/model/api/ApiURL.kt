package net.hyntech.common.model.api

import net.hyntech.common.global.Global


class ApiURL {
    companion object{


        // https://api.hyntech.net/app/v1/user/usual/get_user_info.thtml
//            private const val EXT = "/app"
            private const val EXT = "/antitheft"

//        private const val PREFIX_URL = "/antitheft/v1/user/usual"
//        private const val PREFIX_URL = "/antitheft/v1/user/police"


        //常用前缀
//        private const val PREFIX_URL = "${EXT}/v1/user/usual"
        private const val PREFIX_URL = "${EXT}/v1/user/police"


        // 特殊的 获取短信 前缀
//        private const val PREFIX_SMS_URL = "${EXT}/v1/sms/usual"
        private const val PREFIX_SMS_URL = "${EXT}/v1/sms/police"

        // 特殊的 上传图片 前缀
//        private const val PREFIX_OSS_URL = "${EXT}/v1/oss/usual"
        private const val PREFIX_OSS_URL = "${EXT}/v1/oss/police"


        //用户登录
        const val URL_USER_LOGIN = "${PREFIX_URL}/login.thtml"
        //获取短信验证码
        const val URL_SMS_VERIFY_CODE = "${PREFIX_SMS_URL}/send_code.thtml"
        //获取消息数量
        const val URL_MESSAGE_COUNT = "${PREFIX_URL}/message_count.thtml"
        //获取用户信息
        const val URL_USER_INFO = "${PREFIX_URL}/get_user_info.thtml"
        //上传图片
        const val URL_UPLOAD_IMG = "${PREFIX_OSS_URL}/img_upload.thtml"

        //修改头像
        const val URL_EDIT_HEADIMGURL = "${PREFIX_URL}/edit_headimgurl.thtml"

        //----------民用版--------------------------------------------------------------------------------------------------
        //民用版 锁车/解锁 车辆
        const val URL_EBIKE_LOCK = "${EXT}/v1/ebike/usual/ebike_lock.thtml"
        //民用版 车辆异常信息
        const val URL_EBIKE_ERROR = "${EXT}/v1/alarm_exception_log/usual/list.thtml"
        //民用版 忽略车辆异常信息
        const val URL_EBIKE_IGNORE = "${EXT}/v1/alarm_exception_log/usual/ignore.thtml"
        //民用版 报警记录
        const val URL_ALARM_RECORD = "${EXT}/v1/alarm/usual/list.thtml"
        //民用版 报警提交
        const val URL_SUBMIT_ALARM = "${EXT}/v1/alarm/usual/create.thtml"
        //民用版 我的保单
        const val URL_MY_ORDER = "${EXT}/v1/service_package/usual/my_list.thtml"
        //民用版 服务包列表
        const val URL_SERVICE_PACKAGE = "${EXT}/v1/service_package/usual/list.thtml"
        //民用版 服务详情
        const val URL_SERVICE_DETAIL = "${EXT}/v1/service_package/usual/detail.thtml"
        //民用版 理赔流程
        const val URL_CLAIM_PROCESS = "${EXT}/v1/service_package/usual/claim_process.thtml"
        //民用版 保额信息
        const val URL_INSURANCE_COVERANGE = "${EXT}/v1/ebike/usual/ebike_insurance_coverange.thtml"
        //民用版 保障信息
        const val URL_TO_PAY = "${EXT}/v1/service_package/usual/to_pay.thtml"
        //民用版 便民服务
        const val URL_CONVER_SERVICE = "${EXT}/v1/service_shop/usual/list.thtml"
        //民用版 车辆位置搜索(民用版V2新接口，带增值服务)
        const val URL_LOCATION_SEARCH = "${EXT}/v2/ebike/usual/location_search.thtml"
        //民用版 增值服务列表
        const val URL_ADDVAL_SERVICE = "${EXT}/v2/valueAddedService/usual/list.thtml"
        //民用版 增值服务详情
        const val URL_ADDVAL_DETAIL = "${EXT}/v2/valueAddedService/usual/detail.thtml"
        //民用版 增值服务的车辆服务信息
        const val URL_ADDVAL_INFO = "${EXT}/v2/valueAddedServiceOrder/usual/pre_order.thtml"

        //----------警用版--------------------------------------------------------------------------------------------------
        //警用版 搜索设备
        const val URL_SEARCH_DEVICE = "${EXT}/v1/collector/police/search.thtml"
        //警用版 车辆查找
        const val URL_FIND_EBIKE = "${EXT}/v1/ebike/police/location_search.thtml"
        //警用版 已找回提交
        const val URL_EBIKE_FOUND = "${EXT}/v1/alarm/police/found.thtml"
        //警用版 点位管理 采集器列表
        const val URL_COLLECTOR_LIST = "${EXT}/v1/collector/police/list.thtml"
        //警用版 点位（采集器）添加、修改
        const val URL_COLLECTOR_SAVE = "${EXT}/v1/collector/police/save.thtml"
        //web url

        //我的保单
         val WEB_URL_MY_ORDER = "${Global.BASE_WEB_URL}/page/my/grxx/wdbd.html"
    }
}