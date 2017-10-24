package com.gioppl.ephome.forum

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import com.avos.avoscloud.AVException
import com.avos.avoscloud.AVFile
import com.avos.avoscloud.AVObject
import com.avos.avoscloud.SaveCallback
import com.facebook.drawee.view.SimpleDraweeView
import com.gioppl.ephome.FinalValue
import com.gioppl.ephome.R
import com.zzti.fengyongge.imagepicker.PhotoSelectorActivity


/**
 * Created by GIOPPL on 2017/10/7.
 */

class AddForumPost : AppCompatActivity() {
    private var ed_title: EditText? = null
    private var ed_content: EditText? = null
    private var im_picture: SimpleDraweeView? = null
    private var url:String? = null
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_forum_post)
        initView()
    }

    private fun initView() {
        im_picture = findViewById(R.id.my_image_view) as SimpleDraweeView
        ed_title = findViewById(R.id.ed_addForum_title) as EditText?
        ed_content = findViewById(R.id.ed_addForum_content) as EditText?
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
                //处理代码
                FinalValue().successMessage(paths[0]);
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
                    val todoFolder = AVObject("Forum")// 构建对象
                    todoFolder.put("title", ed_title!!.text.toString())// 设置名称
                    todoFolder.put("content", ed_content!!.text.toString())// 设置内容
                    todoFolder.put("person", FinalValue.USER_NAME)// 设置人名
                    todoFolder.put("add", FinalValue.ADDRESS)// 设置地址
                    todoFolder.put("url", file.url)// 设置照片
                    todoFolder.saveInBackground()// 保存到服务端
                    finish()
                    FinalValue.toast(this@AddForumPost,"发布成功")
                }
            })
        }else{
            FinalValue.toast(this@AddForumPost,"请先登陆")
        }
    }
}
