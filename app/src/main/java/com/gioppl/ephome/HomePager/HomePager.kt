package com.gioppl.ephome.HomePager

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.gioppl.ephome.FinalValue
import com.gioppl.ephome.HomePager.entity.PollutionEntity
import com.gioppl.ephome.PostRequest
import com.gioppl.ephome.R
import com.gioppl.ephome.SharedPreferencesUtils
import com.gioppl.ephome.policy.PollutionDetail
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ezy.ui.view.BannerView
import org.greenrobot.eventbus.EventBus
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by GIOPPL on 2017/9/23.
 */
class HomePager : Fragment(), View.OnClickListener {
    private var lin_p1: LinearLayout? = null
    private var lin_p2: LinearLayout? = null
    private var lin_p3: LinearLayout? = null
    private var lin_p4: LinearLayout? = null
    private var lin_p5: LinearLayout? = null
    private var lin_p6: LinearLayout? = null

    //两条新闻
    private var tv_new1_title: TextView? = null
    private var tv_new1_date: TextView? = null
    private var tv_new2_title: TextView? = null
    private var tv_new2_date: TextView? = null
    private var mList = ArrayList<NewEntity>()


    private var picture1="http://lc-rxsnxjjw.cn-n1.lcfile.com/bd4559f82af6eb83fcd5/a1.png"
    private var picture2="http://lc-rxsnxjjw.cn-n1.lcfile.com/6727443bf6654d1bbe33/a3.png"
    private var picture3="http://lc-rxsnxjjw.cn-n1.lcfile.com/eb24b1855eb68ba8c032/a2.png"
    private var picture4="http://lc-rxsnxjjw.cn-n1.lcfile.com/8db96d94964795c29b2c/a4.png"


    //轮播图
    var banner: BannerView<Any>? = null
    var titles = arrayOf("公告1", "公告2", "公告3", "公告4", "公告5", "公告6")
    val list = ArrayList<BannerItem>()
    var urls= arrayOf(//750x500//
            picture1,
            picture2,
            picture3,
            picture4
    )

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?)
            = inflater!!.inflate(R.layout.home, container, false)!!

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        picture1=SharedPreferencesUtils.getParam(activity,"picture1","") as String
        picture2=SharedPreferencesUtils.getParam(activity,"picture2","") as String
        picture3=SharedPreferencesUtils.getParam(activity,"picture3","") as String
        picture4=SharedPreferencesUtils.getParam(activity,"picture4","") as String
        urls= arrayOf(//750x500//
                picture1,
                picture2,
                picture3,
                picture4
        )
        for (i in urls.indices) {
            val item = BannerItem()
            item.image = urls[i]
            item.title = titles[i]

            list.add(item)
        }
        initView()
        initRollImage()
        newData()
    }

    private fun initView() {
        lin_p1 = activity.findViewById(R.id.lin_home_p1) as LinearLayout?
        lin_p2 = activity.findViewById(R.id.lin_home_p2) as LinearLayout?
        lin_p3 = activity.findViewById(R.id.lin_home_p3) as LinearLayout?
        lin_p4 = activity.findViewById(R.id.lin_home_p4) as LinearLayout?
        lin_p5 = activity.findViewById(R.id.lin_home_p5) as LinearLayout?
        lin_p6 = activity.findViewById(R.id.lin_home_p6) as LinearLayout?

        lin_p1!!.setOnClickListener(this)
        lin_p2!!.setOnClickListener(this)
        lin_p3!!.setOnClickListener(this)
        lin_p4!!.setOnClickListener(this)
        lin_p5!!.setOnClickListener(this)
        lin_p6!!.setOnClickListener(this)

        activity.findViewById(R.id.lin_new1).setOnClickListener(View.OnClickListener {
            if (mList.size == 2) {
                EventBus.getDefault().postSticky(PollutionEntity(mList[0].title, mList[0].content));
                context.startActivity(Intent(context, PollutionDetail::class.java))
            }
        })
        activity.findViewById(R.id.lin_new2).setOnClickListener(View.OnClickListener {
            if (mList.size == 2) {
                EventBus.getDefault().postSticky(PollutionEntity(mList[1].title, mList[1].content));
                context.startActivity(Intent(context, PollutionDetail::class.java))
            }
        })
        tv_new1_title = activity.findViewById(R.id.tv_new1_title) as TextView?
        tv_new1_date = activity.findViewById(R.id.tv_new1_date) as TextView?
        tv_new2_title = activity.findViewById(R.id.tv_new2_title) as TextView?
        tv_new2_date = activity.findViewById(R.id.tv_new2_date) as TextView?

    }

    //
    private fun initRollImage() {

        banner = activity.findViewById(R.id.banner) as BannerView<Any>
        banner!!.setViewFactory(BannerViewFactory())
        banner!!.setDataList(list as List<Any>)
        banner!!.start()
    }

    class BannerViewFactory : BannerView.ViewFactory<BannerItem> {
        override fun create(item: BannerItem, position: Int, container: ViewGroup): View {
            val iv = ImageView(container.context)
            val options = RequestOptions().diskCacheStrategy(DiskCacheStrategy.DATA)
            Glide.with(container.context.applicationContext).load(item.image).apply(options).into(iv)
            return iv
        }
    }

