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
        var base_url=SharedPreferencesUtils.getParam(this,"base_url","http://192.168.42.234:8080") as String
        FinalValue.BASE_URL=base_url;
        FinalJAVA.BaseUrl=base_url;
    }
}