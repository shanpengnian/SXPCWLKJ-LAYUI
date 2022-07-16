package com.sxpcwlkj.service.impl;

import com.sxpcwlkj.entity.PayOrder;
import com.sxpcwlkj.entity.UrlConfig;
import com.sxpcwlkj.service.FatherService;
import com.sxpcwlkj.utils.*;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FatherServiceImpl implements FatherService {
    @Autowired
    private UrlConfig urlConfig;

    @Override
    public JsonResultObject sendMessages(String phone, String codeId,int type) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("phone", phone);
            params.put("codeId", codeId);
            params.put("type",type);
            String strData = HttpRequestUtil.Get(urlConfig.getUrl() + "sendPhone", params, urlConfig.getToken());
            JSONObject jsonObject = JSONObject.fromObject(new String(strData));
            JsonResultObject jsonResultObject = (JsonResultObject) JSONObject.toBean(jsonObject, JsonResultObject.class);
            //System.out.println(jsonResultObject.toString());
            return jsonResultObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public JsonResultObject voucherOrder(BigDecimal piceNum,int productId,int num) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("piceNum", DataUtil.getString(piceNum));
            params.put("productId",productId);
            params.put("orderCount",num);
            String strData = HttpRequestUtil.Get(urlConfig.getUrl() + "voucher", params, urlConfig.getToken());
            JSONObject jsonObject = JSONObject.fromObject(new String(strData));
            JsonResultObject jsonResultObject = (JsonResultObject) JSONObject.toBean(jsonObject, JsonResultObject.class);
            //System.out.println(jsonResultObject.toString());
            return jsonResultObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public JsonResultObject weChatPay(int orderId, String describe) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("orderId", orderId);
            params.put("describe",describe);
            String strData = HttpRequestUtil.Get(urlConfig.getUrl() + "getQRCode", params, urlConfig.getToken());
            JSONObject jsonObject = JSONObject.fromObject(new String(strData));
            JsonResultObject jsonResultObject = (JsonResultObject) JSONObject.toBean(jsonObject, JsonResultObject.class);
            //System.out.println(jsonResultObject.toString());
            return jsonResultObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public JsonResultObject notifyWeChatPnPay(int orderId) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("orderId", orderId);
            String strData = HttpRequestUtil.Get(urlConfig.getUrl() + "notifyWeChatPnPay", params, urlConfig.getToken());
            JSONObject jsonObject = JSONObject.fromObject(new String(strData));
            JsonResultObject jsonResultObject = (JsonResultObject) JSONObject.toBean(jsonObject, JsonResultObject.class);
            //System.out.println(jsonResultObject.toString());
            return jsonResultObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public JsonResultObject AlipayPay(int orderId, String describe) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("orderId", orderId);
            params.put("describe",describe);
            String strData = HttpRequestUtil.Get(urlConfig.getUrl() + "goAlipay", params, urlConfig.getToken());
            JSONObject jsonObject = JSONObject.fromObject(new String(strData));
            JsonResultObject jsonResultObject = (JsonResultObject) JSONObject.toBean(jsonObject, JsonResultObject.class);
            //System.out.println(jsonResultObject.toString());
            return jsonResultObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public JsonResultObject alipayReturnNotice() {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("orderId", 0);
            String strData = HttpRequestUtil.Get(urlConfig.getUrl() + "alipayReturnNotice", params, urlConfig.getToken());
            JSONObject jsonObject = JSONObject.fromObject(new String(strData));
            JsonResultObject jsonResultObject = (JsonResultObject) JSONObject.toBean(jsonObject, JsonResultObject.class);
            //System.out.println(jsonResultObject.toString());
            return jsonResultObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public JsonResultObject updatealipayReturnNotice(String outTradeNo,String totalAmount, String tradeNo) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("outTradeNo", outTradeNo);
            params.put("totalAmount", totalAmount);
            params.put("tradeNo", tradeNo);
            String strData = HttpRequestUtil.Get(urlConfig.getUrl() + "uddateAlipayOrder", params, urlConfig.getToken());
            JSONObject jsonObject = JSONObject.fromObject(new String(strData));
            JsonResultObject jsonResultObject = (JsonResultObject) JSONObject.toBean(jsonObject, JsonResultObject.class);
            //System.out.println(jsonResultObject.toString());
            return jsonResultObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public JsonResultObject selectMoney() {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("outTradeNo", 0);

            String strData = HttpRequestUtil.Get(urlConfig.getUrl() + "selectSysMoney", params, urlConfig.getToken());
            JSONObject jsonObject = JSONObject.fromObject(new String(strData));
            JsonResultObject jsonResultObject = (JsonResultObject) JSONObject.toBean(jsonObject, JsonResultObject.class);
            System.out.println(jsonResultObject.getData().toString());
            jsonResultObject.setData(JsonUtil.getJsonToMap(jsonResultObject.getData()));
            return jsonResultObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public JsonResultObject uploadFile(File file) {
        try {
            Map<String, Object> params = new HashMap<>();
            String strData = HttpRequestUtil.getUploadFile(urlConfig.getUrl() + "fileUpload",file, params, urlConfig.getToken());
            JSONObject jsonObject = JSONObject.fromObject(new String(strData));
            JsonResultObject jsonResultObject = (JsonResultObject) JSONObject.toBean(jsonObject, JsonResultObject.class);

            return jsonResultObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
