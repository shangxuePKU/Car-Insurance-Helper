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

public class InputTwoActivity extends Activity {

    private EditText edit3_001, edit3_002, edit3_003,
            edit3_004, edit3_005, edit3_006,
            edit3_007, edit3_008, edit3_009,
            edit3_010, edit3_011;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_two);

        //设置导航栏
        TopNavigationView topNavigationView = (TopNavigationView) findViewById(R.id.it_navigation);
        topNavigationView.setTitleAndButton(TopNavigationView.INPUT_ACTIVITY);
        topNavigationView.setNavigationClickListner(new TopNavigationView.NavigationClickListner() {
            @Override
            public void next() {
                readyNext();
            }
        });

        edit3_001 = (EditText)this.findViewById(R.id.edit3_001);
        edit3_002 = (EditText)this.findViewById(R.id.edit3_002);
        edit3_003 = (EditText)this.findViewById(R.id.edit3_003);
        edit3_004 = (EditText)this.findViewById(R.id.edit3_004);
        edit3_005 = (EditText)this.findViewById(R.id.edit3_005);
        edit3_006 = (EditText)this.findViewById(R.id.edit3_006);
        edit3_007 = (EditText)this.findViewById(R.id.edit3_007);
        edit3_008 = (EditText)this.findViewById(R.id.edit3_008);
        edit3_009 = (EditText)this.findViewById(R.id.edit3_009);
        edit3_010 = (EditText)this.findViewById(R.id.edit3_010);
        edit3_011 = (EditText)this.findViewById(R.id.edit3_011);
    }

    /**
     * 跳转下一个界面之前的准备
     */
    private void readyNext(){
        //获取数据
        getData();
        //跳到下一个界面
        Intent intent = new Intent(InputTwoActivity.this, InputThreeActivity.class);
        startActivity(intent);
    }

    /**
     * 获取EditText的数据
     */
    private void getData(){
        DataModel.content.put("car1", edit3_001.getText().toString());
        DataModel.content.put("car2", edit3_002.getText().toString());
        DataModel.content.put("car3", edit3_003.getText().toString());
        DataModel.content.put("car4", edit3_004.getText().toString());
        DataModel.content.put("car5", edit3_005.getText().toString());
        DataModel.content.put("car6", edit3_006.getText().toString());
        DataModel.content.put("car7", edit3_007.getText().toString());
        DataModel.content.put("car8", edit3_008.getText().toString());
        DataModel.content.put("car9", edit3_009.getText().toString());
        DataModel.content.put("car10", edit3_010.getText().toString());
        DataModel.content.put("car11", edit3_011.getText().toString());
    }
}
