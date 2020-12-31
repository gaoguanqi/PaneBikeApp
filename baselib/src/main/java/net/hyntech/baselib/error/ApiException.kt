package net.hyntech.baselib.error

import java.lang.RuntimeException


class ApiException:RuntimeException {
    constructor():super()
    constructor(message:String):super(message)
}