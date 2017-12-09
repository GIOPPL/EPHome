package com.gioppl.ephome.sliding.userInfor

import android.content.Intent
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
class UpdateUser : AppCompatActivity() {

    private var ed_address: EditText? = null
    private var ed_phone: EditText? = null
    private var ed_username: EditText? = null
    private var ed_mail: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.update_user_activity)
        initView()
        initDate()
    }

    private fun initDate() {
        ed_address!!.hint = FinalValue.ADDRESS
        ed_mail!!.hint = FinalValue.MAIL
        ed_phone!!.hint = FinalValue.PHONE
        ed_username!!.hint = FinalValue.USER_NAME
    }

    private fun initView() {
        ed_address = findViewById(R.id.ed_updateUser_address) as EditText?
        ed_phone = findViewById(R.id.ed_updateUser_phone) as EditText?
        ed_username = findViewById(R.id.ed_updateUser_name) as EditText?
        ed_mail = findViewById(R.id.ed_updateUser_mail) as EditText?
    }

    public fun UpdatePassword(view: View) {
        startActivity(Intent(this@UpdateUser, UpdatePassword::class.java))
        finish()
    }

    public fun UpdateOwnInfo(view: View) {
        val map = HashMap<String, Any>()

        if (!(ed_username!!.text.toString()==""))
            map.put("uname", ed_username!!.text.toString())
        else
            map.put("uname", FinalValue.USER_NAME)

        if (!(ed_address!!.text.toString()==""))
            map.put("address", ed_address!!.text.toString())
        else
            map.put("address", FinalValue.ADDRESS)


        if (!(ed_phone!!.text.toString()==""))
            map.put("iphone", ed_phone!!.text.toString())
        else
            map.put("iphone", FinalValue.PHONE)


        if (!(ed_mail!!.text.toString()==""))
            map.put("email", ed_mail!!.text.toString())
        else
            map.put("email", FinalValue.MAIL)


        map.put("upwd", FinalValue.USER_PASSWORD)
        map.put("uid", FinalValue.ID)
        map.put("uimage", FinalValue.HEAD_PHOTO_URL)

        PostRequest(map, "http://116.196.91.8:8080/webtest/ServletPPLuserupdate", PostRequest.POST, object : PostRequest.RequestCallback {
            override fun success(back: String) {
                Log.i("修改信息成功", back)
                val bean = formatBeanList(back)
                if (bean.isState) {
                    if (bean.uimage != null) FinalValue.HEAD_PHOTO_URL = bean.uimage
                    FinalValue.USER_NAME = bean.uname
                    FinalValue.ADDRESS = bean.address
                    FinalValue.ID = bean.uid.toString()
                    FinalValue.PHONE = bean.iphone
                    FinalValue.MAIL = bean.email
                    FinalValue.LOAD_STA = true
                    FinalValue.USER_PASSWORD = bean.upwd
                    FinalValue.toast(this@UpdateUser, "修改信息成功")
                    finish()
                } else {
                    FinalValue.toast(this@UpdateUser, "修改信息失败")
                }

            }

            override fun error(back: String) {
                FinalValue.toast(this@UpdateUser, "修改信息失败，请检查网络连接")
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

    public fun back(view: View) {
        finish()
    }
}