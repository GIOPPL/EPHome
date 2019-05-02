package com.gioppl.ephome.HomePager.net;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.gioppl.ephome.FinalJAVA;
import com.gioppl.ephome.FinalValue;
import com.gioppl.ephome.HomePager.entity.PollutionEntity;
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
 * Created by GIOPPL on 2017/11/28.
 */

public class PollutionPost {
    private PollutionCallback callback;
    private Context context;

    public PollutionPost(Context context,  PollutionCallback callback) {
        this.callback = callback;
        this.context = context;
        new Thread(new Runnable() {
            @Override
            public void run() {
                loginByPost();
            }
        }).start();
    }

    public interface PollutionCallback{
        void success(ArrayList<PollutionEntity> list);
        void error(String error);
    }

    /** * post的方式请求
     *@return 返回null 登录异常
     */
    public  void loginByPost(){
        Message msg;
        String path = FinalJAVA.BaseUrl+ "/ServletEnvGetall";
        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("GET");

            //数据准备
            String data ="";
            //至少要设置的两个请求头
            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

            //post的方式提交实际上是留的方式提交给服务器
            connection.setDoOutput(true);
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(data.getBytes());

            //获得结果码
            int responseCode = connection.getResponseCode();
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
                System.out.println(sb.toString());
                msg= FinalValue.Companion.getMessage(sb.toString(),0x31);
            }else {
                //请求失败
                msg= FinalValue.Companion.getMessage(responseCode+"",0x32);
            }
            handler.sendMessage(msg);
        } catch (MalformedURLException e) {
            msg= FinalValue.Companion.getMessage(e.getMessage(),0x32);
            handler.sendMessage(msg);
            e.printStackTrace();
        } catch (ProtocolException e) {
            msg= FinalValue.Companion.getMessage(e.getMessage(),0x32);
            handler.sendMessage(msg);
            e.printStackTrace();
        } catch (IOException e) {
            msg= FinalValue.Companion.getMessage(e.getMessage(),0x32);
            handler.sendMessage(msg);
            e.printStackTrace();
        }

    }



    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1==0x31){
                ArrayList<PollutionEntity> list=formatJsonToEntity((String) msg.obj);
                callback.success(list);
            }else if (msg.arg1==0x32){
                callback.error((String) msg.obj);
            }
        }
    };
    private ArrayList<PollutionEntity> formatJsonToEntity(String json) {
        ArrayList<PollutionEntity> list;
        Type listType = new TypeToken<List<PollutionEntity>>() {}.getType();
        Gson gson=new Gson();
        list=gson.fromJson(json, listType);
        return list;
    }


}
