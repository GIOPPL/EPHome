package com.gioppl.ephome.ep

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.StrictMode
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.zhouwei.library.CustomPopWindow
import com.gioppl.ephome.FinalValue
import com.gioppl.ephome.HomePager.adapt.EpAdapt
import com.gioppl.ephome.PostRequest
import com.gioppl.ephome.R
import com.gioppl.ephome.SharedPreferencesUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

/**
 * Created by GIOPPL on 2017/10/8.
 */
class Ep : Fragment() {
    private var tv_pop: TextView? = null
    private var im_top: ImageView? = null
    private var lin_ep: LinearLayout? = null
    private var flag = false//false为看不见
    private var mCustomPopWindow: CustomPopWindow? = null
    private var ed_count: EditText? = null
    private var tv_money: TextView? = null
    private var sort=-1//种类，猪粪为0开始
    var rv: RecyclerView? = null
    var mAdapt: EpAdapt? = null
    private var mList= ArrayList<EpEntity>()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?)
            = inflater!!.inflate(R.layout.ep, container, false)!!

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        SetAdaptManager()
        initData()
    }

    private fun initData() {

        val map = HashMap<String, Any>()
        var base_url= SharedPreferencesUtils.getParam(activity,"base_url","错误url")as String
        PostRequest(map, base_url+FinalValue.INTERFACE_EP, PostRequest.POST, object : PostRequest.RequestCallback {
            override fun success(back: String) {
                mList.clear()
                val list=formatJsonToEntity(back)
                for (i in list)
                    mList.add(i)
                mAdapt!!.notifyDataSetChanged()
            }

            override fun error(back: String) {
                Log.i("失败", back)
            }

            override fun getBeanList(bean: ArrayList<Any>) {

            }
        })
    }

    private fun formatJsonToEntity(json: String): ArrayList<EpEntity> {
        val list: ArrayList<EpEntity>
        val listType = object : TypeToken<List<EpEntity>>() {

        }.type
        val gson = Gson()
        list = gson.fromJson<ArrayList<EpEntity>>(json, listType)
        return list
    }


    //set adapt
    private fun SetAdaptManager() {
        rv = activity.findViewById(R.id.rv_ep) as RecyclerView
        val layoutManager = LinearLayoutManager(activity)
        rv!!.layoutManager = layoutManager as RecyclerView.LayoutManager?
        rv!!.setHasFixedSize(true)
        layoutManager.orientation = OrientationHelper.VERTICAL
        mAdapt = EpAdapt(mList, activity)
        rv!!.adapter = mAdapt
    }
    private fun initView() {
        tv_pop = activity.findViewById(R.id.tv_epCal_pop) as TextView?
        tv_pop!!.setOnClickListener(View.OnClickListener { showPopMenu() })
        im_top = activity.findViewById(R.id.im_top_calculator) as ImageView?
        lin_ep = activity.findViewById(R.id.lin_ep) as LinearLayout?
        im_top!!.setOnClickListener(View.OnClickListener {
            if (flag) {
                lin_ep!!.visibility = View.GONE
                flag = false
            } else {
                lin_ep!!.visibility = View.VISIBLE
                flag = true
            }

        })

        tv_money = activity.findViewById(R.id.tv_epCal_money) as TextView?
        ed_count = activity.findViewById(R.id.ed_epCal_count) as EditText?

        tv_money!!.setOnClickListener(View.OnClickListener {
            var count:Int=Integer.parseInt(ed_count!!.text.toString())
            if (sort!=-1){
                tv_money!!.text=("￥"+count * FinalValue.goodsPriseList[sort].price )
            }
        })

    }



    @Throws(IOException::class)
    fun drawableFromUrl(url: String): Drawable {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        val x: Bitmap
        val connection = URL(url).openConnection() as HttpURLConnection
        connection.connect()
        val input = connection.inputStream
        x = BitmapFactory.decodeStream(input)
        return BitmapDrawable(x)
    }


    private fun showPopMenu() {
        val contentView = LayoutInflater.from(activity).inflate(R.layout.pop_menu, null)
        (contentView.findViewById(R.id.tv_menu_1) as TextView ).text=FinalValue.goodsPriseList[0].gname
        (contentView.findViewById(R.id.tv_menu_2) as TextView ).text=FinalValue.goodsPriseList[1].gname
        (contentView.findViewById(R.id.tv_menu_3) as TextView ).text=FinalValue.goodsPriseList[2].gname
        (contentView.findViewById(R.id.tv_menu_4) as TextView ).text=FinalValue.goodsPriseList[3].gname
        (contentView.findViewById(R.id.tv_menu_5) as TextView ).text=FinalValue.goodsPriseList[4].gname

        //处理popWindow 显示内容
        handleLogic(contentView)
        //创建并显示popWindow
        mCustomPopWindow = CustomPopWindow.PopupWindowBuilder(activity)
                .setView(contentView)
                .create()
                .showAsDropDown(tv_pop, 0, 20)
    }

    /**
     * 处理弹出显示内容、点击事件等逻辑
     * @param contentView
     */
    private fun handleLogic(contentView: View) {
        val listener = View.OnClickListener { v ->
            if (mCustomPopWindow != null) {
                mCustomPopWindow!!.dissmiss()
            }
            var showContent = ""
            when (v.id) {
                R.id.menu1 -> {
                    showContent = FinalValue.goodsPriseList[0].gname
                    tv_pop!!.text=showContent
                    sort=0
                }
                R.id.menu2 -> {
                    showContent = FinalValue.goodsPriseList[1].gname
                    tv_pop!!.text=showContent
                    sort=1
                }
                R.id.menu3 -> {
                    showContent = FinalValue.goodsPriseList[2].gname
                    tv_pop!!.text=showContent
                    sort=2
                }
                R.id.menu4 -> {
                    showContent =FinalValue.goodsPriseList[3].gname
                    tv_pop!!.text=showContent
                    sort=3
                }
                R.id.menu5 -> {
                    showContent =FinalValue.goodsPriseList[4].gname
                    tv_pop!!.text=showContent
                    sort=4
                }
            }
//            Toast.makeText(activity, showContent, Toast.LENGTH_SHORT).show()
        }
        contentView.findViewById(R.id.menu1).setOnClickListener(listener)
        contentView.findViewById(R.id.menu2).setOnClickListener(listener)
        contentView.findViewById(R.id.menu3).setOnClickListener(listener)
        contentView.findViewById(R.id.menu4).setOnClickListener(listener)
        contentView.findViewById(R.id.menu5).setOnClickListener(listener)
    }

}

