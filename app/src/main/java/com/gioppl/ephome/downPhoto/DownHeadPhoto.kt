package com.gioppl.ephome.downPhoto

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import com.avos.avoscloud.AVException
import com.avos.avoscloud.AVFile
import com.avos.avoscloud.GetDataCallback
import com.avos.avoscloud.ProgressCallback
import com.gioppl.ephome.EventBusMain
import com.gioppl.ephome.FinalValue
import org.greenrobot.eventbus.EventBus
import java.io.*
import java.util.*
/**
 * Created by GIOPPL on 2017/4/30.
 */

class DownHeadPhoto(url: String?) {//
    init {
        val photoID = decomposeUrl(url)//图片储存的ID  kotlin
         getPhoto(url, photoID)
    }

    private fun getPhoto(url: String?, objectId: String?) {
        val file = AVFile("test.jpg", url, HashMap<String, Any>())
        file.getThumbnailUrl(true, 100, 100)
        file.getDataInBackground(object : GetDataCallback() {
            override fun done(bytes: ByteArray, e: AVException?) {
                // bytes 就是文件的数据流
                // 创建GIOPPL文件夹，并且以name+headphoto作为文件名
                val uPath = Environment.getExternalStorageDirectory().absolutePath + "/GIOPPL/EPHome/my"
                val photoPath = "/$objectId.png"
                val GIOPPLfile = File(uPath)
                val headPhotoFile = File(uPath + photoPath)
                // GIOPPL文件夹不存在
                if (!GIOPPLfile.exists()) {
                    GIOPPLfile.mkdirs()
                    if (!headPhotoFile.exists()) {
                        // 储存图片
                        CreatPhoto(bytes, headPhotoFile)
                    } else {
                        headPhotoFile.delete()
                        // 储存图片
                        CreatPhoto(bytes, headPhotoFile)
                    }
                } else {// GIOPPL文件夹存在
                    if (!headPhotoFile.exists()) {
                        // 储存图片
                        CreatPhoto(bytes, headPhotoFile)
                    } else {
                        headPhotoFile.delete()
                        // 储存图片
                        CreatPhoto(bytes, headPhotoFile)
                    }
                }
            }
        }, object : ProgressCallback() {
            override fun done(integer: Int?) {
                // 下载进度数据，integer 介于 0 和 100。
                if (integer==100){
                    EventBus.getDefault().postSticky(EventBusMain(true,true))
                }
            }
        })
    }

    /**
     * 把文件流转化成头像

     * @param bytes
     * *
     * @param headphotofile
     * *
     * @author GIOPPL
     * *
     * @2016-12-11 下午8:50:39
     */
    private fun CreatPhoto(bytes: ByteArray, headphotofile: File) {
        val inputStream = ByteArrayInputStream(bytes)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        val out: FileOutputStream
        try {
            out = FileOutputStream(headphotofile)
            if (bitmap.compress(Bitmap.CompressFormat.PNG, 90, out)) {
                out.flush()
                out.close()
            }
        } catch (e1: FileNotFoundException) {
            e1.printStackTrace()
        } catch (e1: IOException) {
            e1.printStackTrace()
        }

    }

    /**
     * 原地址http://ac-rxsnxjjw.clouddn.com/ubrE5GSgls97xKfirV73qH6IEIc5Y9BVFS1ZRtNr.jpg
     * 转换成ubrE5GSgls97xKfirV73qH6IEIc5Y9BVFS1ZRtNr
     * http://ac-rxsnxjjw.clouddn.com/ubrE5GSgls97xKfirV73qH6IEIc5Y9BVFS1ZRtNr.jpg
     */
    private fun decomposeUrl(url: String?): String {
        var url = url
        url = url!!.split("http://ac-rxsnxjjw.clouddn.com/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
        url = url.substring(0, url.length - 6)
        FinalValue.HEAD_PHOTO_ADD = url
        return url
    }
}
