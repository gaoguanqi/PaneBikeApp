package net.hyntech.baselib.http

class ResponseThrowable : Exception {
    var code: String
    var errMsg: String

    constructor(error: ERROR) {
        code = error.getKey()
        errMsg = error.getValue()
    }

    constructor(code: String, msg: String) {
        this.code = code
        this.errMsg = msg
    }
}