package com.example.khb.widgettest.view.ui.activity.impl;

import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.example.khb.widgettest.R;
import com.example.khb.widgettest.utils.alipay.PayResult;
import com.example.khb.widgettest.utils.alipay.SignUtils;
import com.example.khb.widgettest.view.ui.base.BaseActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * Created by khb on 2016/6/16.
 */
public class PayDemoActivity extends BaseActivity{

    // 商户PID
    public static final String PARTNER = "2088121076892244";
    // 商户收款账号
    public static final String SELLER = "miuhouse@163.com";
    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAN5u3kvDllGtQZ6QnJ9aAEjXOZekUUFifUgXfQmOcwzhtvc81Kv4H7zG4VU/OELDTwd4FmYUtUSplwPELnysuaCadzLqYI/r1vPoW7a5jVQhJawYivFNiDQQB1RugTVNAXaKdt0stltXbjbXvLHOSHiZnAKTyKVVtaZJZBKczfNBAgMBAAECgYEA3QHx0/vBNuRcJ83uIRMO51EaA0ccCUhvzJqx/TXRsMHwxtVlRn1iI3LEX6xSHVvsATLbpo/6z/9lcjOSMQR55nQkSdE4EpF/Fa/c2Hmp5NFXqhdh5a9E7fwem73xdhqApuK/MGwC1wygDVaMri0o+7criGlu4A/OxVZcIOKvqnECQQD4FdTC2e39qfWXVqXa1CnRSU5R7Fi50JirV7fo0W9HDqeKjPK9l9osVobxbwy8nCGThaujNUIFOu1PzVWpUY7tAkEA5YeHp/HtwbaRTaYD9hYI1NOC2UAZ2SGaoGG5Lgod17hj/2H4w18j5fhz0Fu1XH+mADJzXiNskPBkbBGk3kEXJQJBAOrFnicXncVkReoGMca46SoJuXiFtiGCajgcNwq4yBnyoFLZuA372pgC+okYknEbQBsfcKJvEB9q+JGAvAQUDrECQGYSFymAVJLIdWVqZmiQ8+xxAsdJ0jTU1MZtrsPii9LalN2E2/NF49jo29fUcJppyZBc3yEF78mknYPRdNfBamECQQDD838DLAilNsqQcNJLnMazHVUzAyEgVFSYBP80859X9+ibFFIaTPSP9IY6CGUX0woHg3wvY+zmUIrGknvPrD2l";
    // 支付宝公钥
    public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_CHECK_FLAG = 2;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SDK_PAY_FLAG:
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(PayDemoActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(PayDemoActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(PayDemoActivity.this, "支付失败", Toast.LENGTH_SHORT).show();

                        }
                    }
                    break;
            }
        }
    };

    @Override
    public void init() {

    }

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_pay_demo;
    }

    @Override
    public View getLoadingParentView() {
        return null;
    }

    @Override
    public void initViewsAndEvents() {

    }

    public void pay(View view ){
        if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE)
                || TextUtils.isEmpty(SELLER)){
            new AlertDialog.Builder(this)
                    .setTitle("警告")
                    .setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
                    .setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialoginterface, int i) {
                                    //
                                    finish();
                                }
                            }).show();
            return;
        }
        String orderInfo = getOrderInfo("这是商品","这是商品描述", "0.01");   //订单
        String sign = SignUtils.sign(orderInfo, RSA_PRIVATE);       //对订单做RSA签名,签名逻辑要放在服务端，避免私钥泄露
        try {
            sign = URLEncoder.encode(sign, "UTF-8");        //对签名做url编码
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
                + getSignType();

        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(PayDemoActivity.this);
                String result = alipay.pay(payInfo, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();


    }

    public void h5Pay(View view){

    }

    /**
     * create the order info. 创建订单信息
     *
     */
    private String getOrderInfo(String subject, String body, String price) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm" + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     *
     */
    private String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    /**
     * get the sign type we use. 获取签名方式
     *
     */
    public String getSignType() {
        return "sign_type=\"RSA\"";
    }

}
