package com.gioppl.ephome

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.RadioGroup
import android.widget.RelativeLayout
import com.avos.avoscloud.AVException
import com.avos.avoscloud.AVObject
import com.avos.avoscloud.SaveCallback
import com.gioppl.ephome.HomePager.HomePager
import com.gioppl.ephome.ep.Ep
import com.gioppl.ephome.forum.Forum
import com.gioppl.ephome.policy.PolicyPager


class MainActivity : AppCompatActivity() {
    var relative: RelativeLayout?=null
    var count = 0
    var vp: BanSlidingViewPager? = null
    var mRadioGroup: RadioGroup? = null
    var mPagerList = ArrayList<Fragment>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initPager()
//        for (i in 0..20){
//            store()
//        }
    }

    private fun store() {
        val todo = AVObject("Forum")
        todo.put("title", "test title " + count)
        todo.put("content", "test content " + count)
        todo.put("add", "天津")
        todo.put("person", "PPL")
        todo.put("url", "http://ac-rxsnxjjw.clouddn.com/9c2dc624a05360229896.jpg")
        todo.saveInBackground(object : SaveCallback() {
            override fun done(e: AVException?) {
                if (e == null) {
                    FinalValue().toast(this@MainActivity, "success" + count)
                } else {
                    // 失败的话，请检查网络环境以及 SDK 配置是否正确
                }
            }
        })
        count++
    }

    private fun initView() {
        relative= findViewById(R.id.rea_main) as RelativeLayout?
        vp = findViewById(R.id.vp_main) as BanSlidingViewPager?
        var im_add = findViewById(R.id.im_top_add)
        var im_calculator = findViewById(R.id.im_top_calculator)
        mRadioGroup = findViewById(R.id.rg_main_bottom) as RadioGroup?
        mRadioGroup!!.check(0)
        mRadioGroup!!.setOnCheckedChangeListener {
            radioGroup, i ->
            when (i) {
                R.id.rbtn_main_one -> {
                    vp!!.setCurrentItem(0)
                    im_add.visibility=View.GONE
                    im_calculator.visibility=View.GONE
                }
                R.id.rbtn_main_two -> {
                    vp!!.setCurrentItem(1)
                    im_add.visibility=View.GONE
                    im_calculator.visibility=View.GONE
                }
                R.id.rbtn_main_three -> {
                    vp!!.setCurrentItem(2)
                    im_add.visibility=View.GONE
                    im_calculator.visibility=View.VISIBLE
                }
                R.id.rbtn_main_four -> {
                    vp!!.setCurrentItem(3)
                    im_add.visibility = View.VISIBLE
                    im_calculator.visibility=View.GONE
                }
            }
        }
    }

    private fun initPager() {
        mPagerList.add(HomePager())
        mPagerList.add(PolicyPager())
        mPagerList.add(Ep())
        mPagerList.add(Forum())
        var pagerAdapt = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment = mPagerList.get(position)
            override fun getCount(): Int = mPagerList.size
        }
        vp!!.adapter = pagerAdapt
    }
}
