package com.gioppl.ephome.HomePager

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.gioppl.ephome.FinalJAVA
import com.gioppl.ephome.FinalValue
import com.gioppl.ephome.HomePager.adapt.WeatherAdapt
import com.gioppl.ephome.HomePager.entity.WeatherBean
import com.gioppl.ephome.R
import com.gioppl.ephome.net.WeatherModel
import com.gioppl.ephome.voice.write2voice.BaseActivity
import java.util.*

class FutureWeatherActivity : BaseActivity() {
    private var rv: RecyclerView? = null
    private var mAdapt: WeatherAdapt? = null
    private var tv_title:TextView?=null
    private var mList= ArrayList<WeatherBean.DataBean>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        getWeather()
        setContentView(R.layout.activity_future_weather)
        setAdaptManager()
    }

    private fun initView() {
//        (findViewById(R.id.tv_title_top) as TextView).text="未来七天天气"
    }

    private fun setAdaptManager() {
        rv = findViewById(R.id.rv_weather) as RecyclerView
        val layoutManager = LinearLayoutManager(this)
        rv!!.layoutManager = layoutManager as RecyclerView.LayoutManager?
        rv!!.setHasFixedSize(true)
        layoutManager.orientation = OrientationHelper.VERTICAL
        mAdapt = WeatherAdapt(mList, this,object : WeatherAdapt.RefreshWeatherData{
            override fun refresh() {
                getWeather()
            }
        })
        rv!!.adapter = mAdapt
    }
    public fun back(view:View){
        finish()
    }

    private fun getWeather() {
        WeatherModel(this, FinalJAVA.Weather, object : WeatherModel.ResultInterface {
            override fun success(bean: WeatherBean) {
                runOnUiThread {
                    FinalValue.toast(this@FutureWeatherActivity,"刷新完成")
                    mList.clear()
                    mList.addAll(bean.data)
                    mAdapt!!.notifyDataSetChanged()
                }
            }
        })
    }
    public fun startVoice(view:View){
        var text=""
        for (i in 0..mList.size-1){
            text=text+mList[i].day+" "+mList[i].wea.toString()+" "
        }

        checkResult(synthesizer!!.speak(text), "speak")
    }
}
