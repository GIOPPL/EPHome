package com.gioppl.ephome.policy

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gioppl.ephome.R
import com.gioppl.ephome.forum.PolicyAdapt

/**
 * Created by GIOPPL on 2017/10/8.
 */

class PolicyPager : Fragment() {
    var rv_policy:RecyclerView?=null
    var mList:ArrayList<PolicyModel>?=null
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.policy, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initDate()
        SetAdaptManager()
    }

    private fun initDate() {
        mList= ArrayList()
        val mod=activity.resources
        mList!!.add(PolicyModel(mod.getString(R.string.policy_1_title),mod.getString(R.string.policy_1_msg),mod.getString(R.string.policy_1_content)))
        mList!!.add(PolicyModel(mod.getString(R.string.policy_2_title),mod.getString(R.string.policy_2_msg),mod.getString(R.string.policy_2_content)))
        mList!!.add(PolicyModel(mod.getString(R.string.policy_3_title),mod.getString(R.string.policy_3_msg),mod.getString(R.string.policy_3_content)))
        mList!!.add(PolicyModel(mod.getString(R.string.policy_4_title),mod.getString(R.string.policy_4_msg),mod.getString(R.string.policy_4_content)))
        mList!!.add(PolicyModel(mod.getString(R.string.policy_5_title),mod.getString(R.string.policy_5_msg),mod.getString(R.string.policy_5_content)))
        mList!!.add(PolicyModel(mod.getString(R.string.policy_6_title),mod.getString(R.string.policy_6_msg),mod.getString(R.string.policy_6_content)))
        mList!!.add(PolicyModel(mod.getString(R.string.policy_7_title),mod.getString(R.string.policy_7_msg),mod.getString(R.string.policy_7_content)))
        mList!!.add(PolicyModel(mod.getString(R.string.policy_8_title),mod.getString(R.string.policy_8_msg),mod.getString(R.string.policy_8_content)))
        mList!!.add(PolicyModel(mod.getString(R.string.policy_9_title),mod.getString(R.string.policy_9_msg),mod.getString(R.string.policy_9_content)))
        mList!!.add(PolicyModel(mod.getString(R.string.policy_10_title),mod.getString(R.string.policy_10_msg),mod.getString(R.string.policy_10_content)))
        mList!!.add(PolicyModel(mod.getString(R.string.policy_11_title),mod.getString(R.string.policy_11_msg),mod.getString(R.string.policy_11_content)))
    }

    //set adapt
    private fun SetAdaptManager() {
        rv_policy= activity.findViewById(R.id.rv_policy) as RecyclerView
        val layoutManager = LinearLayoutManager(activity)
        rv_policy!!.layoutManager=layoutManager
        rv_policy!!.setHasFixedSize(true)
        layoutManager.orientation = OrientationHelper.VERTICAL
        var mAdapt = PolicyAdapt(mList!!, activity)
        rv_policy!!.adapter=mAdapt
    }
}
