package com.gioppl.ephome.forum

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import com.facebook.drawee.view.SimpleDraweeView
import com.gioppl.ephome.FinalValue
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
    var sim_goods:SimpleDraweeView?=null

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
        sim_goods= findViewById(R.id.sim_formDetail_goods) as SimpleDraweeView?
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    fun helloEventBus(eventBus: ForumBean) {
        if (eventBus.uimage!="") {
            sim_head!!.setImageURI(eventBus.uimage)
        }
        if (eventBus.goods!="")
            sim_goods!!.setImageURI(eventBus.goods)
        else
            sim_goods!!.visibility=View.GONE
        tv_title!!.text=eventBus.title
        tv_content!!.text=eventBus.content
        if (FinalValue.LOAD_STA){
            if (!eventBus.isHide){
                tv_phone!!.text = eventBus.iphone
            }else{
                tv_phone!!.text ="该帖设置手机号隐藏"
            }
        }else{
            tv_phone!!.text ="未登录手机号码不可见"
        }
        tv_msg!!.text = eventBus.date + "\t" + eventBus.address
    }
    public fun back(v: View){
        finish()
    }
}