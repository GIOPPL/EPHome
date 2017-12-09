package com.gioppl.ephome.policy

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import com.gioppl.ephome.HomePager.entity.PollutionEntity
import com.gioppl.ephome.R
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by GIOPPL on 2017/10/9.
 */
class PollutionDetail: AppCompatActivity() {
    var tv_title:TextView?=null
    var tv_msg:TextView?=null
    var tv_content:TextView?=null
    var eventBus: PollutionEntity?=null//上一个界面传过来的数据
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.policy_detail)
        EventBus.getDefault().register(this)
        initView()
        initData()
    }

    private fun initData() {
        tv_title!!.text=eventBus!!.environmental_type
        tv_msg!!.visibility=View.GONE
        tv_content!!.text=eventBus!!.content
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    fun helloEventBus(eventBus: PollutionEntity) {
        this.eventBus=eventBus
    }

    private fun initView() {
        tv_title= findViewById(R.id.tv_policyDetail_title) as TextView?
        tv_msg= findViewById(R.id.tv_policyDetail_msg) as TextView?
        tv_content= findViewById(R.id.tv_policyDetail_content) as TextView?
    }
    public fun back(view:View){
        finish()
    }
}