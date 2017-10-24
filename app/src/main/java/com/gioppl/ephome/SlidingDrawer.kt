package com.gioppl.ephome.ep

import android.content.Intent
import android.support.v4.app.FragmentActivity
import android.view.View
import android.widget.LinearLayout
import com.facebook.drawee.view.SimpleDraweeView
import com.gioppl.ephome.R
import com.gioppl.ephome.login.Login

/**
 * Created by GIOPPL on 2017/8/18.
 */
class SlidingDrawer(private var activity: FragmentActivity) :View.OnClickListener{


    var fim_head: SimpleDraweeView?=null
    var lin_update:LinearLayout?=null
    var lin_login:LinearLayout?=null
    init {
        initView()
    }

    private fun initView() {

        lin_login= activity.findViewById(R.id.lin_sliding_login) as LinearLayout?
        fim_head= activity.findViewById(R.id.fim_sliding_head) as SimpleDraweeView?
        fim_head!!.setImageURI("res://drawable/"+R.drawable.head)
        lin_login!!.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.lin_sliding_login->{
                activity.startActivity(Intent(activity,Login::class.java))
            }
        }
    }

}