package com.gioppl.ephome

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import com.gioppl.ephome.ep.GoodsPriceEntity
import com.gioppl.ephome.forum.ForumBean
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

/**
 * Created by GIOPPL on 2017/10/23.
 */
class AppStart : AppCompatActivity() {
    var v: View? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        v = View.inflate(this, R.layout.app_start, null)
        setContentView(v)
        initPager()
        initDate()
    }

    //获取不变更数据
    private fun initDate() {
        GoodsPrice()
    }

    private fun GoodsPrice() {
        val map = HashMap<String, Any>()
        PostRequest(map,"http://116.196.91.8:8080/webtest/ServletCountGet", PostRequest.POST, object : PostRequest.RequestCallback {
            override fun success(back: String) {
                for (i in formatGoodsList(back))
                    FinalValue.goodsPriseList.add(i)
                for (i in FinalValue.goodsPriseList)
                    FinalValue.successMessage(i.gname+","+i.price)
            }

            override fun error(back: String) {
                Log.i("失败", back)
            }

            override fun getBeanList(bean: ArrayList<Any>) {

            }
        })
    }

    private fun formatGoodsList(json: String): ArrayList<GoodsPriceEntity> {
        val list: ArrayList<GoodsPriceEntity>
        val listType = object : TypeToken<List<GoodsPriceEntity>>() {

        }.type
        val gson = Gson()
        list = gson.fromJson<ArrayList<GoodsPriceEntity>>(json, listType)
        return list
    }


    private fun initPager() {
        var ani = AlphaAnimation(0.9f, 1.0f)
        ani.duration = 1000
        v!!.startAnimation(ani)
        ani.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                startApp()
            }

            override fun onAnimationStart(animation: Animation?) {
            }

        })
    }

    private fun startApp() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
    private fun forumCount(){
        val map = HashMap<String, Any>()
        map.put("from","0")
        map.put("to","1")
        PostRequest(map, "http://116.196.91.8:8080/webtest/ServletDxlFindAll", PostRequest.POST, object : PostRequest.RequestCallback {
            override fun getBeanList(bean: ArrayList<Any>?) {
                //
            }

            override fun success(back: String) {
                val list=formatForumBean(back)
                Log.i("成功获取forum的数量",list[list.size-1].count.toString())
                FinalValue.FORUM_COUNT=list[list.size-1].count
            }

            override fun error(back: String) {
                Log.i("失败", back)
            }


        })
    }
    private fun formatForumBean(json: String): java.util.ArrayList<ForumBean> {
        val list: java.util.ArrayList<ForumBean>
        val listType = object : TypeToken<List<ForumBean>>() {}.type
        val gson = Gson()
        list = gson.fromJson<java.util.ArrayList<ForumBean>>(json, listType)
        return list
    }
}