package com.gioppl.ephome.voice

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.gioppl.ephome.R
import com.gioppl.ephome.voice.write2voice.BaseActivity

class VoiceTestActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voice_test)
        initialTts()
    }
    public fun start(view: View){
        val voicetext=(findViewById(R.id.et_content) as TextView).text.toString()
        checkResult(synthesizer!!.speak(voicetext), "speak")
    }

    /**
     * speak 实际上是调用 synthesize后，获取音频流，然后播放。
     * 获取音频流的方式见SaveFileActivity及FileSaveListener
     *
     */
    private fun speak() {

    }





}
