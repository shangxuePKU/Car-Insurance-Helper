package com.aisino.carinsurance.view;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.aisino.carinsurance.R;
import com.aisino.carinsurance.custom.TopNavigationView;
import com.aisino.carinsurance.data.DataModel;
import com.aisino.carinsurance.data.Message;
import com.aisino.carinsurance.data.Url;
import com.aisino.carinsurance.model.Contract;
import com.aisino.carinsurance.model.Response;
import com.aisino.carinsurance.util.CharUtil;
import com.aisino.carinsurance.util.DialogUtil;
import com.aisino.carinsurance.util.GsonUtil;
import com.aisino.carinsurance.util.HttpUtil;
import com.aisino.carinsurance.util.PackageUtil;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by HXQ on 2017/5/31.
 */

public class InputEightActivity extends Activity {

    private EditText edit9_002, edit9_003, edit9_004, edit9_005, edit9_006;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_eight);
        //设置导航栏
        TopNavigationView topNavigationView = (TopNavigationView) findViewById(R.id.ie_navigation);
        topNavigationView.setTitleAndButton(TopNavigationView.INPUTEIGHT_ACTIVITY);

        edit9_002 = (EditText)this.findViewById(R.id.edit9_002);
        edit9_003 = (EditText)this.findViewById(R.id.edit9_003);
        edit9_004 = (EditText)this.findViewById(R.id.edit9_004);
        edit9_005 = (EditText)this.findViewById(R.id.edit9_005);
        edit9_006 = (EditText)this.findViewById(R.id.edit9_006);
    }

    /**
     * 跳转下一个界面之前的准备
     */
    private void readyNext(){

    }

    /**
     * 获取EditText的数据
     */
    private void getData(){
        DataModel.content.put("info2", edit9_002.getText().toString());
        DataModel.content.put("info3", edit9_003.getText().toString());
        DataModel.content.put("info4", edit9_004.getText().toString());
        DataModel.content.put("info5", edit9_005.getText().toString());
        DataModel.content.put("info6", edit9_006.getText().toString());
    }

    /**
     * 封装参数，发起生成保单请求
     */
    private void sendContract(){
        // 使用Map封装请求参数
        Map<String, Object> map = new HashMap<String, Object>();
        //因为接口有中午乱码问题，去掉中文
        Map<String, String> content = new HashMap<>();
        for (Map.Entry<String, String> entry : DataModel.content.entrySet()) {
            if (CharUtil.isChinese(entry.getValue())) {
                content.put(entry.getKey(), "");
            }else {
                content.put(entry.getKey(), entry.getValue());
            }
        }
        map.put("content", DataModel.content);
        String json = GsonUtil.GsonString(map);
        // 定义发送请求的URL
        String url = Url.getUrl(Url.Url_Contract_SendEventContract);
        // 发送请求
        HttpUtil.postRequestTest(url, json, new HttpUtil.PostResposeListner() {
            @Override
            public void back(boolean status, String result) {
                //判断请求是否成功
                if (status) {//网络请求成功
                    //解析json获取数据
                    Type type = new TypeToken<Response<Contract>>(){}.getType();
                    Response response = GsonUtil.GsonToBean(result, type);
                    //对请求合同图片操作返回状态值进行判断
                    if (response.getStatus() == 0) {//操作成功
                        //取图片数据
                        String code = ((Contract)response.getDatas()).getCode();
                        long contractId = ((Contract)response.getDatas()).getContractId();
                        DataModel.contractCache.setCode(code);
                        DataModel.contractCache.setContractId(contractId);
                        DialogUtil.hiddenWaitingDialog();
                        //toSignActivity();
                        DialogUtil.showSubmitDialog(InputEightActivity.this, Message.getMessage(Message.Message_Contract_Send_Success), new DialogUtil.ButtonClickListner() {
                            @Override
                            public void onClick() {
                                toSignActivity();
                            }
                        });
                    } else {//操作失败
                        DialogUtil.hiddenWaitingDialog();
                        String message = response.getMessage();
                        DialogUtil.showSubmitDialog(InputEightActivity.this, message, null);
                    }
                }else {//网络请求失败
                    DialogUtil.hiddenWaitingDialog();
                    DialogUtil.showSubmitDialog(InputEightActivity.this, result, null);
                }
            }
        });
    }

    /**
     * 进入签署合同界面
     */
    private void toSignActivity(){
        //判断手机上是否存在指定包名的应用
        if (PackageUtil.isPkgInstalled(InputEightActivity.this, DataModel.signPackageName)){//有
//            PackageManager packageManager = getPackageManager();
//            //跳转到第三方应用签署
//            final Intent intent = packageManager.getLaunchIntentForPackage(DataModel.signPackageName);
////            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
////                    Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED |
////                    Intent.FLAG_ACTIVITY_CLEAR_TOP) ;
//            Bundle bundle = new Bundle();
//            bundle.putString("code", DataModel.contractCache.getCode());
//            bundle.putLong("contractId", DataModel.contractCache.getContractId());
//            bundle.putString("backUrl", DataModel.packageName);
//            intent.putExtras(bundle);
//            startActivity(intent);
            //延迟两秒跳转
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    PackageManager packageManager = getPackageManager();
                    //跳转到第三方应用签署
                    Intent intent = packageManager.getLaunchIntentForPackage(DataModel.signPackageName);
                    Bundle bundle = new Bundle();
                    // bundle.putString("code", "123456");
                    bundle.putString("code", DataModel.contractCache.getCode());
                    bundle.putLong("contractId", DataModel.contractCache.getContractId());
                    bundle.putString("backUrl", DataModel.packageName);
                    bundle.putInt("insurance",1);
                    intent.putExtras(bundle);

                    //intent.setFlags(PendingIntent.FLAG_ONE_SHOT);
                    startActivity(intent);
                }
            }, 1000);
            //startActivity(intent);
        }else {//没有，提示
            DialogUtil.showSubmitDialog(InputEightActivity.this, Message.getMessage(Message.Message_App_Not_Found), new DialogUtil.ButtonClickListner() {
                @Override
                public void onClick() {
                }
            });
        }
    }

    /**
     * 生成保单的按钮点击响应
     */
    public void buildContractBtnClick(View v){
        //获取数据
        getData();
        //显示等待动画
        DialogUtil.showWaitingDialog(InputEightActivity.this,
                Message.getMessage(Message.Message_Contract_Send_Loading));
        //生成保单
        sendContract();
    }
}
