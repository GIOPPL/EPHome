package com.gioppl.ephome.net;

import android.app.Activity;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.gioppl.ephome.FinalJAVA;
import com.gioppl.ephome.HomePager.entity.WeatherBean2;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherModel2 {
    private Activity context;

    public WeatherModel2(Activity context, final String phoneNumber, final ResultInterface resultInterface) {
        this.context=context;
        ModelThread.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(FinalJAVA.Weather2)
                        .build();
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    resultInterface.success(jsonStringToArray(response.body().string()));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    public WeatherBean2 jsonStringToArray(String json) {
        Log.i("天气预报",json);
        try {
            WeatherBean2 myBean = JSON.parseObject(json, WeatherBean2.class);
            return myBean;
        } catch (Exception e) {
            e.printStackTrace();
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i(WeatherModel2.class.getName(),"解析出错！");
                }
            });
        }
        return null;
    }

    public interface ResultInterface {
        void success(WeatherBean2 myBean);
    }
}
