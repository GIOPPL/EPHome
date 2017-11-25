package com.gioppl.ephome.forum

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.gioppl.ephome.FinalValue
import com.gioppl.ephome.R
import com.gioppl.ephome.login.Login
import com.gioppl.ephome.network.ForumEntity
import com.gioppl.ephome.network.ForumRequestInterface
import com.xlibs.xrv.LayoutManager.XGridLayoutManager
import com.xlibs.xrv.LayoutManager.XLinearLayoutManager
import com.xlibs.xrv.LayoutManager.XStaggeredGridLayoutManager
import com.xlibs.xrv.listener.OnLoadMoreListener
import com.xlibs.xrv.listener.OnRefreshListener
import com.xlibs.xrv.view.XRecyclerView
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

    private var isFirstLoad = true
    private var mHeaderView: View? = null
    private var mFooterView: View? = null
    var im_add: ImageView? = null
    var mXRecyclerView: XRecyclerView? = null
    var mAdapt: ForumAdapt? = null
    var mList: ArrayList<ForumBean>? = ArrayList()
    var layoutManager: GridLayoutManager? = null
    var lin_forum: LinearLayout? = null
    var entityList: ForumEntity? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?)
            = inflater!!.inflate(R.layout.forum_pager, container, false)!!

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        initRV()
//        initDate()
        if (isFirstLoad) getDataRefresh(FinalValue.getForumRefreshFlag(), FinalValue.getForumRefreshFlag() + 5)
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
        mXRecyclerView = activity.findViewById(R.id.rv_forum) as XRecyclerView?
        mAdapt = ForumAdapt(mList, activity)
//        layoutManager = GridLayoutManager(activity, 1);//set hte number of column on this boundary
//        mRV!!.layoutManager = layoutManager
//        layoutManager!!.orientation = OrientationHelper.VERTICAL
//        mRV!!.adapter = mAdapt
        val xLinearLayoutManager = XLinearLayoutManager(activity)
        val xGridLayoutManager = XGridLayoutManager(activity, 2)
        val xStaggeredGridLayoutManager = XStaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        mXRecyclerView!!.layoutManager = xLinearLayoutManager
        mHeaderView = LayoutInflater.from(activity).inflate(R.layout.custom_header_view, null)
        mFooterView = LayoutInflater.from(activity).inflate(R.layout.footer_view, null)
        mXRecyclerView!!.addHeaderView(mHeaderView, 50)
        mXRecyclerView!!.addFootView(mFooterView, 50)
//        initData()
        mXRecyclerView!!.adapter = mAdapt
        mXRecyclerView!!.setOnLoadMoreListener(OnLoadMoreListener { loadMoreData() })
        mXRecyclerView!!.setOnRefreshListener(OnRefreshListener { refreshData() })
    }

    private fun refreshData() {
        getDataRefresh(FinalValue.getForumRefreshFlag(), FinalValue.getForumRefreshFlag() + 5)
        Handler().postDelayed({
            mXRecyclerView!!.refreshComplate()
        }, 1000)

    }

    private fun loadMoreData() {
        getDataLoad(FinalValue.getForumRefreshFlag(), FinalValue.getForumRefreshFlag() + 5)
        Handler().postDelayed({
            mXRecyclerView!!.loadMoreComplate()
        }, 1000)
    }

//    /**
//     * 在界面能看到的时候加载数据
//     */
//    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
//        super.setUserVisibleHint(isVisibleToUser)
//        FinalValue.successMessage("setUserVisibleHint:$isVisibleToUser")
//        if ((!isFirstLoad)&&isVisibleToUser) getData(0,5)
//    }


    //从后台获取数据
    fun getDataRefresh(form: Int, to: Int) {
        var moviesUrl = "http://116.196.91.8:8080/webtest/"
        var retrofit = Retrofit
                .Builder().baseUrl(moviesUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
        var myNet = retrofit.create(ForumRequestInterface::class.java)
        myNet.data(form, to)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<ForumEntity>() {
                    override fun onError(e: Throwable?) {
                        Log.i("error", e!!.message)
                    }

                    override fun onCompleted() {
                    }

                    override fun onNext(t: ForumEntity) {
                        FinalValue.successMessage("获取论坛成功")

                        entityList = t;
//                        mList!!.clear()
                        for (i in t.data) {
                            val bean = ForumBean(i.telephone, i.address, i.authorid, i.dataline, i.subject, i.message, i.author)
                            mList!!.add(bean);
                        }
                        mAdapt!!.notifyDataSetChanged()
                        lin_forum!!.visibility = View.GONE
                        FinalValue.clearForumRefreshFlag()
                    }
                })
    }

    //从后台获取数据
    fun getDataLoad(form: Int, to: Int) {
        var moviesUrl = "http://116.196.91.8:8080/webtest/"
        var retrofit = Retrofit
                .Builder().baseUrl(moviesUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
        var myNet = retrofit.create(ForumRequestInterface::class.java)
        myNet.data(form, to)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<ForumEntity>() {
                    override fun onError(e: Throwable?) {
                        Log.i("error", e!!.message)
                    }

                    override fun onCompleted() {

                    }

                    override fun onNext(t: ForumEntity) {
                        Log.i("success", "成功")
                        FinalValue.successMessage("获取论坛成功")
                        entityList = t!!;
                        for (i in t!!.data) {
                            val bean = ForumBean(i.telephone, i.address, i.authorid, i.dataline, i.subject, i.message, i.author)
                            mList!!.add(bean);
                        }
                        mAdapt!!.notifyDataSetChanged()
                        lin_forum!!.visibility = View.GONE
                    }
                })
    }
}