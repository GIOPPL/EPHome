package com.gioppl.ephome.sliding.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import com.gioppl.ephome.FinalValue
import com.gioppl.ephome.R

/**
 * Created by GIOPPL on 2017/11/23.
 */
class ConfirmCode:AppCompatActivity() {
    private var ed_pwd1:EditText?=null
    private var ed_pwd2:EditText?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.confirm_code)
        initView()
    }

    private fun initView() {
        ed_pwd1= findViewById(R.id.ed_confirm_pwd1) as EditText?
        ed_pwd2= findViewById(R.id.ed_confirm_pwd2) as EditText?
    }

    public fun ConfirmPassword(view:View){
        if (PwdConsistency()){
            Login.PASSWORD=ed_pwd1!!.text.toString()
            startActivity(Intent(this@ConfirmCode,Register::class.java))
        }else{
            FinalValue.toast(this@ConfirmCode,"密码不一致")
        }
    }
    private fun PwdConsistency()= ed_pwd1!!.text.toString() == ed_pwd2!!.text.toString()
}