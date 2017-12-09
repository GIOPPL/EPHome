package com.gioppl.ephome.sliding.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.EditText
import com.gioppl.ephome.FinalValue
import com.gioppl.ephome.PostRequest
import com.gioppl.ephome.R
import java.util.*

/**
 * Created by GIOPPL on 2017/11/28.
 */
class Register :AppCompatActivity(){
    private var ed_name: EditText?=null
    private var ed_address: EditText?=null
    private var ed_mail: EditText?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)
        initView()
    }

    private fun initView() {
        ed_name= findViewById(R.id.ed_register_name) as EditText?
        ed_address= findViewById(R.id.ed_register_address) as EditText?
        ed_mail= findViewById(R.id.ed_register_mail) as EditText?
    }

    public fun ConfirmRegister(view: View){
        val  name=ed_name!!.text.toString()
        val address=ed_address!!.text.toString()
        val mail=ed_mail!!.text.toString()

        Login.USER_NAME=name;
        Login.ADDRESS=address
        Login.MAIL=mail

        val map = HashMap<String, Any>()
        map.put("uname", Login.USER_NAME)
        map.put("upwd", Login.PASSWORD)
        map.put("iphone", Login.PHONE_NUMBER)
        map.put("email", Login.MAIL)
        map.put("address", Login.ADDRESS)
        PostRequest(map, FinalValue.INTERFACE_REGISTER, PostRequest.POST, object : PostRequest.RequestCallback {

            override fun success(back: String) {
                Log.i("注册成功", back)
                FinalValue.toast(this@Register,"注册成功")
                FinalValue.LOAD_STA=true
                finish()
            }

            override fun error(back: String) {
                Log.i("注册失败", back)
                FinalValue.toast(this@Register,"注册失败")
            }

            override fun getBeanList(bean: ArrayList<Any>) {

            }
        })
    }
}