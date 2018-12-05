package com.aisino.carinsurance.custom;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aisino.carinsurance.R;
import com.aisino.carinsurance.data.DataModel;


/**
 * Created by HXQ on 2017/5/22.
 */

public class TopNavigationView extends RelativeLayout {

    //当前导航所属的activity
    public static final int REVIEW_ACTIVITY = 1;
    public static final int INPUT_ACTIVITY = 2;
    public static final int INPUTEIGHT_ACTIVITY = 3;

    private Button backBtn;
    private TextView titleTv;
    private Button nextBtn;

    public TopNavigationView(Context context) {
        super(context);
        init(context);
    }

    public TopNavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TopNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TopNavigationView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    /**
     * 初始化布局
     * @param context
     */
    private void init(Context context){
        LayoutInflater.from(context).inflate(R.layout.top_navigation, this);
        backBtn = (Button)findViewById(R.id.tn_back);
        titleTv = (TextView)findViewById(R.id.tn_title);
        nextBtn = (Button)findViewById(R.id.tn_next);

        final Context newContext = context;
        //监听返回按钮点击事件
        backBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                DataModel.back();
            }
        });
        //监听下一步按钮点击事件
        nextBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (navigationClickListner != null){
                    navigationClickListner.next();
                }
            }
        });
    }

    /**
     * 根据传入的activity，设置导航项
     * @param activity
     */
    public void setTitleAndButton(int activity){
        switch (activity){
            case REVIEW_ACTIVITY:
                backBtn.setVisibility(View.VISIBLE);
                titleTv.setText("保单查看");
                break;
            case INPUT_ACTIVITY:
                backBtn.setVisibility(View.VISIBLE);
                titleTv.setText("保单填写");
                nextBtn.setVisibility(View.VISIBLE);
                break;
            case INPUTEIGHT_ACTIVITY:
                backBtn.setVisibility(View.VISIBLE);
                titleTv.setText("生成保单");
                break;
        }
    }

    /**
     * 显示下一步按钮
     */
    public void showNextBtn(){
        nextBtn.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏下一步按钮
     */
    public void hiddenNextBtn(){
        nextBtn.setVisibility(View.GONE);
    }
    //回调
    public interface NavigationClickListner{
        void next();
    }

    NavigationClickListner navigationClickListner;

    public  void setNavigationClickListner(NavigationClickListner navigationClickListner) {
        this.navigationClickListner = navigationClickListner;
    }
}
