package com.gioppl.ephome.forum

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.gioppl.ephome.FinalValue
import com.gioppl.ephome.R
import com.gioppl.ephome.forum.request.ForumRequest
import com.gioppl.ephome.login.Login
import com.gioppl.ephome.network.ForumEntity
import com.gioppl.ephome.network.ForumRequestInterface
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by GIOPPL on 2017/10/6.
 */
class Forum : Fragment() {


    var im_add: ImageView? = null
    var mRV: RecyclerView? = null
    var mAdapt: ForumAdapt? = null
    var mList: ArrayList<ForumBean>? = ArrayList()
    var layoutManager: GridLayoutManager? = null
    var request: ForumRequest? = null
    var lin_forum: LinearLayout? = null
    var entityList:ForumEntity?=null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?)
            = inflater!!.inflate(R.layout.forum_pager, container, false)!!

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        initRV()
//        initDate()
        getData()
    }

    private fun initView() {
        lin_forum = activity.findViewById(R.id.lin_forum) as LinearLayout?
        im_add = activity.findViewById(R.id.im_top_add) as ImageView?
        im_add!!.setOnClickListener(View.OnClickListener {
            if (FinalValue.LOAD_STA) {
                startActivity(Intent(activity, AddForumPost::class.java))
            } else {
                FinalValue.toast(activity, "请登陆后再操作^_^\"")
                startActivity(Intent(activity, Login::class.java))
            }
        })
    }

    private fun initDate() {
//        lin_forum!!.visibility = View.GONE
//        request = ForumRequest(activity, ForumRequest.ForumData {beanList ->
//            for (bean in beanList) {
//                mList!!.add(bean)
//                mAdapt!!.notifyDataSetChanged()
//            }
//        })
    }

    //初始化recyclerView
    private fun initRV() {
        mRV = activity.findViewById(R.id.rv_forum) as RecyclerView?
        mAdapt = ForumAdapt(mList, activity)
        layoutManager = GridLayoutManager(activity, 1);//set hte number of column on this boundary
        mRV!!.layoutManager = layoutManager
        layoutManager!!.orientation = OrientationHelper.VERTICAL
        mRV!!.adapter = mAdapt
    }

    override fun onResume() {
        super.onResume()
    }

    fun getData() {
        var moviesUrl="http://116.196.91.8:8080/webtest/"
        var retrofit= Retrofit
                .Builder().baseUrl(moviesUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
        var myNet=retrofit.create(ForumRequestInterface::class.java)
        myNet.data(0,5)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<ForumEntity>() {
                    override fun onError(e: Throwable?) {
                        Log.i("error",e!!.message)
                    }

                    override fun onCompleted() {
                    }

                    override fun onNext(t: ForumEntity) {
                        Log.i("success","成功")
                        FinalValue.successMessage("获取论坛成功")
                        lin_forum!!.visibility = View.GONE
                        entityList=t!!;
                        for (i in t!!.data){
                            val bean=ForumBean(i. telephone, i. address, i. authorid, i. dataline, i. subject, i. message, i. author)
                            mList!!.add(bean);
                        }
                        mAdapt!!.notifyDataSetChanged()
                    }
                })


    }
}