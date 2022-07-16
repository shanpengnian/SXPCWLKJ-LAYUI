package com.sxpcwlkj.utils;

public class AlipayConfig {
    //支付应用ID
    public static String app_id = "2018062360383926";//在后台获取（必须配置）
    //商户私钥  ==>用支付宝工具生生成 应用 公钥和 私钥（私钥填在这里，公钥设置在应用获取 支付公钥）
    public static String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCuvXuiHvL3bKwbnRGXgz06vkg4v9L7lJX06PFRYSGm+GPstgq1kqUkNo/6zEErqQR3WUKtf52kosKakTkAFYCE6oFdhQKv1ij2STdfB5G/GVPB+uZAafXP4L3HIVYSVs65nznXcYHj32YmscfSoihFxBOemZNzhFxCLzBiTlShaLUdV7/2M8KIRpRV4LkuAxfTw++TnOM6Ipbvzt8/PfPYM91miizQIqmGIvsokzPnvf4fLSoVOECkGp63P5tIpk/89tCHgnbOTtaxcrQj6K4NglDEZNAlqwv2HVtfRgMOKyhl6A++L3Wpf/BWe5Ango5j3HN8Yk/LVaJJjx4uLzv1AgMBAAECggEAUNuReCWGHIQG6Ag4ebna/XHLjacGBDBva5LXB+dAYTkTVBewwPCIxkcRPOaDSaa+UFPXjN0+n05JaqjEjYtlmxvAnPvNkHwzHDQpESo5gQpBPcPSBACEJJtEdf6xC29r3W0WoOD/T+iyJjmh05ABvrbKtVsvZgbbgiy/4N2KP92lES+Gzli/YT7oCHi3W4IvbzGohoxzUBbDWDCDcs459UhhFtb8hiOmiLOtdZd1vCzYwDBBO/o1fgnXGTBbdcWkkQB5lXS9IkdMe1cg+9f8Y2Ry0zdxmHjyOgpbu6jE651ZoYnIcSYnyKXhvHRd4YkTXYQf1EXYZ2VaKu4PrHAywQKBgQDq1Jg3EK3ANG+dza4qrXkfkIofaOw8IfsDxU2m3gEsd2+oxT76kRqxAdDZ5c4faVqQUMuVvTlETv9tEqoD8cBymiPRlnb/M4BaFUDtvpVvUv2VP6OjGv2tXc127Gcjb26Ij/PUZMWRuZg8SLVDr3+4y2TU8LN1aRq3RbrX8M6enQKBgQC+fiC7PkyTIoErLdlhik0hswATMPqvzTFJmQ6jEg7VSzzMleFLFrq9xvlSqtAx4/PsqGcA/DTulmHf0Xborn7bhgAAZOUE6JV3PGKzJAeW5d4Tmd+KvoM0zPuUXxzuaBEUm/+6vfcGrQzpIbrMZMXlMM8HeKzfPFACQsA+7NQnOQKBgQCaOwS57b9gJFBGgUbpisOpgoHk/UFigSLeqCBG/zogHVV9sAacBN9V8A5efjsxkD1F2XrMntnUzlmeJor5SjcOTcRGrB79n7Kl952MSAbXdddMxd5QirKfwaLnf36B3HbpRDptfb3w5sdgmJRMnyAwm41e+bOz4lMEmcyMdQEPVQKBgG3tJQBIjDHgGIANXkNuZuJKhIGOoytuEsIw5ARweETXvQcmerM1M6AuQVRWI/yWWf24lHA6GmWQPHzHSIIvqB8QLdIMZyi0+wOqwh40bXjDv6q6AgjztY1zuL4/QNfHEAw/lYxy8SlWFXw3La+je5ut1dqu5buQ927GjGADlM3pAoGBAK4jSaKbETya5X1crUwLz1JGas3gFOqg1lvn8iD5CUUoub8Q7z5SL1UM1uRPE0s1AIkCyqfmUrvd3lU8PG/WOHlGQku2wN9OaqfhPUzDTfBG42J81LGRbnVRNemVHa8mZ1niZW9oqQHsFfmyBfWHaQhbS02li3sJyhaz3x/8SxVt";//教程查看获取方式（必须配置）
    //支付宝公钥  用应用换取的
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzmnPaVtRCilghVzkAEaIMYY+wiipUECi0QDViRNNESsUH3e0PLkl4ZwjIPDsyw5cM8vNj+OzYNYqfIcCbgNWafBu3hCM7l6Jz7mBTbd6EZzjgz5r3gF1XQ5GfzpxdwkAyxOgH7/E//d608YY7BqPNjDIX3KgEz3tJdQdEdwanWMujRpQsrItFA2hN5cAkP/wfMfVoaoKLb7WYSldGR8mZSci2NHyGGP/ASyB1RClR5BBNqo1UGM79UL1UnrJcmj3Ln900ODK8M79QAD2TZ4A4DEXXxAv3lQIcs25OAZEpo6AlZ6VajY+99xzRGWB+8QoOMidasC9LOHPzehXf0R0qwIDAQAB";//教程查看获取方式（必须配置）
    //支付宝异步 通知页面 接口   需要 http:// 完整的链接接口 不能加?id=123等参数
    public static String notify_url = "https://jifugou.picp.vip/alipay/alipayNotifyNotice";
    //页面跳转 同步通知页面  接口 需要 http:// 完整的链接接口不能加?id=123等参数
    public static String return_url = "https://jifugou.picp.vip/alipay/alipayReturnNotice";
    //签名方式
    public static String sign_type = "RSA2";
    //字符串编码格式
    public static String charset = "utf-8";
    //支付宝网关 沙箱测试环境：https://openapi.alipaydev.com/gateway.do，正式环境为：https://openapi.alipay.com/gateway.do
    public static String gatewayUrl = "https://openapi.alipay.com/gateway.do";
}
