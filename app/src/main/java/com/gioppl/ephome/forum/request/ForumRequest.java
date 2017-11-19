package com.gioppl.ephome.forum.request;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.CloudQueryCallback;
import com.gioppl.ephome.FinalValue;
import com.gioppl.ephome.forum.ForumBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by GIOPPL on 2017/10/7.
 */

public class ForumRequest {
    private ForumData mCircleData;
    private Context mContext;
    private ArrayList<ForumBean> beanList;

    public ForumRequest(Context mContext,ForumData mCircleData){
        this.mContext=mContext;
        this.mCircleData=mCircleData;
        getForumDate();
        System.out.println("无敌泡泡龙，，李嘉欣");

    }

    public void getForumDate(){
        String sql_getCircleDate="select * from Forum where id>=1" ;
        AVQuery.doCloudQueryInBackground(sql_getCircleDate, new CloudQueryCallback<AVCloudQueryResult>() {
            @Override
            public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                if (e!=null){
                    Log.i("ERROR",e.getMessage()+"--"+e.getCode());
                }else {
                    String s1=avCloudQueryResult.getResults().toString();
                    FinalValue.Companion.successMessage(s1);
                    beanList=FormatCircleBean(s1);
                    Message msg=new Message();
                    msg.arg1=0x1;
                    msg.obj=beanList;
                    mHandle.sendMessage(msg);

                }
            }
        });
    }

    // format the string to CircleBean
    private ArrayList<ForumBean> FormatCircleBean(String S_circle) {
        ArrayList<ForumBean> list;
        Type listType = new TypeToken<List<ForumBean>>() {}.getType();
        Gson gson=new Gson();
        list=gson.fromJson(S_circle, listType);
        System.out.println("无敌泡泡龙，，李嘉欣");
        return list;
    }

    public interface ForumData{
        void getData(ArrayList<ForumBean> beanList);
    }
    private Handler mHandle=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1==0x1){
                mCircleData.getData((ArrayList<ForumBean>) msg.obj);
            }
        }
    };
}
