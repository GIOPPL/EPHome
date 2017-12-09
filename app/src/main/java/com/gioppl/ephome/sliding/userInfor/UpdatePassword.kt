package com.gioppl.ephome.sliding.userInfor

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.EditText
import com.gioppl.ephome.FinalValue
import com.gioppl.ephome.PostRequest
import com.gioppl.ephome.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

/**
 * Created by GIOPPL on 2017/12/3.
 */
class UpdatePassword : AppCompatActivity() {
    private var ed_old: EditText? = null
    private var ed_new: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.update_pwd_activity)
        initView()
    }

    private fun initView() {
        ed_old = findViewById(R.id.ed_updatePwd_old) as EditText?
        ed_new = findViewById(R.id.ed_updatePwd_new) as EditText?
    }

    public fun back(view: View) {
        finish()
    }

    public fun update(view: View) {
        if (ed_old!!.text.toString() != FinalValue.USER_PASSWORD) {
            FinalValue.toast(this@UpdatePassword, "原密码输入错误")
            return
        }
        if (ed_new!!.text.toString() == "") FinalValue.toast(this@UpdatePassword, "请填写密码")
        val map = HashMap<String, Any>()
        map.put("uname", FinalValue.USER_NAME)
        map.put("address", FinalValue.ADDRESS)
        map.put("iphone", FinalValue.PHONE)
        map.put("email", FinalValue.MAIL)
        map.put("upwd", ed_new!!.text.toString())
        map.put("uid", FinalValue.ID)
        map.put("uimage", FinalValue.HEAD_PHOTO_URL)
        PostRequest(map, "http://116.196.91.8:8080/webtest/ServletPPLuserupdate", PostRequest.POST, object : PostRequest.RequestCallback {
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
                    FinalValue.toast(this@UpdatePassword,"修改密码成功")
                    finish()
                } else{
                    FinalValue.toast(this@UpdatePassword,"修改密码失败")
                }
            }

            override fun error(back: String) {
                FinalValue.toast(this@UpdatePassword,"修改密码失败，请检查网络连接")
                Log.i("修改密码失败", back)
            }

            override fun getBeanList(bean: ArrayList<Any>) {

            }
        })
    }
    private fun formatBeanList(json: String): UpdateEntity {
        val listType = object : TypeToken<UpdateEntity>() {}.type
        val gson = Gson()
        val list = gson.fromJson<UpdateEntity>(json, listType)
        return list
    }
}