package com.gioppl.ephome.forum

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import com.gioppl.ephome.R
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by GIOPPL on 2017/10/8.
 */
class ForumDetails :AppCompatActivity() {
    var tv_title:TextView?=null
    var tv_content:TextView?=null
    var tv_userName:TextView?=null
    var tv_msg:TextView?=null
    var tv_phone:TextView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.forum_detail)
        EventBus.getDefault().register(this)
        initView()
    }

    private fun initView() {

    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    fun helloEventBus(eventBus: ForumBean) {

    }
    public fun back(v: View){
        finish()
    }
}