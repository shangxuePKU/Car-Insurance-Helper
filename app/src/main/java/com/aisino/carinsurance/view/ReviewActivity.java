package com.aisino.carinsurance.view;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.aisino.carinsurance.R;
import com.aisino.carinsurance.custom.BaseDragZoomImageView;
import com.aisino.carinsurance.custom.TopNavigationView;
import com.aisino.carinsurance.data.DataModel;
import com.aisino.carinsurance.data.Message;
import com.aisino.carinsurance.data.Url;
import com.aisino.carinsurance.model.Contract;
import com.aisino.carinsurance.model.Response;
import com.aisino.carinsurance.util.Base64Util;
import com.aisino.carinsurance.util.DialogUtil;
import com.aisino.carinsurance.util.GsonUtil;
import com.aisino.carinsurance.util.HttpUtil;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by HXQ on 2017/5/31.
 */

public class ReviewActivity extends Activity {

    /** ContractImageView默认的宽高，就是父控件的宽高 */
    private int imageDefalutWidth;
    private int imageDefalutHeight;
    /** 滑动进度条值的倍数值 */
    private int seekBarScale;
    /** 界面控件*/
    private BaseDragZoomImageView contractImageView;
    private TextView pageTextView;
    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        //设置默认值
        setDefalut();
        //查询合同数据
        setDefalutData();
    }

    /**
     * 设置默认值
     */
    private void setDefalut(){
        //设置导航栏
        TopNavigationView topNavigationView = (TopNavigationView)findViewById(R.id.review_navigation);
        topNavigationView.setTitleAndButton(TopNavigationView.REVIEW_ACTIVITY);

        contractImageView = (BaseDragZoomImageView)this.findViewById(R.id.mid_Iv);
        pageTextView = (TextView)this.findViewById(R.id.mid_Tv);
        seekBar = (SeekBar)this.findViewById(R.id.mid_SeekBar);
        //设置观察者，在contractImageView绘制前获取控件真实宽和高
        ViewTreeObserver vto = contractImageView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                contractImageView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                imageDefalutWidth = contractImageView.getWidth();
                imageDefalutHeight = contractImageView.getHeight();
            }
        });
    }

    /**
     * 设置默认数据
     */
    private void setDefalutData(){
        //显示等待动画
        DialogUtil.showWaitingDialog(ReviewActivity.this,
                Message.getMessage(Message.Message_Contract_Get_Loading));
        //请求合同数据
        getContract();
    }

    /**
     * 封装参数，发起请求保单请求
     */
    private void getContract(){
        // 使用Map封装请求参数
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", DataModel.contractCache.getCode());
        String json = GsonUtil.GsonString(map);
        // 定义发送请求的URL
        String url = Url.getUrl(Url.Url_Contract_GetContractByCode);
        // 发送请求
        HttpUtil.postRequestTest(url, json, new HttpUtil.PostResposeListner() {
            @Override
            public void back(boolean status, String result) {
                //判断请求是否成功
                if (status) {//网络请求成功
                    //解析json获取数据
                    Type type = new TypeToken<Response<Contract>>(){}.getType();
                    Response response = GsonUtil.GsonToBean(result, type);
                    //对请求合同操作返回状态值进行判断
                    if (response.getStatus() == 0) {//操作成功
                        Contract contract = (Contract)response.getDatas();
                        DataModel.contractCache.setPages(contract.getPages());
                        //设置页码信息和滑动进度条
                        setTextViewValue();
                        setSeekBarValue();
                        //请求图片
                        for (int i = 1; i <= contract.getPages(); i++){
                            getContractImage(i);
                        }
                    } else {//操作失败
                        DialogUtil.hiddenWaitingDialog();
                        String message = response.getMessage();
                        DialogUtil.showSubmitDialog(ReviewActivity.this, message, null);
                    }
                }else {//网络请求失败
                    DialogUtil.hiddenWaitingDialog();
                    DialogUtil.showSubmitDialog(ReviewActivity.this, result, null);
                }
            }
        });
    }

    /**
     * 获取合同图片
     * @param page 请求的图片所在页码
     */
    private void getContractImage(final int page){
        // 使用Map封装请求参数
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code",DataModel.contractCache.getCode());
        map.put("id", page);
        String json = GsonUtil.GsonString(map);
        // 定义发送请求的URL
        String url = Url.getUrl(Url.Url_Contract_GetImageByCode);
        // 发送请求
        HttpUtil.postRequestTest(url, json, new HttpUtil.PostResposeListner() {
            @Override
            public void back(boolean status, String result) {
                //判断请求是否成功
                if (status){//网络请求成功
                    //解析json获取数据
                    Type type = new TypeToken<Response<Map<String, String>>>(){}.getType();
                    Response response = GsonUtil.GsonToBean(result, type);
                    //对请求合同图片操作返回状态值进行判断
                    if (response.getStatus() == 0) {//操作成功
                        //取图片数据
                        String base64Data = ((Map<String, String>)response.getDatas()).get("image");
                        Bitmap bitmap = Base64Util.base64ToBitmap(base64Data);
                        //图片放到缓存中
                        DataModel.contractCache.getImages().addBitmapToCache(page, bitmap);
                        //请求到第一张图片则隐藏等待框
                        if (page == 1){
                            DialogUtil.hiddenWaitingDialog();
                        }
                        //DataModel.contractCache.getPage()默认值为1，但是也可能因为用户滑动SeekBar改变
                        if (page == DataModel.contractCache.getPage()){
                            setContractImageViewValue();
                        }
                    }else {//操作失败
                        DialogUtil.hiddenWaitingDialog();
                        String message = response.getMessage();
                        DialogUtil.showSubmitDialog(ReviewActivity.this, message, null);
                    }
                }else {//网络请求失败
                    DialogUtil.hiddenWaitingDialog();
                    DialogUtil.showSubmitDialog(ReviewActivity.this, result, null);
                }
            }
        });
    }

    /**
     * 设置当前页数与总页数的文本信息
     */
    private void setTextViewValue(){
        pageTextView.setText("当前浏览页数" + DataModel.contractCache.getPage() + "/" + DataModel.contractCache.getPages());
    }

    /**
     * 设置滑动进度条最大值，和监听滑动事件,因为seekBar设置最大值太小，滑动体验很差，如果合同页数小于30，则设置最大值为总页数*倍数>50
     */
    private void setSeekBarValue(){
        //seekBar最小值为0，而page最小值为1
        int max = 0, pages;
        pages = DataModel.contractCache.getPages();
        //因为seekBar设置最大值太小，滑动体验很差，如果合同页数小于30，则设置最大值为总页数*倍数>=50
        if (0 < pages && pages < 30){
            max = (100 % pages == 0)? 100 : ((100 / pages + 1) * pages);
            seekBarScale = max / pages;
        }else {
            max = pages;
            seekBarScale = 1;
        }
        seekBar.setMax(max - 1);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int page = (i/seekBarScale) + 1;
                if (page != DataModel.contractCache.getPage()){
                    DataModel.contractCache.setPage(page);
                    pageTextView.setText("当前浏览页数" + DataModel.contractCache.getPage() + "/" + DataModel.contractCache.getPages());
                    setContractImageViewValue();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    /**
     * 设置当前显示的合同图片
     */
    private void setContractImageViewValue(){
        Bitmap bitmap = DataModel.contractCache.getImages().getBitmapFromCache(DataModel.contractCache.getPage());
        if (bitmap != null){
            //改变bitmap大小适应控件大小
            Bitmap zoomBitmap = adaptContractImageView(bitmap);
            //改变去签署页面的按钮的位置
            contractImageView.setImageBitmap(zoomBitmap);
        }else {
            contractImageView.setImageBitmap(null);
        }
    }

    /**
     *  处理图片,改变bitmap大小适应ContractImageView大小
     * @param bm 所要转换的bitmap
     * @return 指定宽高的bitmap
     */
    private Bitmap adaptContractImageView(Bitmap bm){
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        if (width == imageDefalutWidth && height == imageDefalutHeight){
            return bm;
        }else {
            // 计算缩放比例
            float scaleWidth = ((float) imageDefalutWidth) / width;
            float scaleHeight = ((float) imageDefalutHeight) / height;
            float scale = (scaleWidth > scaleHeight)? scaleHeight: scaleWidth;
            // 取得想要缩放的matrix参数
            Matrix matrix = new Matrix();
            matrix.postScale(scale, scale);
            // 得到新的图片
            Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
            //根据图片大小改变imageView大小
            int newImageWidth = (int)(width * scale + 0.5f);
            int newImageHeight = (int)(height * scale + 0.5f);
            //获取imageView布局
            ViewGroup.LayoutParams layoutParams  = contractImageView.getLayoutParams();
            layoutParams.width = newImageWidth;
            layoutParams.height = newImageHeight;
            //使设置好的布局参数应用到控件
            contractImageView.setLayoutParams(layoutParams );
            return newbm;
        }

    }
}
