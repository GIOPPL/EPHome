package com.gioppl.ephome.HomePager

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.gioppl.ephome.FatherActivity
import com.gioppl.ephome.HomePager.adapt.BiologyAdapt
import com.gioppl.ephome.PostRequest
import com.gioppl.ephome.R
import com.gioppl.ephome.ep.EpEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

/**
 * Created by GIOPPL on 2017/12/5.
 */
class News :FatherActivity(){
    var rv: RecyclerView? = null
    var mAdapt: BiologyAdapt? = null
    private var mList= ArrayList<BiologyEntity>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.p2_biology)
        SetAdaptManager()
        initData()
    }

    private fun initData() {
        val map = HashMap<String, Any>()
        map.put("from","1")
        map.put("to","20");
        PostRequest(map, "http://116.196.91.8:8080/webtest/ServletNewLimitTo", PostRequest.POST, object : PostRequest.RequestCallback {
            override fun success(back: String) {
                val list=formatJsonToEntity(back)
                for (i in list)
                    mList.add(BiologyEntity(i.id,i.title,i.content))
                mAdapt!!.notifyDataSetChanged()
            }

            override fun error(back: String) {
                Log.i("失败", back)
            }

            override fun getBeanList(bean: ArrayList<Any>) {

            }
        })
    }

    //set adapt
    private fun SetAdaptManager() {
        rv = findViewById(R.id.rv_biology) as RecyclerView
        val layoutManager = LinearLayoutManager(this)
        rv!!.layoutManager = layoutManager as RecyclerView.LayoutManager?
        rv!!.setHasFixedSize(true)
        layoutManager.orientation = OrientationHelper.VERTICAL
        mAdapt = BiologyAdapt(mList!!, this)
        rv!!.adapter = mAdapt
    }

    private fun formatJsonToEntity(json: String): ArrayList<EpEntity> {
        val list: ArrayList<EpEntity>
        val listType = object : TypeToken<List<EpEntity>>() {

        }.type
        val gson = Gson()
        list = gson.fromJson<ArrayList<EpEntity>>(json, listType)
        return list
    }
}