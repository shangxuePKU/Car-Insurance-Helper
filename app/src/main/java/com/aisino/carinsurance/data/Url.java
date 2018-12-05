package com.aisino.carinsurance.data;

/**
 * Created by HXQ on 2017/4/24.
 */

public class Url {

    //public static final String BASE_URL = "http://192.168.17.216:8080/car_Insurance/mobile/";
    public static final String BASE_URL = "http://192.168.3.117:9000/car_Insurance/mobile/";
    //合同
    public final static int Url_Contract_GetContractByCode = 0;
    public final static int Url_Contract_GetImageByCode = 1;
    public final static int Url_Contract_SendEventContract = 2;

    private static String[] urls;

    static {
        urls = new String[256];
        urls[Url_Contract_GetContractByCode] = "getContractByCode";
        urls[Url_Contract_GetImageByCode] = "getImageByCode";
        urls[Url_Contract_SendEventContract] = "sendEventContract";
    }

    public static String getUrl(int index){
        if(urls[index] == null)
            return BASE_URL;
        String url = BASE_URL + urls[index];
        return url;
    }
}
