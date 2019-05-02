package com.gioppl.ephome.forum

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.gioppl.ephome.FinalValue
import com.gioppl.ephome.PostRequest
import com.gioppl.ephome.R
import com.gioppl.ephome.SharedPreferencesUtils
import com.gioppl.ephome.sliding.login.Login
import com.gioppl.ephome.voice.VoiceActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.xlibs.xrv.LayoutManager.XLinearLayoutManager
import com.xlibs.xrv.listener.OnLoadMoreListener
import com.xlibs.xrv.listener.OnRefreshListener
import com.xlibs.xrv.view.XRecyclerView
import java.util.HashMap
import kotlin.collections.ArrayList

/**
 * Created by GIOPPL on 2017/10/6.
 */
class Forum : Fragment() {

    private var mHeaderView: View? = null
    private var mFooterView: View? = null
    var im_add: ImageView? = null
    var im_search: ImageView? = null
    var mXRecyclerView: XRecyclerView? = null
    var mAdapt: ForumAdapt? = null
    var mList = ArrayList<ForumBean>()
    var layoutManager: GridLayoutManager? = null
    var lin_forum: LinearLayout? = null
    var from = 1;
    var to = 10;
    private var firstLoad=true

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?)
            = inflater!!.inflate(R.layout.forum_pager, container, false)!!

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        from = 10
        to = 20
        initView()
        initRV()
        initDate()
    }

    private fun initView() {
        lin_forum = activity.findViewById(R.id.lin_forum) as LinearLayout?
        im_add = activity.findViewById(R.id.im_top_add) as ImageView?
        im_search = activity.findViewById(R.id.im_top_search) as ImageView?
        im_add!!.setOnClickListener(View.OnClickListener {
            if (FinalValue.LOAD_STA) {
                startActivity(Intent(activity, AddForumPost::class.java))
            } else {
                FinalValue.toast(activity, "请登陆后再操作^_^\"")
                startActivity(Intent(activity, Login::class.java))
            }
        })
        im_search!!.setOnClickListener(View.OnClickListener {
            startActivity(Intent(activity, VoiceActivity::class.java))
        })
    }

    private fun initDate() {
        val map = HashMap<String, Any>()
        map.put("from", 0)
        map.put("to", 10);
        var base_url= SharedPreferencesUtils.getParam(activity,"base_url","错误url")as String
        PostRequest(map, base_url+FinalValue.INTERFACE_FORUM, PostRequest.POST, object : PostRequest.RequestCallback {
            override fun success(back: String) {
                val list = formatForumBean(back)
                mList.clear()
                for (i in list) {
                    mList.add(0, i)
                }
                from++
                lin_forum!!.visibility = View.GONE
                mAdapt!!.notifyDataSetChanged()
// /                if (mList.size < 5)
//                    mXRecyclerView!!.scrollToPosition(2)
//                else
//                    mXRecyclerView!!.scrollToPosition(mList.size - 1)
                    mXRecyclerView!!.scrollToPosition(1)
            }

            override fun error(back: String) {
                Log.i("失败", back)
            }

            override fun getBeanList(bean: java.util.ArrayList<Any>) {

            }
        })
    }

    private fun loadMoreDate() {
        val map = HashMap<String, Any>()
        map.put("from", from)
        map.put("to", to)
        var base_url=SharedPreferencesUtils.getParam(activity,"base_url","错误url")as String
        PostRequest(map, base_url+FinalValue.INTERFACE_FORUM_LOAD_MORE, PostRequest.POST, object : PostRequest.RequestCallback {
            override fun success(back: String) {
                val list = formatForumBean(back)
                for (i in list) {
                    mList.add(0,i)
                }
                from += 10
                to += 10
                lin_forum!!.visibility = View.GONE
                mAdapt!!.notifyDataSetChanged()
            }

            override fun error(back: String) {
                Log.i("失败", back)
            }

            override fun getBeanList(bean: java.util.ArrayList<Any>) {

            }
        })
    }

    //初始化recyclerView
    private fun initRV() {
        mXRecyclerView = activity.findViewById(R.id.rv_forum) as XRecyclerView?
        mAdapt = ForumAdapt(mList, activity)
        val xLinearLayoutManager = XLinearLayoutManager(activity)
        mXRecyclerView!!.layoutManager = xLinearLayoutManager as RecyclerView.LayoutManager?
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
        initDate()
        Handler().postDelayed({
            mXRecyclerView!!.refreshComplate()
        }, 1000)

    }

    private fun loadMoreData() {
        loadMoreDate()
        Handler().postDelayed({
            mXRecyclerView!!.loadMoreComplate()
        }, 1000)
    }

    private fun formatForumBean(json: String): java.util.ArrayList<ForumBean> {
        val list: java.util.ArrayList<ForumBean>
        val listType = object : TypeToken<List<ForumBean>>() {

        }.type
        val gson = Gson()
        list = gson.fromJson<java.util.ArrayList<ForumBean>>(json, listType)
        return list
    }

}