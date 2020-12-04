package net.hyntech.common.model.api


class ApiURL {
    companion object{

        private const val PREFIX_URL = "/antitheft/v1/user/usual"
//        private const val PREFIX_URL = "/antitheft/v1/user/police"


        private const val PREFIX_SMS_URL = "/antitheft/v1/sms/usual"
//        private const val PREFIX_SMS_URL = "/antitheft/v1/sms/police"

        const val URL_USER_LOGIN = PREFIX_URL + "/login.thtml"
        const val URL_SMS_VERIFY_CODE = PREFIX_SMS_URL + "/send_code.thtml"
        const val URL_MESSAGE_COUNT = PREFIX_URL + "/message_count.thtml"
    }
}