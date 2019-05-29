package com.gioppl.ephome.HomePager

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.gioppl.ephome.*
import com.gioppl.ephome.HomePager.adapt.BiologyAdapt
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

/**
 * Created by GIOPPL on 2017/12/4.
 */

class Biology : FatherActivity() {
    var rv: RecyclerView? = null
    var mAdapt: BiologyAdapt? = null
    private var mList=ArrayList<BiologyEntity>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.p2_biology)
        SetAdaptManager()
        initData()
    }

    private fun initData() {
        val map = HashMap<String, Any>()
        map.put("from","1")
        map.put("to","100");
        var base_url= SharedPreferencesUtils.getParam(this@Biology,"base_url","错误url")as String
        PostRequest(map, base_url+FinalValue.INTERFACE_ServletResourceLimitTo, PostRequest.POST, object : PostRequest.RequestCallback {
            override fun success(back: String) {
                val list=formatJsonToEntity(back)
                mList.clear()
                for (i in list)
                    mList.add(0,i)
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

    private fun formatJsonToEntity(json: String): ArrayList<BiologyEntity> {
        val list: ArrayList<BiologyEntity>
        val listType = object : TypeToken<List<BiologyEntity>>() {

        }.type
        val gson = Gson()
        list = gson.fromJson<ArrayList<BiologyEntity>>(json, listType)
        return list
    }
}

