package com.aisino.carinsurance.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.aisino.carinsurance.R;
import com.aisino.carinsurance.data.DataModel;
import com.aisino.carinsurance.model.Contract;

public class MainActivity extends Activity {

    //启动应用以后，默认为true
    private static boolean isAppStart = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("MainActivity","onCreate");
        //应用刚刚被启动
        if (isAppStart){
            DataModel.contractCache = new Contract(MainActivity.this);
            isAppStart = false;
        }
        //判断是否第三方应用打开
        if (getIntent().getStringExtra("code") != null) {//第三方打开本应用
            fromOther();
        }else {//正常启动本应用
            setContentView(R.layout.activity_main);
        }
    }

    private void fromOther(){
        String action = getIntent().getStringExtra("action");
        if ("next".equals(action)){
            //获取信手书传过来的code；
            DataModel.contractCache.setCode(getIntent().getStringExtra("code"));
            //跳转到保单展示
            Intent intent = new Intent(MainActivity.this, ReviewActivity.class);
            startActivity(intent);
        }else {
            finish();
        }
    }

    /**
     * 去保单填写页面的按钮点击响应
     */
    public void toInputBtnClick(View v){
        Intent intent = new Intent(MainActivity.this, InputOneActivity.class);
        startActivity(intent);
    }
}
