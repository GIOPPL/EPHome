package com.gioppl.ephome.net;

import android.app.Activity;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.gioppl.ephome.FinalJAVA;
import com.gioppl.ephome.HomePager.entity.WeatherBean;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherModel {
    private Activity context;

    public WeatherModel(Activity context, final String phoneNumber, final ResultInterface resultInterface) {
        this.context=context;
        ModelThread.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(FinalJAVA.Weather)
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

    public WeatherBean jsonStringToArray(String json) {
        Log.i("天气预报",json);
        try {
            WeatherBean result = JSON.parseObject(json, WeatherBean.class);
//            WeatherBean result = JSON.parseObject(json, new TypeReference<WeatherBean>() {});
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i(WeatherModel.class.getName(),"解析出错！");
                }
            });
        }
        return null;
    }

    public interface ResultInterface {
        void success(WeatherBean myBean);
    }
}
