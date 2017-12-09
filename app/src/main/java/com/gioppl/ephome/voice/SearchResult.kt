package com.gioppl.ephome.voice

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import com.gioppl.ephome.FatherActivity
import com.gioppl.ephome.R
import com.gioppl.ephome.forum.ForumAdapt
import com.gioppl.ephome.forum.ForumBean
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by GIOPPL on 2017/12/5.
 */
class SearchResult:FatherActivity() {
    var rv: RecyclerView? = null
    var mAdapt: ForumAdapt? = null
    private var mList= ArrayList<ForumBean>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.p2_biology)
        EventBus.getDefault().register(this)
        SetAdaptManager()
    }
    //set adapt
    private fun SetAdaptManager() {
        rv = findViewById(R.id.rv_biology) as RecyclerView
        val layoutManager = LinearLayoutManager(this)
        rv!!.layoutManager = layoutManager as RecyclerView.LayoutManager?
        rv!!.setHasFixedSize(true)
        layoutManager.orientation = OrientationHelper.VERTICAL
        mAdapt = ForumAdapt(mList!!, this)
        rv!!.adapter = mAdapt
    }
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    fun helloEventBus(eventBusList: ArrayList<ForumBean>) {
        for (i in eventBusList)
            mList.add(i)
    }

}