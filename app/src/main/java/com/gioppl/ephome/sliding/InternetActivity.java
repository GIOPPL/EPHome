package com.gioppl.ephome.sliding;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.gioppl.ephome.FinalJAVA;
import com.gioppl.ephome.FinalValue;
import com.gioppl.ephome.R;
import com.gioppl.ephome.SharedPreferencesUtils;

public class InternetActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText et_interface,picture1,picture2,picture3,picture4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet);
        et_interface= (EditText) findViewById(R.id.et_internet_interface);
        et_interface.setHint(FinalValue.Companion.getBASE_URL());
        picture1= (EditText) findViewById(R.id.et_internet_picture1);
        picture2= (EditText) findViewById(R.id.et_internet_picture2);
        picture3= (EditText) findViewById(R.id.et_internet_picture3);
        picture4= (EditText) findViewById(R.id.et_internet_picture4);
        findViewById(R.id.btn_picture1).setOnClickListener(this);
        findViewById(R.id.btn_picture2).setOnClickListener(this);
        findViewById(R.id.btn_picture3).setOnClickListener(this);
        findViewById(R.id.btn_picture4).setOnClickListener(this);
    }

    public void onDetermine(View view) {
        String add=et_interface.getText().toString();
        if (et_interface==null||add.equals("")){
            add=et_interface.getHint().toString();
        }
        FinalJAVA.BaseUrl="http://"+add+":8080";
        FinalValue.Companion.setBASE_URL(add);
        SharedPreferencesUtils.setParam(InternetActivity.this,"base_url","http://"+add+":8080");
        Toast.makeText(this,"已经修改地址为"+add,Toast.LENGTH_SHORT).show();
    }

    public void onBack(View view) {
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_picture1:
                String add1=picture1.getText().toString();
                if (add1.equals(""))
                    add1=picture1.getHint().toString();
                SharedPreferencesUtils.setParam(InternetActivity.this,"picture1",add1);
                Toast.makeText(this,"已经修改图片1地址："+picture1.getText().toString(),Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_picture2:
                String add2=picture2.getText().toString();
                if (add2.equals(""))
                    add2=picture2.getHint().toString();
                SharedPreferencesUtils.setParam(InternetActivity.this,"picture2",add2);
                Toast.makeText(this,"已经修改图片2地址："+picture2.getText().toString(),Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_picture3:
                String add3=picture3.getText().toString();
                if (add3.equals(""))
                    add3=picture3.getHint().toString();
                SharedPreferencesUtils.setParam(InternetActivity.this,"picture3",add3);
                Toast.makeText(this,"已经修改图片3地址："+picture3.getText().toString(),Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_picture4:
                String add4=picture4.getText().toString();
                if (add4.equals(""))
                    add4=picture4.getHint().toString();
                SharedPreferencesUtils.setParam(InternetActivity.this,"picture4",add4);
                Toast.makeText(this,"已经修改图片4地址："+picture4.getText().toString(),Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
