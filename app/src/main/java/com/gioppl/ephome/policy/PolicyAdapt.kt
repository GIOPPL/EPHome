package com.gioppl.ephome.forum

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.gioppl.ephome.R
import com.gioppl.ephome.policy.PolicyDetail
import com.gioppl.ephome.policy.PolicyEntity
import org.greenrobot.eventbus.EventBus

/**
 * Created by GIOPPL on 2017/10/6.
 * http://ac-rxsnxjjw.clouddn.com/2e04c777b75c9b016c12.png
 */
class PolicyAdapt(private var mList:ArrayList<PolicyEntity>, private var context:Context): RecyclerView.Adapter<PolicyAdapt.MyPolicyViewHolder>() {

    override fun onBindViewHolder(holder: MyPolicyViewHolder, position: Int) {
//        val mod=mList[position]
        holder.tv_title!!.text=mList[position].ztitle
        holder.tv_msg!!.visibility=View.GONE
        holder.lin!!.setOnClickListener(View.OnClickListener {
            EventBus.getDefault().postSticky(mList[position]);
            context.startActivity(Intent(context,PolicyDetail::class.java))
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int)
            = MyPolicyViewHolder(LayoutInflater.from(parent!!.context).inflate(R.layout.policy_item,parent,false))

    override fun getItemCount()=if (mList==null) 0 else mList.size

    class MyPolicyViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){
        var tv_title:TextView?=null
        var tv_msg:TextView?=null
        var lin:LinearLayout?=null
        init {
            lin= itemView.findViewById(R.id.lin_policyItem) as LinearLayout?
            tv_title= itemView.findViewById(R.id.tv_policyItem_title) as TextView?
            tv_msg= itemView.findViewById(R.id.tv_policyItem_msg) as TextView?
        }
    }

    override fun hashCode(): Int {
        return 222222
    }
}