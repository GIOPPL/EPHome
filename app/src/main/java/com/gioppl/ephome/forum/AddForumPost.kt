package com.gioppl.ephome.forum

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import ch.ielse.view.SwitchView
import com.avos.avoscloud.AVException
import com.avos.avoscloud.AVFile
import com.avos.avoscloud.SaveCallback
import com.facebook.drawee.view.SimpleDraweeView
import com.gioppl.ephome.*
import com.zzti.fengyongge.imagepicker.PhotoSelectorActivity
import java.util.*

/**
 * Created by GIOPPL on 2017/10/7.
 */

class AddForumPost : FatherActivity() {
    private var ed_title: EditText? = null
    private var ed_content: EditText? = null
    private var im_picture: SimpleDraweeView? = null
    private var url:String? = null
    private var switchView:SwitchView?=null
    private var hide=false
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_forum_post)
        initView()
    }

    private fun initView() {
        switchView= findViewById(R.id.switchView) as SwitchView?
        im_picture = findViewById(R.id.my_image_view) as SimpleDraweeView
        ed_title = findViewById(R.id.ed_addForum_title) as EditText?
        ed_content = findViewById(R.id.ed_addForum_content) as EditText?
        switchView!!.setOnStateChangedListener(object : SwitchView.OnStateChangedListener {
            override fun toggleToOn(view: SwitchView) {
                view.toggleSwitch(true) // or false
                hide=true
                (findViewById(R.id.tv_addForum_isHide)as TextView).text="手机号隐藏"
            }

            override fun toggleToOff(view: SwitchView) {
                view.toggleSwitch(false) // or true
                hide=false
                (findViewById(R.id.tv_addForum_isHide)as TextView).text="手机号可见"
            }
        })
    }

    fun addPicture(view: View) {
        val intent = Intent(this@AddForumPost, PhotoSelectorActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        intent.putExtra("limit", 1)//number是选择图片的数量
        startActivityForResult(intent, 0)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            0 -> if (data != null) {
                val paths = data.extras.getSerializable("photos") as List<String>//path是选择拍照或者图片的地址数组
                FinalValue.successMessage(paths[0])
                url = paths[0]
                im_picture!!.setImageURI("file://" + url)
            }
            else -> {

            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    public fun confirmAdd(view: View) {
        if(FinalValue.LOAD_STA){
            FinalValue.successMessage(url!!)
            val file = AVFile.withAbsoluteLocalPath("GIOPPL.jpg", url)
            file.saveInBackground(object : SaveCallback() {
                override fun done(e: AVException?) {
                    val map = HashMap<String, Any>()
                    map.put("iphone",FinalValue.PHONE)
                    map.put("title",ed_title!!.text.toString())
                    map.put("content",ed_content!!.text.toString())
                    map.put("goods",file.url)
                    map.put("uname",FinalValue.USER_NAME)
                    map.put("address",FinalValue.ADDRESS)
                    map.put("uimage",FinalValue.HEAD_PHOTO_URL)
                    map.put("hide",hide)
                    map.put("uid",FinalValue.ID)
                    var base_url= SharedPreferencesUtils.getParam(this@AddForumPost,"base_url","错误url")as String
                    PostRequest(map, base_url+FinalValue.INTERFACE_ADDFORUM, PostRequest.POST, object : PostRequest.RequestCallback {
                        override fun success(back: String) {
                            Log.i("成功", back)
                        }

                        override fun error(back: String) {
                            Log.i("失败", back)
                        }

                        override fun getBeanList(bean: ArrayList<Any>) {

                        }
                    })
                    finish()
                    FinalValue.toast(this@AddForumPost,"发布成功")
                }
            })
        }else{
            FinalValue.toast(this@AddForumPost,"请先登陆")
        }
    }
}
