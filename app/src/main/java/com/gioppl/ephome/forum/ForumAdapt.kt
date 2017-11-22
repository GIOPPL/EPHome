package com.gioppl.ephome.forum

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.facebook.drawee.view.SimpleDraweeView
import com.gioppl.ephome.FinalValue
import com.gioppl.ephome.R
import org.greenrobot.eventbus.EventBus

/**
 * Created by GIOPPL on 2017/10/6.
 * http://ac-rxsnxjjw.clouddn.com/2e04c777b75c9b016c12.png
 */
class ForumAdapt(private var mList:ArrayList<ForumBean>?, private var context:Context): RecyclerView.Adapter<ForumAdapt.MyViewHolder>() {

    override fun onBindViewHolder(holder: MyViewHolder?, position: Int) {
        holder!!.sim_image!!.setImageURI("http://ac-rxsnxjjw.clouddn.com/LBT7wbbfWF0bIvzwbH7kmF0J62OpeQlaNTx7bSp3.jpg")
        holder.tv_title!!.text=mList!![position].subject
        holder.tv_content!!.text=mList!![position].message
        holder.lin_forum!!.setOnClickListener(View.OnClickListener {
            EventBus.getDefault().postSticky(mList!![position]);
            context.startActivity(Intent(context,ForumDetails::class.java))
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int)
            = MyViewHolder(LayoutInflater.from(parent!!.context).inflate(R.layout.forum_item,parent,false))

    override fun getItemCount():Int{
        if (mList == null) {
            FinalValue.errorMessage("获取到的mList的size是0")
            return 0
        }else{
            FinalValue.errorMessage("获取到的mList的size是"+mList!!.size)
            return mList!!.size
        }
    }

    class MyViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){
        var tv_title: TextView?=null
        var tv_content:TextView?=null
        var sim_image:SimpleDraweeView?=null
        var lin_forum:LinearLayout?=null
        init {
            tv_title= itemView.findViewById(R.id.tv_forumItem_title) as TextView?
            tv_content= itemView.findViewById(R.id.tv_forumItem_content) as TextView?
            sim_image= itemView.findViewById(R.id.sim_forumItem) as SimpleDraweeView?
            lin_forum= itemView.findViewById(R.id.lin_forumItem) as LinearLayout?
        }
    }

}