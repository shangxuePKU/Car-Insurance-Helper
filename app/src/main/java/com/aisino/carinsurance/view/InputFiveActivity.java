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

public class InputFiveActivity extends Activity{

    private EditText edit6_001, edit6_002, edit6_003,
            edit6_004, edit6_005, edit6_006,
            edit6_007, edit6_008;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_five);

        //设置导航栏
        TopNavigationView topNavigationView = (TopNavigationView) findViewById(R.id.ifi_navigation);
        topNavigationView.setTitleAndButton(TopNavigationView.INPUT_ACTIVITY);
        topNavigationView.setNavigationClickListner(new TopNavigationView.NavigationClickListner() {
            @Override
            public void next() {
                readyNext();
            }
        });

        edit6_001 = (EditText)this.findViewById(R.id.edit6_001);
        edit6_002 = (EditText)this.findViewById(R.id.edit6_002);
        edit6_003 = (EditText)this.findViewById(R.id.edit6_003);
        edit6_004 = (EditText)this.findViewById(R.id.edit6_004);
        edit6_005 = (EditText)this.findViewById(R.id.edit6_005);
        edit6_006 = (EditText)this.findViewById(R.id.edit6_006);
        edit6_007 = (EditText)this.findViewById(R.id.edit6_007);
        edit6_008 = (EditText)this.findViewById(R.id.edit6_008);
    }

    /**
     * 跳转下一个界面之前的准备
     */
    private void readyNext(){
        //获取数据
        getData();
        //调到下一个界面
        Intent intent = new Intent(InputFiveActivity.this, InputSixActivity.class);
        startActivity(intent);
    }

    /**
     * 获取EditText的数据
     */
    private void getData(){
        DataModel.content.put("tax1", edit6_001.getText().toString());
        DataModel.content.put("tax2", edit6_002.getText().toString());
        DataModel.content.put("tax3", edit6_003.getText().toString());
        DataModel.content.put("tax4", edit6_004.getText().toString());
        DataModel.content.put("tax5", edit6_005.getText().toString());
        DataModel.content.put("tax6", edit6_006.getText().toString());
        DataModel.content.put("tax7", edit6_007.getText().toString());
        DataModel.content.put("tax8", edit6_008.getText().toString());
    }
}
