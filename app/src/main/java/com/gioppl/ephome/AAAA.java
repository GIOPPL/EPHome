package com.gioppl.ephome;

import android.app.Activity;

import com.gioppl.ephome.forum.ForumBean;
import com.gioppl.ephome.forum.request.ForumRequest;

import java.util.ArrayList;

/**
 * Created by GIOPPL on 2017/10/7.
 */

public class AAAA extends Activity{
    private ForumRequest request;
    AAAA(){
        request=new ForumRequest(AAAA.this, new ForumRequest.ForumData() {
            @Override
            public void getData(ArrayList<ForumBean> beanList) {
                ArrayList<ForumBean> beanList2=beanList;
                ArrayList<ForumBean> beanList3=beanList;
                ArrayList<ForumBean> beanList4=beanList;

            }
        });
    }
}
