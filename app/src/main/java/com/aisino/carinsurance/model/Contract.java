package com.aisino.carinsurance.model;

import android.content.Context;

import com.aisino.carinsurance.data.ImageMemoryCache;


/**
 * Created by HXQ on 2017/5/3.
 */

public class Contract {
    private long contractId;//合同ID
    public  String code;
    private int pages;//合同总页数
    private int page;//当前显示的合同的页数
    private ImageMemoryCache images;//合同的图片



    public long getContractId() {
        return contractId;
    }

    public void setContractId(long contractId) {
        this.contractId = contractId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public ImageMemoryCache getImages() {
        return images;
    }

    public void setImages(ImageMemoryCache images) {
        this.images = images;
    }


    public Contract() {
    }

    public Contract(Context context) {
        contractId = -1;
        pages = 0;
        page = 1;
        images = new ImageMemoryCache(context);
    }
}
