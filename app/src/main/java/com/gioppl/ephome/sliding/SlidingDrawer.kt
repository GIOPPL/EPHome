package com.gioppl.ephome.ep

import android.content.Intent
import android.os.Environment
import android.support.v4.app.FragmentActivity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.facebook.drawee.view.SimpleDraweeView
import com.gioppl.ephome.EventBusMain
import com.gioppl.ephome.FinalValue
import com.gioppl.ephome.R
import com.gioppl.ephome.admin.AdminActivity
import com.gioppl.ephome.sliding.AboutMe
import com.gioppl.ephome.sliding.InternetActivity
import com.gioppl.ephome.sliding.login.Login
import com.gioppl.ephome.sliding.userInfor.UpdateUser
import com.zzti.fengyongge.imagepicker.PhotoSelectorActivity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


/**
 * Created by GIOPPL on 2017/8/18.
 */
class SlidingDrawer(private var activity: FragmentActivity,private var view:View) : View.OnClickListener {


    var tv_login: TextView? = null
    var sim_head: SimpleDraweeView? = null
    var lin_update: LinearLayout? = null
    var lin_login: LinearLayout? = null
    var lin_inter:LinearLayout?=null
    var tv_admin: TextView? = null

    init {
        initView()
        EventBus.getDefault().register(this)
    }

    private fun initView() {

        tv_admin = view.findViewById(R.id.tv_sliding_admin) as TextView?
        tv_login = view.findViewById(R.id.tv_sliding_login) as TextView?
        lin_login = view.findViewById(R.id.lin_sliding_login) as LinearLayout?
        lin_inter= view.findViewById(R.id.lin_sliding_interface) as LinearLayout?
        sim_head = view.findViewById(R.id.sim_sliding_head) as SimpleDraweeView?
        lin_inter!!.setOnClickListener(this)
        view.findViewById(R.id.lin_sliding_update).setOnClickListener(this)
        view.findViewById(R.id.lin_sliding_about).setOnClickListener(this)
        view.findViewById(R.id.lin_sliding_admin).setOnClickListener(this)
        sim_head!!.setImageURI(FinalValue.HEAD_PHOTO_URL)
        lin_login!!.setOnClickListener(this)
        sim_head!!.setOnClickListener(View.OnClickListener {
            val intent = Intent(activity, PhotoSelectorActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            intent.putExtra("limit", 1)//number是选择图片的数量
            activity.startActivityForResult(intent, 0)
        })
        tv_admin!!.text="未登录"
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.lin_sliding_login -> {
                activity.startActivity(Intent(activity, Login::class.java))
            }
            R.id.lin_sliding_update -> {
                if (FinalValue.LOAD_STA) {
                    activity.startActivity(Intent(activity, UpdateUser::class.java))
                } else
                    FinalValue.toast(activity, "您未登陆，不能更改信息")
            }
            R.id.lin_sliding_about -> {
                activity.startActivity(Intent(activity, AboutMe::class.java))
            }
            R.id.lin_sliding_interface->{
                activity.startActivity(Intent(activity, InternetActivity::class.java))
            }
            R.id.lin_sliding_admin->{
                if (FinalValue.ADMIN){
                    activity.startActivity(Intent(activity, AdminActivity::class.java))
                }else{
                    Toast.makeText(activity,"您无权进入",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    fun helloEventBus(eventBus: EventBusMain) {
        val localPath = "file:" + Environment.getExternalStorageDirectory().absolutePath + "/GIOPPL/EPHome/my/" + FinalValue.HEAD_PHOTO_ADD + ".png"
        FinalValue.successMessage("成功接收到登陆界面传递的数据." + localPath)
        if (eventBus.login_status) {
            tv_login!!.text = "退出登陆"
            tv_admin!!.text = "普通会员"
        } else {
            tv_login!!.text = "立即登陆"
        }
        sim_head!!.setImageURI(FinalValue.HEAD_PHOTO_URL)
        (view.findViewById(R.id.tv_sliding_name) as TextView).text = FinalValue.USER_NAME
    }
}