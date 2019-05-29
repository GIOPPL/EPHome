package com.gioppl.ephome.admin

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import com.gioppl.ephome.FinalValue
import com.gioppl.ephome.PostRequest
import com.gioppl.ephome.R
import java.util.*

class AdminActivity : Activity() {
    var mRadioGroup: RadioGroup? = null
    private var et_title:EditText?=null
    private var et_content:EditText?=null
    private var status=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        initView()

    }

    private fun initView() {
        et_title= findViewById(R.id.et_title) as EditText?
        et_content= findViewById(R.id.et_content) as EditText?
        mRadioGroup = findViewById(R.id.rg_admin_bottom) as RadioGroup?
        findViewById(R.id.rbtn_admin_one).setBackgroundColor(Color.parseColor("#4041adfa"))
        mRadioGroup!!.check(R.id.rbtn_admin_one)
        mRadioGroup!!.setOnCheckedChangeListener {//LearnFragment
            radioGroup, i ->
            when (i) {
                R.id.rbtn_admin_one -> {
                    setBackGround()
                    findViewById(R.id.rbtn_admin_one).setBackgroundColor(Color.parseColor("#4041adfa"))
                    status=0
                }
                R.id.rbtn_admin_two -> {
                    setBackGround()
                    findViewById(R.id.rbtn_admin_two).setBackgroundColor(Color.parseColor("#4041adfa"))
                    status=1
                }
                R.id.rbtn_admin_three -> {
                    setBackGround()
                    findViewById(R.id.rbtn_admin_three).setBackgroundColor(Color.parseColor("#4041adfa"))
                    status=2
                }
                R.id.rbtn_admin_four -> {
                    setBackGround()
                    findViewById(R.id.rbtn_admin_four).setBackgroundColor(Color.parseColor("#4041adfa"))
                    status=3
                }
            }
        }
    }

    private fun setBackGround(){
        findViewById(R.id.rbtn_admin_one).setBackgroundColor(Color.parseColor("#40ffffff"))
        findViewById(R.id.rbtn_admin_two).setBackgroundColor(Color.parseColor("#40ffffff"))
        findViewById(R.id.rbtn_admin_three).setBackgroundColor(Color.parseColor("#40ffffff"))
        findViewById(R.id.rbtn_admin_four).setBackgroundColor(Color.parseColor("#40ffffff"))
    }



    public fun addToSQL(view:View){
        val title=et_title!!.text.toString()
        val content=et_title!!.text.toString()
        val map = HashMap<String, Any>()

        var url="${FinalValue.BASE_URL}/ServletNewADD"
        when(status){
            0->{//新闻
                url="${FinalValue.BASE_URL}/ServletNewADD"
                map.put("title",title)
                map.put("content",content)
            }
            1->{//政策
                url="${FinalValue.BASE_URL}/ServletZhengceInsert"
                map.put("ztitle",title)
                map.put("zcontent",content)
            }
            2->{//污染
                url="${FinalValue.BASE_URL}/ServletEnvInsert"
                map.put("environmental_type",title)
                map.put("content",content)
            }
            3->{//环保
                url="${FinalValue.BASE_URL}/ServletResourceInsert"
                map.put("title",title)
                map.put("xinxi",content)
            }
        }
        PostRequest(map,url , PostRequest.POST, object : PostRequest.RequestCallback {
            override fun success(back: String) {
                Toast.makeText(this@AdminActivity,"添加成功",Toast.LENGTH_SHORT).show()
            }

            override fun error(back: String) {
            }

            override fun getBeanList(bean: ArrayList<Any>) {

            }
        })
    }

}
