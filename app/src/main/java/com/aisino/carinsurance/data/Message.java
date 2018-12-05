package com.aisino.carinsurance.data;

/**
 * Created by shangxue on 16/8/26.
 */

public class Message {

    public final static int Message_Connect_Unkown = 0;
    public final static int Message_Error_Unkown = 1;
    public final static int Message_Contract_Send_Loading = 2;
    public final static int Message_Contract_Send_Success = 3;
    public final static int Message_Contract_Get_Loading = 4;
    public final static int Message_App_Not_Found = 5;

    private static String[] messages;

    static {
        messages = new String[256];
        messages[Message_Connect_Unkown] = "服务器无响应";
        messages[Message_Error_Unkown] = "程序异常";
        messages[Message_Contract_Send_Loading] = "生成保单中...";
        messages[Message_Contract_Send_Success] = "生成保单成功，点击\"确定\"后跳转至合同签署";
        messages[Message_Contract_Get_Loading] = "保单加载中...";
        messages[Message_App_Not_Found] = "信手书APP没有发现，请下载安装";
    }

    public static String getMessage(int index){
        if(messages[index] == null)
            return messages[Message_Connect_Unkown];

        return messages[index];
    }
}

