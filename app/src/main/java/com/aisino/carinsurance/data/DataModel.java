package com.aisino.carinsurance.data;

import android.app.Instrumentation;
import android.view.KeyEvent;

import com.aisino.carinsurance.model.Contract;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by HXQ on 2017/5/31.
 */

public class DataModel {

    public static Contract contractCache ;
    public static Map<String, String> content = new HashMap<>();
    public static String signPackageName = "com.aisino.trusthandwrite";
    public static String packageName = "com.aisino.carinsurance";

    /**
     * 实现类似系统back键返回效果
     */
    public static void back(){
        new Thread(){
            public void run() {
                try{
                    Instrumentation inst = new Instrumentation();
                    inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
                } catch (Exception e) {

                }
            }
        }.start();
    }
}
