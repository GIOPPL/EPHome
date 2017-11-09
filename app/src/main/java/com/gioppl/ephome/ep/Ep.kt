package com.gioppl.ephome.ep

import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.StrictMode
import android.support.v4.app.Fragment
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.zhouwei.library.CustomPopWindow
import com.gioppl.ephome.R
import com.gioppl.ephome.method.LongPressLinkMovementMethod
import com.zzhoujay.markdown.MarkDown
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

/**
 * Created by GIOPPL on 2017/10/8.
 */
class Ep : Fragment() {
    private var tv_pop: TextView? = null
    private var tv_1: TextView? = null
    private var tv_2: TextView? = null
    private var tv_3: TextView? = null
    private var im_top: ImageView? = null
    private var lin_ep: LinearLayout? = null
    private var flag = false//false为看不见
    private var mCustomPopWindow: CustomPopWindow? = null
    private var ed_count: EditText? = null
    private var tv_money: TextView? = null
    private var sort=-1//种类，猪粪为0开始
    private val UP: List<Double> = listOf(11.5,2.9,2.1,123.89,12.5)//单价

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?)
            = inflater!!.inflate(R.layout.ep, container, false)!!

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        initMarkDown()
    }

    private fun initMarkDown() {
        assert(tv_1 != null)
        tv_1!!.setMovementMethod(LongPressLinkMovementMethod.getInstance())
        assert(tv_2 != null)
        tv_2!!.setMovementMethod(LongPressLinkMovementMethod.getInstance())
        assert(tv_3 != null)
        tv_3!!.setMovementMethod(LongPressLinkMovementMethod.getInstance())
        setText(R.raw.mk_ep_1, tv_1!!)
        setText(R.raw.mk_ep_2, tv_2!!)
        setText(R.raw.mk_ep_3, tv_3!!)
    }

    private fun initView() {
        tv_pop = activity.findViewById(R.id.tv_epCal_pop) as TextView?
        tv_pop!!.setOnClickListener(View.OnClickListener { showPopMenu() })
        im_top = activity.findViewById(R.id.im_top_calculator) as ImageView?
        tv_1 = activity.findViewById(R.id.tv_ep_1) as TextView?
        tv_2 = activity.findViewById(R.id.tv_ep_2) as TextView?
        tv_3 = activity.findViewById(R.id.tv_ep_3) as TextView?
        lin_ep = activity.findViewById(R.id.lin_ep) as LinearLayout?
        im_top!!.setOnClickListener(View.OnClickListener {
            if (flag) {
                lin_ep!!.visibility = View.GONE
                flag = false
            } else {
                lin_ep!!.visibility = View.VISIBLE
                flag = true
            }

        })

        tv_money = activity.findViewById(R.id.tv_epCal_money) as TextView?
        ed_count = activity.findViewById(R.id.ed_epCal_count) as EditText?

        tv_money!!.setOnClickListener(View.OnClickListener {
            var count:Int=Integer.parseInt(ed_count!!.text.toString())
            if (sort!=-1){
                tv_money!!.text=("￥"+count*UP[sort])
            }
        })

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
                        drawable.setBounds(0, 0, tv.getWidth() - tv.getPaddingLeft() - tv.getPaddingRight(), 400)
                    }

                    return drawable
                }
            }, tv)
            val useTime = System.nanoTime() - time
            tv.text = (spanned)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId != 0x1 && item.itemId != 0x2) {
            setText(item.itemId, tv_1!!)
            setText(item.itemId, tv_2!!)
            setText(item.itemId, tv_3!!)
            return true
        } else if (item.itemId == 0x1) {
            resources.configuration.uiMode = resources.configuration.uiMode or Configuration.UI_MODE_NIGHT_YES
            resources.configuration.uiMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_NO.inv()
            resources.updateConfiguration(resources.configuration, resources.displayMetrics)
            activity.recreate()
            return true
        } else {
            return super.onOptionsItemSelected(item)
        }
    }

    private fun showPopMenu() {
        val contentView = LayoutInflater.from(activity).inflate(R.layout.pop_menu, null)
        //处理popWindow 显示内容
        handleLogic(contentView)
        //创建并显示popWindow
        mCustomPopWindow = CustomPopWindow.PopupWindowBuilder(activity)
                .setView(contentView)
                .create()
                .showAsDropDown(tv_pop, 0, 20)
    }

    /**
     * 处理弹出显示内容、点击事件等逻辑
     * @param contentView
     */
    private fun handleLogic(contentView: View) {
        val listener = View.OnClickListener { v ->
            if (mCustomPopWindow != null) {
                mCustomPopWindow!!.dissmiss()
            }
            var showContent = ""
            when (v.id) {
                R.id.menu1 -> {
                    showContent = "猪粪"
                    tv_pop!!.text=showContent
                    sort=0
                }
                R.id.menu2 -> {
                    showContent = "鸡粪"
                    tv_pop!!.text=showContent
                    sort=1
                }
                R.id.menu3 -> {
                    showContent = "稻谷皮"
                    tv_pop!!.text=showContent
                    sort=2
                }
                R.id.menu4 -> {
                    showContent = "兔屎"
                    tv_pop!!.text=showContent
                    sort=3
                }
                R.id.menu5 -> {
                    showContent = "猫屎"
                    tv_pop!!.text=showContent
                    sort=4
                }
            }
//            Toast.makeText(activity, showContent, Toast.LENGTH_SHORT).show()
        }
        contentView.findViewById(R.id.menu1).setOnClickListener(listener)
        contentView.findViewById(R.id.menu2).setOnClickListener(listener)
        contentView.findViewById(R.id.menu3).setOnClickListener(listener)
        contentView.findViewById(R.id.menu4).setOnClickListener(listener)
        contentView.findViewById(R.id.menu5).setOnClickListener(listener)
    }

}

