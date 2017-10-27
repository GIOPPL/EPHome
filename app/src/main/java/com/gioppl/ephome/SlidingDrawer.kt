package com.gioppl.ephome.ep

import android.content.Intent
import android.os.Environment
import android.support.v4.app.FragmentActivity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.facebook.drawee.view.SimpleDraweeView
import com.gioppl.ephome.EventBusMain
import com.gioppl.ephome.FinalValue
import com.gioppl.ephome.R
import com.gioppl.ephome.login.Login
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


/**
 * Created by GIOPPL on 2017/8/18.
 */
class SlidingDrawer(private var activity: FragmentActivity) : View.OnClickListener {


    var tv_login: TextView? = null
    var fim_head: SimpleDraweeView? = null
    var lin_update: LinearLayout? = null
    var lin_login: LinearLayout? = null
    var tv_admin: TextView? = null

    init {
        initView()
        EventBus.getDefault().register(this)
    }

    private fun initView() {
        tv_admin = activity.findViewById(R.id.tv_sliding_admin) as TextView?
        tv_login = activity.findViewById(R.id.tv_sliding_login) as TextView?
        lin_login = activity.findViewById(R.id.lin_sliding_login) as LinearLayout?
        fim_head = activity.findViewById(R.id.fim_sliding_head) as SimpleDraweeView?
        fim_head!!.setImageURI("res://drawable/" + R.drawable.ic_launcher)
        lin_login!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.lin_sliding_login -> {
                activity.startActivity(Intent(activity, Login::class.java))
            }
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    fun helloEventBus(eventBus: EventBusMain) {
        val localPath = "file:" + Environment.getExternalStorageDirectory().absolutePath + "/GIOPPL/EPHome/my/" + FinalValue.HEAD_PHOTO_ADD + ".png"
        FinalValue.successMessage("成功接收到登陆界面传递的数据." + localPath)
        if (eventBus.login_status) {
            tv_login!!.text = "退出登陆"
            checkAdmin()
        } else {
            tv_login!!.text = "立即登陆"
        }
        if (eventBus.head_photo_status) {
            fim_head!!.setImageURI(localPath)
        } else {
            fim_head!!.setImageURI("res://drawable/" + R.drawable.ic_launcher)
        }
    }

    private fun checkAdmin() {
        if (FinalValue.ADMIN){
            tv_admin!!.text="管理员"
        }else{
            tv_admin!!.text="普通会员"
        }
    }
}