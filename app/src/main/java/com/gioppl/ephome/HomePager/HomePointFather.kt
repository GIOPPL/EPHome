package com.gioppl.ephome.HomePager

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.StrictMode
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.TextView
import com.gioppl.ephome.FinalValue
import com.gioppl.ephome.R
import com.gioppl.ephome.method.LongPressLinkMovementMethod
import com.zzhoujay.markdown.MarkDown
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

/**
 * Created by GIOPPL on 2017/10/17.
 */
class HomePointFather :AppCompatActivity(){
    private var eventBus:HomePointModel?=null
    private var tv_md:TextView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.p1_achievement)
        EventBus.getDefault().register(this)
        initView()
        initMarkDown()
    }
    private fun initMarkDown() {
        assert(tv_md != null)
        tv_md!!.setMovementMethod(LongPressLinkMovementMethod.getInstance())
        setText(eventBus!!.raw, tv_md!!)
    }
    private fun setText(resId: Int, tv: TextView) {
        val stream = resources.openRawResource(resId)
        tv.post(Runnable {
            val time = System.nanoTime()
            val spanned = MarkDown.fromMarkdown(stream, object : Html.ImageGetter {
                val TAG = "Markdown"
                override fun getDrawable(source: String): Drawable {
                    Log.d(TAG, "getDrawable() called with: source = [$source]")
                    var drawable: Drawable
                    try {
                        drawable = drawableFromUrl(source)
                        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
                    } catch (e: IOException) {
                        Log.w(TAG, "can't get image", e)
                        drawable = ColorDrawable(Color.LTGRAY)
                        drawable.setBounds(0, 0, tv!!.getWidth() - tv!!.getPaddingLeft() - tv!!.getPaddingRight(), 400)
                    }

                    return drawable
                }
            }, tv)
            val useTime = System.nanoTime() - time
            tv!!.text = (spanned)
        })
    }
    @Throws(IOException::class)
    fun drawableFromUrl(url: String): Drawable {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        val x: Bitmap
        val connection = URL(url).openConnection() as HttpURLConnection
        connection.connect()
        val input = connection.inputStream
        x = BitmapFactory.decodeStream(input)
        return BitmapDrawable(x)
    }

    private fun initView() {
        tv_md= findViewById(R.id.tv_ach_1) as TextView?
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    fun helloEventBus(eventBus: HomePointModel) {
        this.eventBus=eventBus


        FinalValue.successMessage("SuccessEventBus")
    }

    public fun back(v:View){
        finish()
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }
}