package com.gioppl.ephome.policy

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gioppl.ephome.FinalValue
import com.gioppl.ephome.PostRequest
import com.gioppl.ephome.R
import com.gioppl.ephome.forum.PolicyAdapt
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.xlibs.xrv.LayoutManager.XLinearLayoutManager
import com.xlibs.xrv.listener.OnLoadMoreListener
import com.xlibs.xrv.listener.OnRefreshListener
import com.xlibs.xrv.view.XRecyclerView
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by GIOPPL on 2017/10/8.
 */

class PolicyPager : Fragment() {
    var policyList=ArrayList<PolicyEntity>()
    private var mHeaderView: View? = null
    var mXRecyclerView: XRecyclerView? = null
    var mAdapt: PolicyAdapt? = null
    private var mFooterView: View? = null
    private var firstLoad=true
    var from=1;
    var to=20;

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.policy, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initDate()
        SetAdaptManager()
        mXRecyclerView!!.performClick()
        mXRecyclerView!!.scrollToPosition(2)
    }


    private fun initDate() {
        val map = HashMap<String, Any>()
        map.put("from", from)
        map.put("to", to)
        PostRequest(map, FinalValue.INTERFACE_POLICY, PostRequest.POST, object : PostRequest.RequestCallback {
            override fun success(back: String) {
                for (i in formatBeanList(back.substring(20, back.length - 1)))
                    policyList.add(i)
                mAdapt!!.notifyDataSetChanged()
                mXRecyclerView!!.performClick()
//                if (policyList.size < 5)
                if (firstLoad) {
                    mXRecyclerView!!.scrollToPosition(2)
                    firstLoad=false
                }
//                else
//                    mXRecyclerView!!.scrollToPosition(policyList.size - 1)
            }

            override fun error(back: String) {
                FinalValue.toast(activity, back)
            }

            override fun getBeanList(bean: java.util.ArrayList<Any>) {

            }
        })
    }

    //set adapt
    private fun SetAdaptManager() {
//        mXRecyclerView= activity.findViewById(R.id.rv_policy) as XRecyclerView
//        val layoutManager = LinearLayoutManager(activity)
//        mXRecyclerView!!.layoutManager=layoutManager
//        mXRecyclerView!!.setHasFixedSize(true)
//        layoutManager.orientation = OrientationHelper.VERTICAL
//        mAdapt = PolicyAdapt(FinalValue.policyList, activity)
//        mXRecyclerView!!.adapter=mAdapt
        mXRecyclerView = activity.findViewById(R.id.rv_policy) as XRecyclerView?
        mAdapt = PolicyAdapt(policyList, activity)
        val xLinearLayoutManager = XLinearLayoutManager(activity)
//        val xGridLayoutManager = XGridLayoutManager(activity, 2)
//        val xStaggeredGridLayoutManager = XStaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        mXRecyclerView!!.layoutManager = xLinearLayoutManager as RecyclerView.LayoutManager?
        mFooterView = LayoutInflater.from(activity).inflate(R.layout.footer_view, null)
        mHeaderView = LayoutInflater.from(activity).inflate(R.layout.custom_header_view, null)
        mXRecyclerView!!.addHeaderView(mHeaderView, 50)
        mXRecyclerView!!.addFootView(mFooterView, 50)
//        initData()
        mXRecyclerView!!.adapter = mAdapt
        mXRecyclerView!!.setOnLoadMoreListener(OnLoadMoreListener { loadMoreData() })
        mXRecyclerView!!.setOnRefreshListener(OnRefreshListener { refreshData() })
    }

    private fun refreshData() {
        Handler().postDelayed({
            mXRecyclerView!!.refreshComplate()
        }, 1000)

    }
    private fun loadMoreData() {
        initDate()
        Handler().postDelayed({
            mXRecyclerView!!.loadMoreComplate()
        }, 1000)
        mXRecyclerView!!.performClick()
    }


    private fun formatBeanList(json: String): java.util.ArrayList<PolicyEntity> {
        val list: java.util.ArrayList<PolicyEntity>
        val listType = object : TypeToken<List<PolicyEntity>>() {}.type
        val gson = Gson()
        list = gson.fromJson<java.util.ArrayList<PolicyEntity>>(json, listType)
        return list
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser)mXRecyclerView!!.performClick()
    }

}
