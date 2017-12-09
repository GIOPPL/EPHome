package com.gioppl.ephome.HomePager.adapt

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.gioppl.ephome.HomePager.BiologyEntity
import com.gioppl.ephome.HomePager.entity.PollutionEntity
import com.gioppl.ephome.R
import com.gioppl.ephome.policy.PollutionDetail
import org.greenrobot.eventbus.EventBus

/**
 * Created by GIOPPL on 2017/12/4.
 */
class BiologyAdapt (private var mList:ArrayList<BiologyEntity>, private var context: Context): RecyclerView.Adapter<BiologyAdapt.MyViewHolder>(){

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val mod=mList[position]
        holder.tv_msg!!.visibility=View.GONE
        holder.tv_title!!.text=mList[position].title
        holder.lin!!.setOnClickListener(View.OnClickListener {
            EventBus.getDefault().postSticky(PollutionEntity(mod.title,mod.xinxi));
            context.startActivity(Intent(context, PollutionDetail::class.java))
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int)
            = MyViewHolder(LayoutInflater.from(parent!!.context).inflate(R.layout.policy_item, parent, false))

    override fun getItemCount()=if (mList==null) 0 else mList.size

    class MyViewHolder(item: View):RecyclerView.ViewHolder(item){
        var tv_title: TextView?=null
        var tv_msg: TextView?=null
        var lin: LinearLayout?=null
        init {
            lin= itemView.findViewById(R.id.lin_policyItem) as LinearLayout?
            tv_title= itemView.findViewById(R.id.tv_policyItem_title) as TextView?
            tv_msg= itemView.findViewById(R.id.tv_policyItem_msg) as TextView?

        }
     }
}