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
        private const val PREFIX_URL = "${EXT}/v1/user/usual"
//        private const val PREFIX_URL = "${EXT}/v1/user/police"


        // 特殊的 获取短信 前缀
        private const val PREFIX_SMS_URL = "${EXT}/v1/sms/usual"
//        private const val PREFIX_SMS_URL = "${EXT}/v1/sms/police"

        // 特殊的 上传图片 前缀
        private const val PREFIX_OSS_URL = "${EXT}/v1/oss/usual"
//        private const val PREFIX_OSS_URL = "${EXT}/v1/oss/police"


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



        //web url

        //我的保单
         val WEB_URL_MY_ORDER = "${Global.BASE_WEB_URL}/page/my/grxx/wdbd.html"
    }
}