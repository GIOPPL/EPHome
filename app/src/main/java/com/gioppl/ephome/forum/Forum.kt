package com.gioppl.ephome.forum

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.gioppl.ephome.R
import com.gioppl.ephome.forum.request.ForumRequest

/**
 * Created by GIOPPL on 2017/10/6.
 */
class Forum: Fragment(){
    var im_add:ImageView?=null
    var mRV: RecyclerView?=null
    var mAdapt: ForumAdapt?=null
    var mList:ArrayList<ForumBean> ?= ArrayList()
    var layoutManager :GridLayoutManager?=null
    var request:ForumRequest?=null
    var lin_forum:LinearLayout?=null
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?)
            = inflater!!.inflate(R.layout.forum_pager, container, false)!!

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        initRV()
        initDate()
    }

    private fun initView() {
        lin_forum= activity.findViewById(R.id.lin_forum) as LinearLayout?
        im_add= activity.findViewById(R.id.im_top_add) as ImageView?
        im_add!!.setOnClickListener(View.OnClickListener {
            startActivity(Intent(activity, AddForumPost::class.java))
        })
    }

    private fun initDate() {
        lin_forum!!.visibility=View.GONE
        request= ForumRequest(activity,ForumRequest.ForumData { beanList ->
            for (bean in beanList){
                mList!!.add(bean)
                mAdapt!!.notifyDataSetChanged()
            }
        })
    }

    //初始化recyclerView
    private fun initRV() {
        mRV= activity.findViewById(R.id.rv_forum) as RecyclerView?
        mAdapt= ForumAdapt(mList!!,activity)
        layoutManager = GridLayoutManager(activity,1);//set hte number of column on this boundary
        mRV!!.setLayoutManager(layoutManager)
        layoutManager!!.orientation = OrientationHelper.VERTICAL
        mRV!!.adapter=mAdapt
    }

    override fun onResume() {
        super.onResume()

    }
}