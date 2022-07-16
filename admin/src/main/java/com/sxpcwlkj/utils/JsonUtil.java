package com.sxpcwlkj.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * @author http://www.sxpcwlkj.com
 * @version 1.0
 * @description com.sxpcwlkj.utils
 * @date 2019/4/26 0026
 */
public class JsonUtil {
    public static void main(String[] args) {

        String strArr = "[{\n" +
                "        \"adzone_id\": \"482360879\",\n" +
                "        \"adzone_name\": \"AZSH1\",\n" +
                "        \"alipay_total_price\": \"29.8000\",\n" +
                "        \"auction_category\": \"母婴-尿片洗护\",\n" +
                "        \"click_time\": \"2019-06-30 14:27:44\",\n" +
                "        \"commission\": \"0.00\",\n" +
                "        \"commission_rate\": \"1.0000\",\n" +
                "        \"create_time\": \"2019-06-30 14:28:02\",\n" +
                "        \"income_rate\": \"0.4000\",\n" +
                "        \"item_num\": 2,\n" +
                "        \"item_title\": \"好太太洗衣液500g袋装厂家 会销礼品赠品 劳保福利 活动赠品。\",\n" +
                "        \"num_iid\": 572481026488,\n" +
                "        \"order_type\": \"淘宝\",\n" +
                "        \"pay_price\": \"0.00\",\n" +
                "        \"price\": \"19.90\",\n" +
                "        \"pub_share_pre_fee\": \"11.92\",\n" +
                "        \"seller_nick\": \"早呀阿灿哥\",\n" +
                "        \"seller_shop_title\": \"新启航生活馆\",\n" +
                "        \"site_id\": \"44622942\",\n" +
                "        \"site_name\": \"互力微信淘客系统\",\n" +
                "        \"subsidy_rate\": \"0.0000\",\n" +
                "        \"subsidy_type\": \"-1\",\n" +
                "        \"terminal_type\": \"2\",\n" +
                "        \"tk3rd_type\": \"--\",\n" +
                "        \"tk_status\": 12,\n" +
                "        \"total_commission_rate\": \"0.4000\",\n" +
                "        \"trade_id\": 286440325077279990,\n" +
                "        \"trade_parent_id\": 286440325077279990\n" +
                "    }]";
        for (Map<String, String> mapList : getJsionStrToListMap(strArr)) {
            for (Map.Entry entry : mapList.entrySet()) {
                System.out.println(entry.getKey() + "  " + entry.getValue());
            }
        }
        String str = "{\"ID\":1915399,\"GoodsId\":\"590033982005\",\"GoodsName\":\"【Niye/耐也】挂脖式无线运动蓝牙耳机\"}";
        Map<Object, Object> map=  getJsonToMap(str);
        for (Map.Entry<Object, Object> entry : map.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
    }

    /**
     * 字符串转 Jsion
     *
     * @param str
     * @return
     */
    public static JSONObject getStrToJsion(String str) {
        JSONObject json = JSON.parseObject(str);
        return json;
    }
    /**
     * json string 转换为 map 对象
     *
     * @param jsonObj
     * @return
     */
    public static Map<Object, Object> getJsonToMap(Object jsonObj) {
        net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(jsonObj);
        Map<Object, Object> map = (Map) jsonObject;
        return map;
    }
    /**
     * json string 转换为 对象
     *
     * @param jsonObj
     * @param type
     * @return
     */
    public static <T> T jsonToBean(Object jsonObj, Class<T> type) {
        net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(jsonObj);
        T obj = (T) net.sf.json.JSONObject.toBean(jsonObject, type);
        return obj;
    }

    /**
     * 对象转jsion类型字符串
     * @param object
     * @return
     */
    public static String entityToJsonString(Object object){
        return  new JSONObject().toJSONString(object);
    }
    /**
     * Json类型字符串 转 List  map
     *
     * @param strArr
     * @return
     */
    public static List<Map<String, String>> getJsionStrToListMap(String strArr) {
        List<Map<String, String>> listObjectFir = (List<Map<String, String>>) JSONArray.parse(strArr);

        return listObjectFir;

        /*
        * //第一种方式
        List<Map<String, String>> listObjectFir = (List<Map<String, String>>) JSONArray.parse(strArr);
        System.out.println("利用JSONArray中的parse方法来解析json数组字符串");
        for (Map<String, String> mapList : listObjectFir) {
            for (Map.Entry entry : mapList.entrySet()) {
                System.out.println(entry.getKey() + "  " + entry.getValue());
            }
        }
        //第二种方式
        List<Map<String, String>> listObjectSec = JSONArray.parseObject(strArr, List.class);
        System.out.println("利用JSONArray中的parseObject方法并指定返回类型来解析json数组字符串");
        for (Map<String, String> mapList : listObjectSec) {
            for (Map.Entry entry : mapList.entrySet()) {
                System.out.println(entry.getKey() + "  " + entry.getValue());
            }
        }
        //第三种方式
        JSONArray listObjectThir = JSONArray.parseArray(strArr);
        System.out.println("利用JSONArray中的parseArray方法来解析json数组字符串");
        for (Object mapList : listObjectThir) {
            for (Object entry : ((Map) mapList).entrySet()) {
                System.out.println(((Map.Entry) entry).getKey() + "  " + ((Map.Entry) entry).getValue());
            }
        }
        //第四种方式
        List listObjectFour = JSONArray.parseArray(strArr, Map.class);
        System.out.println("利用JSONArray中的parseArray方法并指定返回类型来解析json数组字符串");
        for (Object mapList : listObjectFour) {
            for (Object entry : ((Map) mapList).entrySet()) {
                System.out.println(((Map.Entry) entry).getKey() + "  " + ((Map.Entry) entry).getValue());
            }
        }
        //第五种方式
        JSONArray listObjectFifth = JSONObject.parseArray(strArr);
        System.out.println("利用JSONObject中的parseArray方法来解析json数组字符串");
        for (Object mapList : listObjectFifth) {
            for (Object entry : ((Map) mapList).entrySet()) {
                System.out.println(((Map.Entry) entry).getKey() + "  " + ((Map.Entry) entry).getValue());
            }
        }
        //第六种方式
        List listObjectSix = JSONObject.parseArray(strArr, Map.class);
        System.out.println("利用JSONObject中的parseArray方法并指定返回类型来解析json数组字符串");
        for (Object mapList : listObjectSix) {
            for (Object entry : ((Map) mapList).entrySet()) {
                System.out.println(((Map.Entry) entry).getKey() + "  " + ((Map.Entry) entry).getValue());
            }
        }
        //第七种方式
        JSONArray listObjectSeven = JSON.parseArray(strArr);
        System.out.println("利用JSON中的parseArray方法来解析json数组字符串");
        for (Object mapList : listObjectSeven) {
            for (Object entry : ((Map) mapList).entrySet()) {
                System.out.println(((Map.Entry) entry).getKey() + "  " + ((Map.Entry) entry).getValue());
            }
        }
        //第八种方式
        List listObjectEigh = JSONObject.parseArray(strArr, Map.class);
        System.out.println("利用JSON中的parseArray方法并指定返回类型来解析json数组字符串");
        for (Object mapList : listObjectEigh) {
            for (Object entry : ((Map) mapList).entrySet()) {
                System.out.println(((Map.Entry) entry).getKey() + "  " + ((Map.Entry) entry).getValue());
            }
        }
        */
    }

    public  static  String[]   strArrToArr(String str){
        str=DataUtil.deleteString(str,'[');
        str=DataUtil.deleteString(str,']');
        String[] arr = str.split(","); // 用,分割
        /*注意： . 、 | 和 * 等转义字符，必须得加 \\。多个分隔符，可以用 | 作为连字符*/
        return  arr;
    }

}
