package com.aisino.carinsurance.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.aisino.carinsurance.R;
import com.aisino.carinsurance.custom.TopNavigationView;
import com.aisino.carinsurance.data.DataModel;

/**
 * Created by HXQ on 2017/5/31.
 */

public class InputFourActivity extends Activity{

    private EditText edit5_001, edit5_002, edit5_003,
               edit5_004, edit5_005, edit5_006;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_four);

        //设置导航栏
        TopNavigationView topNavigationView = (TopNavigationView) findViewById(R.id.if_navigation);
        topNavigationView.setTitleAndButton(TopNavigationView.INPUT_ACTIVITY);
        topNavigationView.setNavigationClickListner(new TopNavigationView.NavigationClickListner() {
            @Override
            public void next() {
                readyNext();
            }
        });

        edit5_001 = (EditText)this.findViewById(R.id.edit5_001);
        edit5_002 = (EditText)this.findViewById(R.id.edit5_002);
        edit5_003 = (EditText)this.findViewById(R.id.edit5_003);
        edit5_004 = (EditText)this.findViewById(R.id.edit5_004);
        edit5_005 = (EditText)this.findViewById(R.id.edit5_005);
        edit5_006 = (EditText)this.findViewById(R.id.edit5_006);
    }

    /**
     * 跳转下一个界面之前的准备
     */
    private void readyNext(){
        //获取数据
        getData();
        //调到下一个界面
        Intent intent = new Intent(InputFourActivity.this, InputFiveActivity.class);
        startActivity(intent);
    }

    /**
     * 获取EditText的数据
     */
    private void getData(){
        DataModel.content.put("cast1", edit5_001.getText().toString());
        DataModel.content.put("cast2", edit5_002.getText().toString());
        DataModel.content.put("cast3", edit5_003.getText().toString());
        DataModel.content.put("cast4", edit5_004.getText().toString());
        DataModel.content.put("cast5", edit5_005.getText().toString());
        DataModel.content.put("cast6", edit5_006.getText().toString());
    }
}
