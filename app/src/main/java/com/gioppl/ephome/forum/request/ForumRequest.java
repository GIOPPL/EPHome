package com.gioppl.ephome.forum.request;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.gioppl.ephome.FinalValue;
import com.gioppl.ephome.forum.ForumBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by GIOPPL on 2017/10/7.
 */

public class ForumRequest {
    private ForumCallback callback;
    private Context mContext;
    private ArrayList<ForumBean> beanList;

    public ForumRequest(Context mContext,ForumCallback callback){
        this.mContext=mContext;
        this.callback=callback;
        new Thread(new Runnable() {
            @Override
            public void run() {
                forumPost();
            }
        }).start();
    }
    private void forumPost(){
        String path = "http://116.196.91.8:8080/webtest/ServletPPLLimit";
        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("POST");
            String data="";
            Log.i("json",data);
            connection.setRequestProperty("Content-Type","application/json;charset=utf-8");
            Log.i("aa",data.length()+"");
            connection.setDoOutput(true);
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(data.getBytes("utf-8"));

            //获得结果码
            int responseCode = connection.getResponseCode();
            Log.i("code",responseCode+"");
            if(responseCode ==200){
                //请求成功
                InputStream is = connection.getInputStream();
                BufferedReader br=new BufferedReader(new InputStreamReader(is));
                String line=null;
                StringBuffer sb=new StringBuffer();
                while ((line=br.readLine())!=null){
                    sb.append(line);
                }
                is.close();
                br.close();
                Log.i("获取论坛数据成功",sb.toString());
                Message msg= FinalValue.Companion.getMessage(FormatForumBean(sb.toString()),0x1);
                mHandle.sendMessage(msg);
            }else {
                Message msg= FinalValue.Companion.getMessage("服务器错误",0x2);
                mHandle.sendMessage(msg);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Message msg= FinalValue.Companion.getMessage("获取数据失败，请查看网络连接",0x2);
            mHandle.sendMessage(msg);
        } catch (ProtocolException e) {
            e.printStackTrace();
            Message msg= FinalValue.Companion.getMessage("获取数据失败，请查看网络连接",0x2);
            mHandle.sendMessage(msg);
        } catch (IOException e) {
            e.printStackTrace();
            Message msg= FinalValue.Companion.getMessage("获取数据失败，请查看网络连接",0x2);
            mHandle.sendMessage(msg);
        }

    }


    // format the string to CircleBean
    private ArrayList<ForumBean> FormatForumBean(String json) {
        ArrayList<ForumBean> list;
        Type listType = new TypeToken<List<ForumBean>>() {}.getType();
        Gson gson=new Gson();
        list=gson.fromJson(json, listType);
        return list;
    }

    public interface ForumCallback{
        void success(ArrayList<ForumBean> beanList);
        void error(String error);
    }
    private Handler mHandle=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1==0x1){
                callback.success((ArrayList<ForumBean>) msg.obj);
            }else if (msg.arg1==0x2){
                callback.error((String) msg.obj);
            }
        }
    };
}
