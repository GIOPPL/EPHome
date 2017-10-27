package com.gioppl.ephome.forum

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import com.facebook.drawee.view.SimpleDraweeView
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
    var tv_msg:TextView?=null
    var tv_phone:TextView?=null
    var sim_head:SimpleDraweeView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.forum_detail)

        initView()
        EventBus.getDefault().register(this)
    }

    private fun initView() {
        tv_title= findViewById(R.id.tv_formDetail_title) as TextView?
        tv_content= findViewById(R.id.tv_formDetail_content) as TextView?
        tv_msg= findViewById(R.id.tv_formDetail_msg) as TextView?
        tv_phone= findViewById(R.id.tv_formDetail_phone) as TextView?
        sim_head= findViewById(R.id.sim_formDetail_head) as SimpleDraweeView?
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    fun helloEventBus(eventBus: ForumBean) {
        val bean=eventBus.serverData
        tv_title!!.text=bean.title
        tv_content!!.text=bean.content
        tv_phone!!.text=bean.person
    }
    public fun back(v: View){
        finish()
    }
}