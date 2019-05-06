package com.gioppl.ephome.sliding.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import cn.smssdk.EventHandler
import cn.smssdk.SMSSDK
import com.gioppl.ephome.FinalJAVA
import com.gioppl.ephome.FinalValue
import com.gioppl.ephome.R
import com.gioppl.ephome.SharedPreferencesUtils
import com.mob.MobSDK

/**
 * Created by GIOPPL on 2017/11/23.
 */
class SendMessage :AppCompatActivity() {

    var im_phone:ImageView?=null
    var ed_phone:EditText?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.send_message)
        MobSDK.init(this@SendMessage, "1723697e0be06", "6574cd70260e9ad5d72e9b35e29210b6")
        initSDK()
        initView()

    }

    private fun initView() {
        im_phone= findViewById(R.id.im_phone) as ImageView?
        ed_phone= findViewById(R.id.ed_register_phoneNumber) as EditText?
        ed_phone!!.setOnClickListener(View.OnClickListener { im_phone!!.setImageResource(R.mipmap.phone_41adfa) })
    }

    public fun SendPhone(view: View){
        object : Thread() {
            override fun run() {
                try {//
                    SMSSDK.getVerificationCode("86", ed_phone!!.text.toString())
                    FinalValue.successMessage("短信发送成功")
                    Login.PHONE_NUMBER=ed_phone!!.text.toString()
                    SharedPreferencesUtils.setParam(this@SendMessage,FinalJAVA.SharePhone,ed_phone!!.text.toString())
                    startActivity(Intent(this@SendMessage, ReceiveMessage::class.java))
                    finish()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                finally {
                }
            }
        }.start()
    }

    private fun initSDK() {
        val eh = object : EventHandler() {
            override fun afterEvent(event: Int, result: Int, data: Any?) {

                val msg = Message()
                msg.arg1 = event
                msg.arg2 = result
                msg.obj = data
                handler.sendMessage(msg)
            }
        }
        SMSSDK.registerEventHandler(eh)

    }
    private var handler = object : Handler() {

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            val event = msg.arg1
            val result = msg.arg2
            val data = msg.obj
            Log.e("event", "event=" + event)
            if (result == SMSSDK.RESULT_COMPLETE) {
                //短信注册成功后，返回MainActivity,然后提示新好友
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//提交验证码成功
                    FinalValue.successMessage("提交验证码成功")
                    if (result == SMSSDK.RESULT_COMPLETE) {
                        Toast.makeText(applicationContext, "依然走短信验证", Toast.LENGTH_SHORT).show()
                    }

                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    Toast.makeText(applicationContext, "验证码已经发送", Toast.LENGTH_SHORT).show()
//                        textView2.setText("验证码已经发送")
                    FinalValue.successMessage("验证码已经发送")
                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {//返回支持发送验证码的国家列表
//                        Toast.makeText(applicationContext, "获取国家列表成功", Toast.LENGTH_SHORT).show()
//                        countryTextView.setText(data.toString())
                    println("+++" + applicationContext)
                } else if (event == SMSSDK.RESULT_ERROR) {
                    Toast.makeText(applicationContext, "------", Toast.LENGTH_SHORT).show()
                }
            } else {
                (data as Throwable).printStackTrace()
                Toast.makeText(applicationContext, "错误" + data, Toast.LENGTH_SHORT).show()
            }

        }

    }
}