package com.gioppl.ephome.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import com.avos.avoscloud.AVException
import com.avos.avoscloud.AVUser
import com.avos.avoscloud.LogInCallback
import com.gioppl.ephome.EventBusMain
import com.gioppl.ephome.FinalValue
import com.gioppl.ephome.MainActivity
import com.gioppl.ephome.R
import com.gioppl.ephome.downPhoto.DownHeadPhoto
import org.greenrobot.eventbus.EventBus


/**
 * Created by GIOPPL on 2017/10/23.
 */
class Login:AppCompatActivity(){
    var ed_user:EditText?=null
    var ed_psw:EditText?=null
    var username:String?=null
    var psw:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        initView()
    }

    private fun initView() {
        ed_psw= findViewById(R.id.ed_login_psw) as EditText?
        ed_user= findViewById(R.id.ed_login_user) as EditText?
    }
    public fun signIn(v:View){
        if (judgeUserAndPsw()){
            AVUser.logInInBackground(username, psw, object : LogInCallback<AVUser>() {
                override fun done(avUser: AVUser?, e: AVException?) {
                    if (e == null) {
                        DownHeadPhoto(avUser!!.get("headPhoto").toString())
                        EventBus.getDefault().postSticky(EventBusMain(true,false));
                        FinalValue.LOAD_STA=true
                        FinalValue.USER_NAME=username!!
                        FinalValue.USER_PASSWORD=psw!!
                        FinalValue.HEAD_PHOTO_URL=avUser.getString("headPhoto")
                        FinalValue.ADMIN=avUser.getBoolean("admin");
                        this@Login.finish()
                        startActivity(Intent(this@Login, MainActivity::class.java))
                    } else {
                        EventBus.getDefault().postSticky(EventBusMain(false,false))
                        FinalValue.toast(this@Login,e.message!!)
                        FinalValue.LOAD_STA=false
                    }
                }
            })
        }
    }
    private fun judgeUserAndPsw():Boolean{
        if (ed_psw!=null&&ed_user!=null){
            username=ed_user!!.text.toString()
            psw=ed_psw!!.text.toString()
            if (username!!.isEmpty()||psw!!.isEmpty()){
                FinalValue.toast(this,"账户或者密码为空")
                return false
            }else{
                return true
            }
        }else{
            FinalValue.toast(this,"未知错误，建议重启软件")
            return false
        }
    }
}