package com.gioppl.ephome.voice.write2voice

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import com.baidu.tts.chainofresponsibility.logger.LoggerProxy
import com.baidu.tts.client.SpeechSynthesizer
import com.baidu.tts.client.TtsMode
import com.gioppl.ephome.voice.write2voice.control.InitConfig
import com.gioppl.ephome.voice.write2voice.control.MySyntherizer
import com.gioppl.ephome.voice.write2voice.control.NonBlockSyntherizer
import com.gioppl.ephome.voice.write2voice.listener.UiMessageListener
import com.gioppl.ephome.voice.write2voice.util.OfflineResource
import java.io.IOException
import java.util.*


/**
 * Created by fujiayi on 2017/9/13.
 *需要合成的文本text的长度不能超过1024个GBK字节。
 *
 * 此类 底层UI实现 无SDK相关逻辑
 */

open class BaseActivity : Activity(), MainHandlerConstant {
    protected var offlineVoice = OfflineResource.VOICE_MALE
    protected var appId = "10435208"
    protected var appKey = "XfgygCcI2lNSwigFIu5o0ZyO"
    protected var secretKey = "47e41117573d5e94a2263e6618b54535"
    protected var ttsMode = TtsMode.MIX
    protected var synthesizer: MySyntherizer?=null
    protected var mainHandler: Handler?=null

    /*
     * @param savedInstanceState
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //        setContentView(R.layout.activity_synth);
        mainHandler = object : Handler() {
            /*
             * @param msg
             */
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                handle(msg)
            }

        }
        initPermission() // android 6.0以上动态权限申请
        initialTts()
    }


    public open fun handle(msg: Message) {

    }

    public fun toPrint(str: String) {
        val msg = Message.obtain()
        msg.obj = str
        mainHandler!!.sendMessage(msg)
    }

    public fun print(msg: Message) {
        val message = msg.obj as String
    }



    /**
     * android 6.0 以上需要动态申请权限
     */
    private fun initPermission() {
        val permissions = arrayOf(Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.MODIFY_AUDIO_SETTINGS, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_SETTINGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.CHANGE_WIFI_STATE)

        val toApplyList = ArrayList<String>()

        for (perm in permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, perm)) {
                toApplyList.add(perm)
                // 进入到这里代表没有权限.
            }
        }
        val tmpList = arrayOfNulls<String>(toApplyList.size)
        if (!toApplyList.isEmpty()) {
            ActivityCompat.requestPermissions(this, toApplyList.toTypedArray(), 123)
        }

    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        // 此处为android 6.0以上动态授权的回调，用户自行实现。
    }

    companion object {
        private val TAG = "MainActivity"
    }
    public fun getParams(): Map<String, String> {
        val params = HashMap<String, String>()
        // 以下参数均为选填
        // 设置在线发声音人： 0 普通女声（默认） 1 普通男声 2 特别男声 3 情感男声<度逍遥> 4 情感儿童声<度丫丫>
        params[SpeechSynthesizer.PARAM_SPEAKER] = "0"
        // 设置合成的音量，0-9 ，默认 5
        params[SpeechSynthesizer.PARAM_VOLUME] = "9"
        // 设置合成的语速，0-9 ，默认 5
        params[SpeechSynthesizer.PARAM_SPEED] = "5"
        // 设置合成的语调，0-9 ，默认 5
        params[SpeechSynthesizer.PARAM_PITCH] = "5"
        params[SpeechSynthesizer.PARAM_MIX_MODE] = SpeechSynthesizer.MIX_MODE_DEFAULT
        // 该参数设置为TtsMode.MIX生效。即纯在线模式不生效。
        // MIX_MODE_DEFAULT 默认 ，wifi状态下使用在线，非wifi离线。在线状态下，请求超时6s自动转离线
        // MIX_MODE_HIGH_SPEED_SYNTHESIZE_WIFI wifi状态下使用在线，非wifi离线。在线状态下， 请求超时1.2s自动转离线
        // MIX_MODE_HIGH_SPEED_NETWORK ， 3G 4G wifi状态下使用在线，其它状态离线。在线状态下，请求超时1.2s自动转离线
        // MIX_MODE_HIGH_SPEED_SYNTHESIZE, 2G 3G 4G wifi状态下使用在线，其它状态离线。在线状态下，请求超时1.2s自动转离线

        // 离线资源文件， 从assets目录中复制到临时目录，需要在initTTs方法前完成
        val offlineResource = createOfflineResource(offlineVoice)
        // 声学模型文件路径 (离线引擎使用), 请确认下面两个文件存在
        params[SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE] = offlineResource!!.getTextFilename()
        params[SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE] = offlineResource.getModelFilename()
        return params
    }
    public fun initialTts() {
        LoggerProxy.printable(true)
        // 此处可以改为 含有您业务逻辑的SpeechSynthesizerListener的实现类
        val listener = UiMessageListener(mainHandler)
        val params = getParams()
        val initConfig = InitConfig(appId, appKey, secretKey, ttsMode, params, listener)
        synthesizer = NonBlockSyntherizer(this, initConfig, mainHandler) // 此处可以改为MySyntherizer 了解调用过程
    }
    public fun createOfflineResource(voiceType: String): OfflineResource? {
        var offlineResource: OfflineResource? = null
        try {
            offlineResource = OfflineResource(this, voiceType)
        } catch (e: IOException) {
            // IO 错误自行处理
            e.printStackTrace()
            toPrint("【error】:copy files from assets failed." + e.message)
        }

        return offlineResource
    }

    public fun checkResult(result: Int, method: String) {
        if (result != 0) {
            toPrint("error code :$result method:$method, 错误码文档:http://yuyin.baidu.com/docs/tts/122 ")
        }
    }

    /**
     * 暂停播放。仅调用speak后生效
     */
    private fun pause() {
        val result = synthesizer!!.pause()
        checkResult(result, "pause")
    }

    /**
     * 继续播放。仅调用speak后生效，调用pause生效
     */
    private fun resume() {
        val result = synthesizer!!.resume()
        checkResult(result, "resume")
    }

    /*
     * 停止合成引擎。即停止播放，合成，清空内部合成队列。
     */
    private fun stop() {
        val result = synthesizer!!.stop()
        checkResult(result, "stop")
    }

    override fun onDestroy() {
        synthesizer!!.release()
        Log.i(TAG, "释放资源成功")
        super.onDestroy()
    }
}
