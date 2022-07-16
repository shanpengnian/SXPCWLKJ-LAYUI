package com.sxpcwlkj.wxpay;

import com.sxpcwlkj.entity.WxPayConfig;

/**
 * https://www.sxpcwlkj.com/
 * 微信支付配置类
 */
public class MyWXConfig extends WXPayConfig {


    private  String appID = "wxbb4fa1c0b01c822f";                                // 公众账号ID  注意 App支付时用的是 微信商户关联时的APPID  而非公众号ID
    private  String mchID = "1502764891";                                        // 微信商户号
    private  String key = "o31H520SOav4vw3Lv8LCwxY6gLqODbKU";                    // 微信商户API密钥
    private  String notiey_URL = "https://www.sxpcwlkj.com/";                         // 成功回调  这个一般在H5和公众号支付时使用


    public MyWXConfig(WxPayConfig iswxPayConfig) {
        this.appID=iswxPayConfig.getAppId();
        this.mchID=iswxPayConfig.getMchId();
        this.key=iswxPayConfig.getWxKey();
        this.notiey_URL=iswxPayConfig.getNotieyUrl();
    }

    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    public int getHttpReadTimeoutMs() {
        return 10000;
    }

    IWXPayDomain getWXPayDomain() {
        return WXPayDomainSimpleImpl.instance();
    }

    public String getPrimaryDomain() {
        return "api.mch.weixin.qq.com";
    }

    public String getAlternateDomain() {
        return "api2.mch.weixin.qq.com";
    }

    @Override
    IWXPayDomain getCertStream() {
        // TODO Auto-generated method stub
        return WXPayDomainSimpleImpl.instance();
    }

    @Override
    public String getAppID() {
        // TODO Auto-generated method stub
        return appID;
    }

    @Override
    public String getMchID() {
        // TODO Auto-generated method stub
        return mchID;
    }

    @Override
    public String getKey() {
        // TODO Auto-generated method stub
        return key;
    }

    public String getNotiey_URL() {
        return notiey_URL;
    }

    public void setNotiey_URL(String notiey_URL) {
        this.notiey_URL = notiey_URL;
    }


}
