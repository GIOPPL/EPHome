package com.gioppl.ephome.forum

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.gioppl.ephome.HomePager.entity.PollutionEntity
import com.gioppl.ephome.R
import com.gioppl.ephome.policy.PollutionDetail
import org.greenrobot.eventbus.EventBus

/**
 * Created by GIOPPL on 2017/10/6.
 * http://ac-rxsnxjjw.clouddn.com/2e04c777b75c9b016c12.png
 */
class PollutionAdapt(private var mList:ArrayList<PollutionEntity>, private var context:Context): RecyclerView.Adapter<PollutionAdapt.MyPollutionViewHolder>() {

    override fun onBindViewHolder(holder: MyPollutionViewHolder, position: Int) {
        val mod=mList[position]
        holder.tv_msg!!.visibility=View.GONE
        holder.tv_title!!.text=mList[position].environmental_type
        holder.lin!!.setOnClickListener(View.OnClickListener {
            EventBus.getDefault().postSticky(PollutionEntity(mod.environmental_type,mod.content));
            context.startActivity(Intent(context,PollutionDetail::class.java))
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int)
            = MyPollutionViewHolder(LayoutInflater.from(parent!!.context).inflate(R.layout.policy_item,parent,false))

    override fun getItemCount()=if (mList==null) 0 else mList.size

    class MyPollutionViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){
        var tv_title:TextView?=null
        var tv_msg:TextView?=null
        var lin:LinearLayout?=null
        init {
            lin= itemView.findViewById(R.id.lin_policyItem) as LinearLayout?
            tv_title= itemView.findViewById(R.id.tv_policyItem_title) as TextView?
            tv_msg= itemView.findViewById(R.id.tv_policyItem_msg) as TextView?

        }
    }

}