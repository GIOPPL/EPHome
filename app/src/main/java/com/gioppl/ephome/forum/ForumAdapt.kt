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
import com.gioppl.ephome.R
import org.greenrobot.eventbus.EventBus

class ForumAdapt(private var mList:ArrayList<ForumBean>?, private var context:Context): RecyclerView.Adapter<ForumAdapt.MyViewHolder>() {

    override fun onBindViewHolder(holder: MyViewHolder?, position: Int) {
            holder!!.sim_image!!.setImageURI(mList!![position].uimage)
            holder.tv_title!!.text=mList!![position].title
            holder.tv_content!!.text=mList!![position].content
            holder.tv_address!!.text=mList!![position].address

            holder.lin_forum!!.setOnClickListener(View.OnClickListener {
                EventBus.getDefault().postSticky(mList!![position]);
                context.startActivity(Intent(context,ForumDetails::class.java))
            })
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int)
            = MyViewHolder(LayoutInflater.from(parent!!.context).inflate(R.layout.forum_item,parent,false))

    override fun getItemCount():Int{
        if (mList == null) {
            return 0
        }else{
            return mList!!.size
        }
    }

    class MyViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){
        var tv_title: TextView?=null
        var tv_content:TextView?=null
        var tv_address:TextView?=null
        var sim_image:SimpleDraweeView?=null
        var lin_forum:LinearLayout?=null
        init {
            tv_address= itemView.findViewById(R.id.tv_forumItem_address) as TextView?
            tv_title= itemView.findViewById(R.id.tv_forumItem_title) as TextView?
            tv_content= itemView.findViewById(R.id.tv_forumItem_content) as TextView?
            sim_image= itemView.findViewById(R.id.sim_forumItem) as SimpleDraweeView?
            lin_forum= itemView.findViewById(R.id.lin_forumItem) as LinearLayout?
        }
    }

}