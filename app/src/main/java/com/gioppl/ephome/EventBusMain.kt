package com.gioppl.ephome

/**
 * Created by GIOPPL on 2017/10/26.
 */

class EventBusMain(status: Boolean,head_photo_status:Boolean) {
    internal var login_status = false
    internal var head_photo_status=false

    init {
        this.login_status = status
        this.head_photo_status = head_photo_status
    }
}
