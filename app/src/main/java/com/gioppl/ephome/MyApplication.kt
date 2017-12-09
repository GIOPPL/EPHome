package com.gioppl.ephome

import android.app.Application
import android.support.multidex.MultiDex
import com.avos.avoscloud.AVOSCloud
import com.facebook.drawee.backends.pipeline.Fresco

/**
 * Created by GIOPPL on 2017/10/6.
 */
class MyApplication: Application(){
    override fun onCreate() {
        super.onCreate()
        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this,"RxsNxJjwRkGlmzJfkJrclWDQ-gzGzoHsz","U0rkTwOIN7AEn7EtMMIHIahs");
        Fresco.initialize(this);
        AVOSCloud.setDebugLogEnabled(true);
        MultiDex.install(this);
    }
}