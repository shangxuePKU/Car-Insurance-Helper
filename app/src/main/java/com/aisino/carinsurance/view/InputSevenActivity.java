package com.aisino.carinsurance.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.aisino.carinsurance.R;
import com.aisino.carinsurance.custom.TopNavigationView;
import com.aisino.carinsurance.data.DataModel;

/**
 * Created by HXQ on 2017/5/31.
 */

public class InputSevenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_seven);

        //设置导航栏
        TopNavigationView topNavigationView = (TopNavigationView) findViewById(R.id.ise_navigation);
        topNavigationView.setTitleAndButton(TopNavigationView.INPUT_ACTIVITY);
        topNavigationView.setNavigationClickListner(new TopNavigationView.NavigationClickListner() {
            @Override
            public void next() {
                readyNext();
            }
        });
    }
    /**
     * 跳转下一个界面之前的准备
     */
    private void readyNext(){
        //获取数据
        getData();
        //跳到下一个界面
        Intent intent = new Intent(InputSevenActivity.this, InputEightActivity.class);
        startActivity(intent);
    }

    /**
     * 获取EditText的数据
     */
    private void getData(){
        DataModel.content.put("info1", "");
    }
}
