package com.gioppl.ephome.voice;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.RecognizerIntent;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.baidu.speech.EventListener;
import com.baidu.speech.EventManager;
import com.baidu.speech.EventManagerFactory;
import com.baidu.speech.asr.SpeechConstant;
import com.gioppl.ephome.FatherActivity;
import com.gioppl.ephome.FinalJAVA;
import com.gioppl.ephome.FinalValue;
import com.gioppl.ephome.PostRequest;
import com.gioppl.ephome.R;
import com.gioppl.ephome.forum.ForumBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.quinny898.library.persistentsearch.SearchBox;
import com.quinny898.library.persistentsearch.SearchResult;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VoiceActivity extends FatherActivity{
    EventManager eventManager;
    static boolean b = true;
    private SearchBox search;
    private CircularRippleButton btn_voiceGet;
    private String searchString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voice_activity);
        getPermission();
        initVoiceEventManager();
        initView();
    }

    private void initView() {
        btn_voiceGet = (CircularRippleButton) findViewById(R.id.btn_voiceActivity_voiceGet);
        btn_voiceGet.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.i("长按事件", "按下");
                        String json = "{\"accept-audio-data\":false,\"disable-punctuation\":false,\"accept-audio-volume\":true,\"pid\":1536}";
                        eventManager.send(SpeechConstant.ASR_START, json, null, 0, 0);
                        btn_voiceGet.startRipple();
                        return true;
                    case MotionEvent.ACTION_UP:
                        Log.i("长按事件", "抬起");
                        eventManager.send(SpeechConstant.ASR_STOP, null, null, 0, 0);
                        btn_voiceGet.stopRipple();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        Log.i("长按事件", "移动");
                        return false;
                }
                return false;
            }
        });

//        findViewById(R.id.im_voiceActivity_microphone).setOnClickListener(this);
        search = (SearchBox) findViewById(R.id.searchbox);
        search.enableVoiceRecognition(this);
//        for (int x = 0; x < 10; x++) {
//            SearchResult option = new SearchResult("Result " + Integer.toString(x), getResources().getDrawable(R.drawable.ic_history));
//            search.addSearchable(option);
//        }
        search.setMenuListener(new SearchBox.MenuListener() {

            @Override
            public void onMenuClick() {
                //Hamburger has been clicked
                Toast.makeText(VoiceActivity.this, "Menu click", Toast.LENGTH_LONG).show();
            }

        });
//        search.setLogoText("你好世界");
        search.setSearchListener(new SearchBox.SearchListener() {

            @Override
            public void onSearchOpened() {
                //Use this to tint the screen
            }

            @Override
            public void onSearchClosed() {
                //Use this to un-tint the screen
            }

            @Override
            public void onSearchTermChanged(String term) {
                //React to the search term changing
                //Called after it has updated results
            }

            @Override//搜索的地方
            public void onSearch(String searchTerm) {
//                Toast.makeText(VoiceActivity.this, searchTerm + " Searched", Toast.LENGTH_LONG).show();
                HashMap map = new HashMap<String, Object>();
                map.put("title",searchTerm);
                new PostRequest(map, FinalJAVA.BaseUrl+"/ServletPPLvague", PostRequest.POST, new PostRequest.RequestCallback() {
                    @Override
                    public void success(String back) {
                        Log.i("搜索帖子成功", back);
                        if (back.length()>20){
                            EventBus.getDefault().postSticky(formatResultJson(back));
                            startActivity(new Intent(VoiceActivity.this,com.gioppl.ephome.voice.SearchResult.class));
                        }else {
//                            FinalValue.Companion.toast(VoiceActivity.this,"没有该类信息");
                        }

                    }

                    @Override
                    public void error(String back) {
                        Log.i("搜索帖子失败", back);
                    }

                    @Override
                    public void getBeanList(ArrayList<Object> bean) {

                    }
                });
            }

            @Override
            public void onResultClick(SearchResult result) {
                //React to a result being clicked
            }

            @Override
            public void onSearchCleared() {
                //Called when the clear button is clicked
            }

        });
//        search.setOverflowMenu(R.menu.overflow_menu);
//        search.setOverflowMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.test_menu_item:
//                        Toast.makeText(VoiceActivity.this, "Clicked!", Toast.LENGTH_SHORT).show();
//                        return true;
//                }
//                return false;
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1234 && resultCode == RESULT_OK) {
            ArrayList<String> matches = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            search.populateEditText(matches.get(0));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void getPermission() {
        String permissions[] = {Manifest.permission.RECORD_AUDIO,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        ArrayList<String> toApplyList = new ArrayList<String>();
        for (String perm : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, perm)) {
                toApplyList.add(perm);
                //进入到这里代表没有权限.
                Toast.makeText(this, "没有权限", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initVoiceEventManager() {
        eventManager = EventManagerFactory.create(this, "asr");
        eventManager.registerListener(new EventListener() {
            @Override
            public void onEvent(String name, String s1, byte[] bytes, int i, int i1) {
                if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_READY)) {
//                    handler.sendMessage(getMsg("请说话"));
                    Message msg = new Message();
                    msg.obj = "请说话";
                    msg.arg1 = 0x30;
                    handler.sendMessage(msg);
                }
                if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_FINISH)) {
//                    handler.sendMessage(getMsg("finish"));
                    Message msg = new Message();
                    msg.obj = "完成";
                    msg.arg1 = 0x32;
                    handler.sendMessage(msg);
                }
                if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_PARTIAL)) {
//                    handler.sendMessage(getMsg(getMatcher(s1, MatcherRegex.RESULT, 1)));
                    Message msg = new Message();
                    msg.obj = formatVoiceJson(s1);
//                    msg.obj=getMatcher(s1, MatcherRegex.RESULT, 1);
                    msg.arg1 = 0x31;
                    handler.sendMessage(msg);
                }
            }
        });
    }

    private String voiceString;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.obj != null) {
                switch (msg.arg1) {
                    case 0x30:
                    case 0x31:
                        voiceString = msg.obj.toString();//正在录制
                    case 0x32:
                        Log.i("完成", "完成");
                        search.setLogoText(voiceString);
                }
                Log.i("输出语音", msg.obj.toString());
                searchString=msg.obj.toString();
                HashMap map = new HashMap<String, Object>();
                map.put("title",searchString);
                new PostRequest(map,FinalJAVA.BaseUrl+"/ServletPPLvague", PostRequest.POST, new PostRequest.RequestCallback() {
                    @Override
                    public void success(String back) {
                        Log.i("搜索帖子成功", back);
                        if (back.length()>20){
                            EventBus.getDefault().postSticky(formatResultJson(back));
                            startActivity(new Intent(VoiceActivity.this,com.gioppl.ephome.voice.SearchResult.class));
                        }else {
//                            FinalValue.Companion.toast(VoiceActivity.this,"没有该类信息");
                        }

                    }

                    @Override
                    public void error(String back) {
                        Log.i("搜索帖子失败", back);
                    }

                    @Override
                    public void getBeanList(ArrayList<Object> bean) {

                    }
                });
            }
        }
    };

    private String formatVoiceJson(String json) {
        Gson gson = new Gson();
        java.lang.reflect.Type type = new TypeToken<VoiceEntity>() {
        }.getType();
        VoiceEntity jsonBean = gson.fromJson(json, type);
        return jsonBean.getBest_result();
    }
    private ArrayList<ForumBean> formatResultJson(String json) {
        ArrayList<ForumBean> list;
        Type listType = new TypeToken<List<ForumBean>>() {}.getType();
        Gson gson=new Gson();
        list=gson.fromJson(json, listType);
        return list;
    }

}