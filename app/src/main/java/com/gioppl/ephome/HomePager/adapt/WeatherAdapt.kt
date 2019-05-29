package com.gioppl.ephome.HomePager.adapt

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.gioppl.ephome.HomePager.entity.WeatherBean
import com.gioppl.ephome.R

/**
 * Created by GIOPPL on 2017/12/4.
 */
class WeatherAdapt (private var mList:ArrayList<WeatherBean.DataBean>, private var context: Context,private var refreshWeatherData: RefreshWeatherData): RecyclerView.Adapter<WeatherAdapt.MyViewHolder>(){

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data=mList[position]
        holder.tv_temp!!.text=data.tem1+"/"+data.tem2
        holder.tv_windDirection!!.text=data.win[0]
        holder.tv_weather!!.text=data.wea.toString()
        holder.tv_air!!.text=data.air_level
        holder.tv_humidity!!.text=data.humidity.toString()
        holder.tv_date!!.text=data.day
        holder.ll_weather!!.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                holder.tv_temp!!.text="0"
                holder.tv_windDirection!!.text="0"
                holder.tv_weather!!.text="0"
                holder.tv_air!!.text="0"
                holder.tv_humidity!!.text="0"
                holder.tv_date!!.text="0"
                refreshWeatherData.refresh()
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int)
            = MyViewHolder(LayoutInflater.from(parent!!.context).inflate(R.layout.future_weather_item, parent, false))

    override fun getItemCount()=if (mList==null) 0 else mList.size

    class MyViewHolder(item: View):RecyclerView.ViewHolder(item){
        //天气预报
        var tv_air:TextView?=null
        var im_weather: ImageView?=null
        var im_refresh: ImageView?=null
        var tv_temp:TextView?=null
        var tv_weather:TextView?=null
        var tv_windDirection:TextView?=null
        var tv_humidity:TextView?=null
        var tv_date:TextView?=null
        var ll_weather:LinearLayout?=null

        init {
            tv_air= itemView.findViewById(R.id.tv_air) as TextView?
            tv_weather= itemView.findViewById(R.id.tv_weather) as TextView?
            tv_temp= itemView.findViewById(R.id.tv_temp) as TextView?
            tv_date= itemView.findViewById(R.id.tv_date) as TextView?
            tv_windDirection= itemView.findViewById(R.id.tv_windDirection) as TextView?
            tv_humidity= itemView.findViewById(R.id.tv_humidity) as TextView?
            tv_temp= itemView.findViewById(R.id.tv_temp) as TextView?
            im_weather= itemView.findViewById(R.id.im_weather) as ImageView?
            im_refresh= itemView.findViewById(R.id.im_refresh) as ImageView?
            ll_weather= itemView.findViewById(R.id.ll_weather) as LinearLayout?
        }
     }

    public interface RefreshWeatherData{
        fun refresh();
    }
}