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

public class InputThreeActivity extends Activity {

    private EditText edit4_001, edit4_002, edit4_003,
            edit4_004, edit4_005, edit4_006;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_three);

        //设置导航栏
        TopNavigationView topNavigationView = (TopNavigationView) findViewById(R.id.ith_navigation);
        topNavigationView.setTitleAndButton(TopNavigationView.INPUT_ACTIVITY);
        topNavigationView.setNavigationClickListner(new TopNavigationView.NavigationClickListner() {
            @Override
            public void next() {
                readyNext();
            }
        });

        edit4_001 = (EditText)this.findViewById(R.id.edit4_001);
        edit4_002 = (EditText)this.findViewById(R.id.edit4_002);
        edit4_003 = (EditText)this.findViewById(R.id.edit4_003);
        edit4_004 = (EditText)this.findViewById(R.id.edit4_004);
        edit4_005 = (EditText)this.findViewById(R.id.edit4_005);
        edit4_006 = (EditText)this.findViewById(R.id.edit4_006);
    }

    /**
     * 跳转下一个界面之前的准备
     */
    private void readyNext(){
        //获取数据
        getData();
        //调到下一个界面
        Intent intent = new Intent(InputThreeActivity.this, InputFourActivity.class);
        startActivity(intent);
    }

    /**
     * 获取EditText的数据
     */
    private void getData(){
        DataModel.content.put("limit1", edit4_001.getText().toString());
        DataModel.content.put("limit2", edit4_002.getText().toString());
        DataModel.content.put("limit3", edit4_003.getText().toString());
        DataModel.content.put("limit4", edit4_004.getText().toString());
        DataModel.content.put("limit5", edit4_005.getText().toString());
        DataModel.content.put("limit6", edit4_006.getText().toString());
    }
}
