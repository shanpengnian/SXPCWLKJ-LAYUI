package com.sxpcwlkj.utils;

/**
 * session key 定义
 */
public class EnumUtil {
    public enum Result {
        SUCCESS(0),                 // 成功
        FAILURE(1),                 // 失败
        UNAUTHOR(403),              // 无权
        ERROR(4);                   // 出错
        private final int value;

        Result(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 状态编码
     */
    public enum StaticUtil {
        SUCCESS("0"),     //成功
        FAILURE("1"),     //失败
        UNAUTHOR("403"),  //无权
        ERROR("4"),       //出错

        CODE("CODE"),
        LOGINCODE("LOGINCODE"),
        REGISTERCODE("REGISTERCODE"),
        WORDPRESSCODE("WORDPRESSCODE"),
        PHONECODE("PHONECODE"),
        PHONE("PHONE");  //

        private final String value;

        StaticUtil(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum PageSize{
        PAGE_NUMBER_TEN(10),
        PAGE_FIF_TEN(15),
        PAGE_NUMBER_TWENTY(20),
        PAGE_NUMBER_THRITY(30),
        PAGE_NUMBER_FIFTY(50),
        PAGE_NUMBER_NOE_HUNDRED(100);
        private final int value;

        PageSize(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum Role {
        USER("user"),                 // 成功
        MEMBER("member");             //  失败
        private final String value;

        Role(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum SysIifo {
        SYSCODE("FrCjKjRgvvPKJVDY"),
        SYSNAME("SXPCWLKJ-CMS"),
        SYSVERSION("v-2.0"),
        LANGUNGER("simplified Chinese");
        private final String value;
        SysIifo(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 支付状态
     */
    public  enum Pay{
        待付款(1),			// 代付款
        已付款(2),				// 已付款
        已取消(3),			// 已取消
        交易关闭(4);			// 超时未支付, 交易关闭
        private final int value;
        Pay(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }
    }


    /**
     * 支付状态
     */
    public  enum System{
        每条短信费用("-0.06");		// 代付款

        private final String value;
        System(String value) {
            this.value = value;
        }
        public String getValue() {
            return value;
        }
    }

    /**
     * 支付状态
     */
    public  enum SysStatic{
        系统LOGO("http://image.sxpcwlkj.cn/jifugouicon.png"),		//集福购logo
        小店背景("http://jifugou.sxpcwlkj.cn/25e8187d-8d86-4686-a5cb-b1693253dc50.png");

        private final String value;
        SysStatic(String value) {
            this.value = value;
        }
        public String getValue() {
            return value;
        }
    }


}
