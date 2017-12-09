package com.gioppl.ephome.HomePager

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.view.View
import com.gioppl.ephome.FinalValue
import com.gioppl.ephome.HomePager.entity.PollutionEntity
import com.gioppl.ephome.HomePager.net.PollutionPost
import com.gioppl.ephome.R
import com.gioppl.ephome.forum.PollutionAdapt

/**
 * Created by GIOPPL on 2017/11/28.
 */
class Pollution :AppCompatActivity() {
    var rv_policy:RecyclerView?=null
    var mAdapt:PollutionAdapt?=null
    var mList=ArrayList<PollutionEntity>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.polltion)
        SetAdaptManager()
        initDate()
    }

    private fun initDate() {
        PollutionPost(this@Pollution,object :PollutionPost.PollutionCallback{
            override fun success(list: java.util.ArrayList<PollutionEntity>) {
                mList!!.clear()
                for (i in list){
                    mList!!.add(i)
                }
                mAdapt!!.notifyDataSetChanged()
            }

            override fun error(error: String) {
                FinalValue.toast(this@Pollution,error)
            }
        })
    }

    //set adapt
    private fun SetAdaptManager() {
        rv_policy= findViewById(R.id.rv_policy) as RecyclerView
        val layoutManager = LinearLayoutManager(this)
        rv_policy!!.layoutManager=layoutManager
        rv_policy!!.setHasFixedSize(true)
        layoutManager.orientation = OrientationHelper.VERTICAL
        mAdapt = PollutionAdapt(mList!!, this)
        rv_policy!!.adapter=mAdapt
    }
    public fun back(view:View){
        finish()
    }

}