//    init {
//        for (i in urls.indices) {
//            val item = BannerItem()
//            item.image = urls[i]
//            item.title = titles[i]
//
//            list.add(item)
//        }
//    }

    class BannerItem {
        var image: String? = null
        var title: String? = null

        override fun toString(): String {
            return title!!
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.lin_home_p1 -> {
                EventBus.getDefault().postSticky(HomePointModel(R.layout.p1_achievement, R.raw.mk_home_1));
                activity.startActivity(Intent(context, HomePointFather::class.java))
            }
            R.id.lin_home_p2 -> {
                EventBus.getDefault().postSticky(HomePointModel(R.layout.p2_biology, R.raw.mk_home_2));
                activity.startActivity(Intent(context, Biology::class.java))
            }
            R.id.lin_home_p3 -> {
                EventBus.getDefault().postSticky(HomePointModel(R.layout.p3_mode, R.raw.mk_home_3));
                activity.startActivity(Intent(context, HomePointFather::class.java))
            }
            R.id.lin_home_p4 -> {
                EventBus.getDefault().postSticky(HomePointModel(R.layout.p4_circulation, R.raw.mk_home_4));
                activity.startActivity(Intent(context, HomePointFather::class.java))
            }
            R.id.lin_home_p5 -> {
//                EventBus.getDefault().postSticky(HomePointModel(R.layout.p5_pullution,R.raw.mk_home_5));
                activity.startActivity(Intent(context, Pollution::class.java))
            }
            R.id.lin_home_p6 -> {
                EventBus.getDefault().postSticky(HomePointModel(R.layout.p6_zoology, R.raw.mk_home_6));
                activity.startActivity(Intent(context, Circle::class.java))
            }

        }
    }

    private fun newData() {
        val map = HashMap<String, Any>()
        map.put("from", "1")
        map.put("to", "2");
        var base_url= SharedPreferencesUtils.getParam(activity,"base_url","错误url")as String
        PostRequest(map, base_url+FinalValue.INTERFACE_ServletNewLimitTo, PostRequest.POST, object : PostRequest.RequestCallback {
            override fun success(back: String) {
                Log.i("获取首页两条成功", back)

                val list = formatJsonToEntity(back)
                val y: Int
                val m: Int
                val d: Int
                val cal = Calendar.getInstance()
                y = cal.get(Calendar.YEAR)
                m = cal.get(Calendar.MONTH)
                d = cal.get(Calendar.DATE)
                val date = "$y-$m-$d"
                if (list.size == 2) {
                    tv_new1_title!!.text = list[0].title
                    tv_new1_date!!.text = date
                    tv_new1_title!!.text = list[1].title
                    tv_new1_date!!.text = date
                }
                for (i in list)
                    mList.add(i)
            }

            override fun error(back: String) {
                Log.i("失败", back)
            }

            override fun getBeanList(bean: ArrayList<Any>) {

            }
        })
    }

    private fun formatJsonToEntity(json: String): ArrayList<NewEntity> {
        val list: ArrayList<NewEntity>
        val listType = object : TypeToken<List<NewEntity>>() {

        }.type
        val gson = Gson()
        list = gson.fromJson<ArrayList<NewEntity>>(json, listType)
        return list
    }
}