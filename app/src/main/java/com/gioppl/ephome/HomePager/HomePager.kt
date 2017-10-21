package com.gioppl.ephome.HomePager

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.gioppl.ephome.R
import ezy.ui.view.BannerView
import org.greenrobot.eventbus.EventBus
import java.util.*


/**
 * Created by GIOPPL on 2017/9/23.
 */
class HomePager : Fragment(),View.OnClickListener {
    private var lin_p1:LinearLayout?=null
    private var lin_p2:LinearLayout?=null
    private var lin_p3:LinearLayout?=null
    private var lin_p4:LinearLayout?=null
    private var lin_p5:LinearLayout?=null
    private var lin_p6:LinearLayout?=null


    //轮播图
    var banner: BannerView<Any>? = null
    var titles = arrayOf("光伏电网最新公告1", "光伏电网最新公告2", "光伏电网最新公告3", "光伏电网最新公告4", "光伏电网最新公告5", "光伏电网最新公告6")
    val list = ArrayList<BannerItem>()
    val urls = arrayOf(//750x500
            "http://ac-qzlvbisn.clouddn.com/0eaa95f4de78c97d7a7b.png",
            "http://ac-qzlvbisn.clouddn.com/b3fb0ea7a8be97bff7c3.png",
            "http://ac-qzlvbisn.clouddn.com/b1cabdeeb1791a498fb8.png",
            "http://ac-qzlvbisn.clouddn.com/299d4a5bbf216ddc295e.png"
    )

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?)
            = inflater!!.inflate(R.layout.home, container, false)!!

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        initRollImage()
    }

    private fun initView() {
        lin_p1= activity.findViewById(R.id.lin_home_p1) as LinearLayout?
        lin_p2= activity.findViewById(R.id.lin_home_p2) as LinearLayout?
        lin_p3= activity.findViewById(R.id.lin_home_p3) as LinearLayout?
        lin_p4= activity.findViewById(R.id.lin_home_p4) as LinearLayout?
        lin_p5= activity.findViewById(R.id.lin_home_p5) as LinearLayout?
        lin_p6= activity.findViewById(R.id.lin_home_p6) as LinearLayout?

        lin_p1!!.setOnClickListener(this)
        lin_p2!!.setOnClickListener(this)
        lin_p3!!.setOnClickListener(this)
        lin_p4!!.setOnClickListener(this)
        lin_p5!!.setOnClickListener(this)
        lin_p6!!.setOnClickListener(this)

    }

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
    init {
        for (i in urls.indices) {
            val item = BannerItem()
            item.image = urls[i]
            item.title = titles[i]

            list.add(item)
        }
    }
    class BannerItem {
        var image: String? = null
        var title: String? = null

        override fun toString(): String {
            return title!!
        }
    }
    override fun onClick(v: View) {
        when(v.id){
            R.id.lin_home_p1->{
                EventBus.getDefault().postSticky(HomePointModel(R.layout.p1_achievement,R.raw.mk_home_1));
                activity.startActivity(Intent(context, HomePointFather::class.java))
            }
            R.id.lin_home_p2->{
                EventBus.getDefault().postSticky(HomePointModel(R.layout.p2_eviroment_protection,R.raw.mk_home_2));
                activity.startActivity(Intent(context, HomePointFather::class.java))
            }
            R.id.lin_home_p3->{
                EventBus.getDefault().postSticky(HomePointModel(R.layout.p3_mode,R.raw.mk_home_3));
                activity.startActivity(Intent(context, HomePointFather::class.java))
            }
            R.id.lin_home_p4->{
                EventBus.getDefault().postSticky(HomePointModel(R.layout.p4_circulation,R.raw.mk_home_4));
                activity.startActivity(Intent(context, HomePointFather::class.java))
            }
            R.id.lin_home_p5->{
                EventBus.getDefault().postSticky(HomePointModel(R.layout.p5_pullution,R.raw.mk_home_5));
                activity.startActivity(Intent(context, HomePointFather::class.java))
            }
            R.id.lin_home_p6->{
                EventBus.getDefault().postSticky(HomePointModel(R.layout.p6_zoology,R.raw.mk_home_6));
                activity.startActivity(Intent(context, HomePointFather::class.java))
            }

        }
    }
}