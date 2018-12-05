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

public class InputOneActivity extends Activity {

    private EditText edit2_001, edit2_002, edit2_003, edit2_004;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_one);

        //设置导航栏
        TopNavigationView topNavigationView = (TopNavigationView)findViewById(R.id.io_navigation);
        topNavigationView.setTitleAndButton(TopNavigationView.INPUT_ACTIVITY);
        topNavigationView.setNavigationClickListner(new TopNavigationView.NavigationClickListner() {
            @Override
            public void next() {
                readyNext();
            }
        });

        edit2_001 = (EditText)this.findViewById(R.id.edit2_001);
        edit2_002 = (EditText)this.findViewById(R.id.edit2_002);
        edit2_003 = (EditText)this.findViewById(R.id.edit2_003);
        edit2_004 = (EditText)this.findViewById(R.id.edit2_004);
    }

    /**
     * 跳转下一个界面之前的准备
     */
    private void readyNext(){
        //获取数据
        getData();
        //调到下一个界面
        Intent intent = new Intent(InputOneActivity.this, InputTwoActivity.class);
        startActivity(intent);
    }

    /**
     * 获取EditText的数据
     */
    private void getData(){
        DataModel.content.put("name", edit2_001.getText().toString());
        DataModel.content.put("idCard", edit2_002.getText().toString());
        DataModel.content.put("address", edit2_003.getText().toString());
        DataModel.content.put("phone", edit2_004.getText().toString());

    }
}
