package com.gioppl.ephome.sliding.login;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.gioppl.ephome.FinalJAVA;
import com.gioppl.ephome.FinalValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by GIOPPL on 2017/11/28.
 */

public class RegisterPost {
    private RegisterCallback callback;
    private Context context;

    public RegisterPost(Context context,final String uname,final String upwd,final String iphone,final String email,final String address, RegisterCallback callback) {
        this.callback = callback;
        this.context = context;
        new Thread(new Runnable() {
            @Override
            public void run() {
                loginByPost(uname,upwd,iphone,email,address);
            }
        }).start();
    }

    public interface RegisterCallback{
        void success(LoginEntity entity);
        void error(String error);
    }

    /** * post的方式请求
     *@return 返回null 登录异常
     */
    public  void loginByPost(String uname,String upwd,String iphone,String email,String address){
        Message msg;
        String path = FinalJAVA.BaseUrl+ "/Register";
        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("POST");

            //数据准备
            String data = "{"
                    + "uname:"+uname+","
                    + "upwd:"+upwd+","
                    + "iphone:"+iphone+","
                    + "email:"+email+","
                    +"address:"+address
                    + "}";
            //至少要设置的两个请求头
            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", data.length()+"");

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
                LoginEntity entity=FinalValue.Companion.JsonToEntityInObject((String) msg.obj);
                callback.success(entity);
            }else if (msg.arg1==0x32){
                callback.error((String) msg.obj);
            }
        }
    };


}
