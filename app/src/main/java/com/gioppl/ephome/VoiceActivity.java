//package com.gioppl.ephome;
//
//import android.Manifest;
//import android.content.pm.PackageManager;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.View;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.baidu.speech.EventListener;
//import com.baidu.speech.EventManager;
//import com.baidu.speech.EventManagerFactory;
//import com.baidu.speech.asr.SpeechConstant;
//
//import java.util.ArrayList;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class VoiceActivity extends AppCompatActivity implements View.OnClickListener{
//    EventManager eventManager;
//    static boolean b = true;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        findViewById(R.id.button).setOnClickListener(this);
//        String permissions[] = {Manifest.permission.RECORD_AUDIO,
//                Manifest.permission.ACCESS_NETWORK_STATE,
//                Manifest.permission.INTERNET,
//                Manifest.permission.READ_PHONE_STATE,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE
//        };
//        ArrayList<String> toApplyList = new ArrayList<String>();
//        for (String perm :permissions){
//            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, perm)) {
//                toApplyList.add(perm);
//                //进入到这里代表没有权限.
//                Toast.makeText(this,"没有权限", Toast.LENGTH_SHORT).show();
//            }
//        }
//
//        eventManager = EventManagerFactory.create(this, "asr");
//        eventManager.registerListener(new EventListener() {
//            @Override
//            public void onEvent(String name, String s1, byte[] bytes, int i, int i1) {
//                if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_READY)) {
//                    handler.sendMessage(getMsg("请说话"));
//                }
//                if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_FINISH)) {
//                    handler.sendMessage(getMsg("finish"));
//                }
//                if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_PARTIAL)) {
//                    handler.sendMessage(getMsg(getMatcher(s1, MatcherRegex.RESULT, 1)));
//                }
//            }
//        });
//
//    }
//
//    static class MatcherRegex {
//        public static final String RESULT = ".*\\{\"word\":\\[\"(.*?)\"\\]\\}.*";
//    }
//    @Override
//    public void onClick(View view) {
//        int id = view.getId();
//        switch (id) {
//            case R.id.button: {
//                if (b) {
//                    String json = "{\"accept-audio-data\":false,\"disable-punctuation\":false,\"accept-audio-volume\":true,\"pid\":1536}";
//                    eventManager.send(SpeechConstant.ASR_START, json, null, 0, 0);
//                    b = false;
//                } else {
//                    eventManager.send(SpeechConstant.ASR_STOP, null, null, 0, 0);
//                    b = true;
//                }
//            }
//            break;
//        }
//    }
//
//    Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            if (msg.obj != null) {
//                ((TextView) findViewById(R.id.textView2)).append(msg.obj.toString() + "\n\n");
//                Log.i("输出语音",msg.obj.toString());
//            }
//
//        }
//    };
//    private Message getMsg(Object obj) {
//        Message msg = handler.obtainMessage();
//        msg.obj = obj;
//        return msg;
//    }
//    private String getMatcher(String info, String regex, int index) {
//        Matcher m = Pattern.compile(regex).matcher(info);
//        m.find();
//        try {
//            return m.group(index);
//        } catch (RuntimeException e) {
//            return null;
//        }
//    }
//}
