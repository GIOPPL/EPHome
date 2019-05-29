package com.gioppl.ephome.sliding.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import com.gioppl.ephome.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.greenrobot.eventbus.EventBus
import java.util.*


/**
 * Created by GIOPPL on 2017/10/23.
 */
class Login : AppCompatActivity() {
    var password="";
    var userId=""
    var ed_user: EditText? = null
    var ed_psw: EditText? = null
    var username: String? = null
    var psw: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        password=SharedPreferencesUtils.getParam(this,"password","") as String
        userId=SharedPreferencesUtils.getParam(this,"userId","") as String
        initView()
    }

    private fun initView() {
        ed_psw = findViewById(R.id.ed_login_psw) as EditText?
        ed_user = findViewById(R.id.ed_login_user) as EditText?
        ed_psw!!.setText(password)
        ed_user!!.setText(userId)
    }
    companion object {
        internal var PASSWORD=""
        internal var ADDRESS=""
        internal var PHONE_NUMBER=""
        internal var USER_NAME=""
        internal var MAIL=""
    }


    //登陆操作
    public fun signIn(v: View) {
        if (judgeUserAndPsw()) {
            val map = HashMap<String, Any>()
            map.put("iphone",ed_user!!.text.toString())
            map.put("upwd",ed_psw!!.text.toString())
            val url="${FinalValue.BASE_URL}/UserLogin"
            PostRequest(map,url , PostRequest.POST, object : PostRequest.RequestCallback {
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
                        FinalValue.USER_PASSWORD=ed_psw!!.text.toString()
                        FinalValue.ADMIN = bean.iphone.equals("17695564750")
                        SharedPreferencesUtils.setParam(this@Login,"userId",ed_user!!.text.toString())
                        SharedPreferencesUtils.setParam(this@Login,"password",ed_psw!!.text.toString())
                        EventBus.getDefault().postSticky(EventBusMain(true,true));
                        this@Login.startActivity(Intent(this@Login,com.gioppl.ephome.MainActivity::class.java))
                        finish()
                    } else{
                        FinalValue.toast(this@Login,"登陆失败，请检查账户密码")
                    }

                }

                override fun error(back: String) {
                    FinalValue.toast(this@Login,"失败$back")
                }

                override fun getBeanList(bean: ArrayList<Any>) {

                }
            })
        }
    }
    private fun judgeUserAndPsw(): Boolean {
        if (ed_psw != null && ed_user != null) {
            username = ed_user!!.text.toString()
            psw = ed_psw!!.text.toString()
            if (username!!.isEmpty() || psw!!.isEmpty()) {
                FinalValue.toast(this, "账户或者密码为空")
                return false
            } else {
                return true
            }
        } else {
            FinalValue.toast(this, "未知错误，建议重启软件")
            return false
        }
    }
    public fun ToRegister(v: View){
        startActivity(Intent(this@Login, SendMessage::class.java))
        finish()
    }

    private fun formatBeanList(json: String): LoginEntity {
        val listType = object : TypeToken<LoginEntity>() {}.type
        val gson = Gson()
        val list = gson.fromJson<LoginEntity>(json, listType)
        return list
    }
}