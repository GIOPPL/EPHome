package com.gioppl.ephome

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.RadioGroup
import android.widget.RelativeLayout
import com.avos.avoscloud.AVException
import com.avos.avoscloud.AVFile
import com.avos.avoscloud.SaveCallback
import com.facebook.drawee.view.SimpleDraweeView
import com.gioppl.ephome.HomePager.HomePager
import com.gioppl.ephome.ep.Ep2
import com.gioppl.ephome.ep.SlidingDrawer
import com.gioppl.ephome.forum.Forum
import com.gioppl.ephome.policy.PolicyPager
import com.gioppl.ephome.sliding.userInfor.UpdateEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yarolegovich.slidingrootnav.SlideGravity
import com.yarolegovich.slidingrootnav.SlidingRootNav
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.HashMap
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    var relative: RelativeLayout?=null
    var count = 0
    var vp: BanSlidingViewPager? = null
    var mRadioGroup: RadioGroup? = null
    var mPagerList = ArrayList<Fragment>()
    var mSliding: SlidingRootNav? =null
    var login_status:LoginSuccess?=null

    val homePager=HomePager()
    val policyPager=PolicyPager()
    val forumPager=Forum()
    val epPager= Ep2()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initPager()
        initSlidingDrawer()
        SlidingDrawer(this)

    }

    private fun initSlidingDrawer() {
        mSliding= SlidingRootNavBuilder(this)
                .withMenuLayout(R.layout.sliding_drawer)
                .withMenuOpened(false) //Initial menu opened/closed state. Default == false
                .withMenuLocked(false) //If true, a user can't open or close the menu. Default == false.
                .withGravity(SlideGravity.LEFT) //If LEFT you can swipe a menu from left to right, if RIGHT - the direction is opposite.
                .inject();
    }


    private fun initView() {
        relative= findViewById(R.id.rea_main) as RelativeLayout?
        vp = findViewById(R.id.vp_main) as BanSlidingViewPager?
        val im_add = findViewById(R.id.im_top_add)
        val im_calculator = findViewById(R.id.im_top_calculator)
        val im_search=findViewById(R.id.im_top_search)
        mRadioGroup = findViewById(R.id.rg_main_bottom) as RadioGroup?
        mRadioGroup!!.check(0)
        mRadioGroup!!.setOnCheckedChangeListener {
            radioGroup, i ->
            when (i) {
                R.id.rbtn_main_one -> {
                    vp!!.setCurrentItem(0)
                    im_add.visibility=View.GONE
                    im_calculator.visibility=View.GONE
                    im_search.visibility=View.GONE
                }
                R.id.rbtn_main_two -> {
                    vp!!.setCurrentItem(1)
                    im_add.visibility=View.GONE
                    im_calculator.visibility=View.GONE
                    im_search.visibility=View.GONE
                }
                R.id.rbtn_main_three -> {
                    vp!!.setCurrentItem(2)
                    im_add.visibility=View.GONE
                    im_calculator.visibility=View.VISIBLE
                    im_search.visibility=View.GONE
                }
                R.id.rbtn_main_four -> {
                    vp!!.setCurrentItem(3)
                    im_add.visibility = View.VISIBLE
                    im_calculator.visibility=View.GONE
                    im_search.visibility=View.VISIBLE
                }
            }
        }
    }

    private fun initPager() {
//        mPagerList.add(HomePager())
//        mPagerList.add(PolicyPager())
//        mPagerList.add(Ep())
//        mPagerList.add(Forum())
        mPagerList.add(homePager)
        mPagerList.add(policyPager)
        mPagerList.add(epPager)
        mPagerList.add(forumPager)
        var pagerAdapt = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment = mPagerList.get(position)
            override fun getCount(): Int = mPagerList.size
        }
        vp!!.adapter = pagerAdapt
    }
    public interface LoginSuccess{
        fun success(status:Boolean)
    }


    var sim_head: SimpleDraweeView? = null
    private var url:String? = null
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            0 -> if (data != null) {
                val paths = data.extras.getSerializable("photos") as List<String>//path是选择拍照或者图片的地址数组
                FinalValue.successMessage(paths[0])
                url = paths[0]
                sim_head = findViewById(R.id.sim_sliding_head) as SimpleDraweeView?
                sim_head!!.setImageURI("file://" + url)
                updateHeadImage(paths[0])
            }
            else -> {

            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
    private fun updateHeadImage(path :String){
        if(FinalValue.LOAD_STA){
            FinalValue.successMessage(url!!)
            val file = AVFile.withAbsoluteLocalPath("GIOPPL.jpg", path)
            file.saveInBackground(object : SaveCallback() {
                override fun done(e: AVException?) {
                    val map = HashMap<String, Any>()
                    map.put("uname", FinalValue.USER_NAME)
                    map.put("address", FinalValue.ADDRESS)
                    map.put("iphone", FinalValue.PHONE)
                    map.put("email", FinalValue.MAIL)
                    map.put("upwd", FinalValue.USER_PASSWORD)
                    map.put("uid", FinalValue.ID)
                    map.put("uimage", file.url)
                    var base_url=SharedPreferencesUtils.getParam(this@MainActivity,"base_url","错误url")as String
                    PostRequest(map, base_url+FinalValue.INTERFACE_USER_UPDATE, PostRequest.POST, object : PostRequest.RequestCallback {
                        override fun success(back: String) {
                            val bean=formatBeanList(back)
                            if (bean.isState){
                                if (bean.uimage!=null)FinalValue.HEAD_PHOTO_URL=bean.uimage
                                FinalValue.USER_NAME=bean.uname
                                FinalValue.ADDRESS=bean.address
                                FinalValue.ID= bean.uid.toString()
                                FinalValue.PHONE=bean.iphone
                                FinalValue.MAIL=bean.email
                                FinalValue.LOAD_STA=true
                                FinalValue.USER_PASSWORD=bean.upwd
                                FinalValue.toast(this@MainActivity,"修改头像成功")
                                finish()
                            } else{
                                FinalValue.toast(this@MainActivity,"修改头像失败")
                            }
                        }

                        override fun error(back: String) {
                            FinalValue.toast(this@MainActivity,"修改头像失败，请检查网络连接")
                            Log.i("修改头像失败", back)
                        }

                        override fun getBeanList(bean: java.util.ArrayList<Any>) {

                        }
                    })
                }
            })
        }else{
            FinalValue.toast(this@MainActivity,"请先登陆")
        }
    }
    private fun formatBeanList(json: String): UpdateEntity {
        val listType = object : TypeToken<UpdateEntity>() {}.type
        val gson = Gson()
        val list = gson.fromJson<UpdateEntity>(json, listType)
        return list
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    fun helloEventBus(eventBus: EventBusMain) {
        FinalValue.successMessage("成功接收到登陆界面传递的数据." )

    }
}
