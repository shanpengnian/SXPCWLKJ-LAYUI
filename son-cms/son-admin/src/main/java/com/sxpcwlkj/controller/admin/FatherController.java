package com.sxpcwlkj.controller.admin;

import com.alipay.api.internal.util.AlipaySignature;
import com.sxpcwlkj.annotation.AuthLoginAnnotation;
import com.sxpcwlkj.service.FatherService;
import com.sxpcwlkj.service.SmsService;
import com.sxpcwlkj.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@RestController
@RequestMapping("/Plugin")
@CrossOrigin(origins = "*", maxAge = 3600)
public class FatherController {


    @Autowired
    private FatherService fatherService;

    /**
     * 验证码发送
     * @param phone
     * @param req
     * @param resp
     * @return
     */

    @PostMapping("sendPhone")
    @AuthLoginAnnotation(authorityCode = "/Plugin/sendPhone")
    public JsonResultObject<Object> sendPhone(String phone, HttpServletRequest req, HttpServletResponse resp) {

        try {
            /**
             * 手机号
             * 模板ID
             * 类型  1：验证码  2：营销短信
             */
            JsonResultObject jsonResultObject=fatherService.sendMessages(phone,"256619",1);
            return jsonResultObject;
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResultObject.getErroeResult(e.getMessage());
        }

    }


    /**
     * 营销短信
     * @param phone
     * @param req
     * @param resp
     * @return
     */
    @PostMapping("sendYinxiao")
    @AuthLoginAnnotation(authorityCode = "/Plugin/sendYinxiao")
    public JsonResultObject<Object> sendYinxiao(String phone, HttpServletRequest req, HttpServletResponse resp) {
        try {
            /**
             * 手机号
             * 模板ID
             * 类型  1：验证码  2：营销短信
             */
            JsonResultObject jsonResultObject=fatherService.sendMessages(phone,"233996",2);
            return jsonResultObject;
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResultObject.getErroeResult(e.getMessage());
        }

    }

    /**
     * 微信下单
     * @param req
     * @param resp
     * @return
     */
    @GetMapping("voucher")
    @AuthLoginAnnotation(authorityCode = "/Plugin/voucher")
    public JsonResultObject<Object> voucher(HttpServletRequest req, HttpServletResponse resp) {
        try {
            //支付金额
            BigDecimal piceNum = DataUtil.getBigDecimal(req.getParameter("piceNum"));
            if (DataUtil.getInt(req.getParameter("piceNum")) <= 0) {
                return JsonResultObject.getFailureResult("金额有误");
            }
            //产品id
            int productId = DataUtil.getInt(req.getParameter("productId"));
            //产品数量
            int orderCount = DataUtil.getInt(req.getParameter("orderCount"));       //充值类型  =1
            JsonResultObject jsonResultObject=fatherService.voucherOrder(piceNum,productId,orderCount);
            return jsonResultObject;
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResultObject.getErroeResult(e.getMessage());
        }

    }

    /**
     * 系统充值下单
     * @param req
     * @param resp
     * @return
     */
    @GetMapping("getQRCode")
    @AuthLoginAnnotation(authorityCode = "/Plugin/getQRCode")
    public JsonResultObject<Object> getQRCode(HttpServletRequest req, HttpServletResponse resp) {
        try {
            //支付订单id
            int orderId = DataUtil.getInt(req.getParameter("orderId"));
            //描述
            String describe = DataUtil.getString(req.getParameter("describe"));
            JsonResultObject jsonResultObject=fatherService.weChatPay(orderId,describe);
            return jsonResultObject;
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResultObject.getErroeResult(e.getMessage());
        }

    }

    /**
     * 微信下单支付异步回调
     * @param req
     * @param resp
     * @return
     */
    @GetMapping("notifyWeChatPnPay")
    @AuthLoginAnnotation(authorityCode = "/Plugin/notifyWeChatPnPay")
    public JsonResultObject<Object> notifyWeChatPnPay(HttpServletRequest req, HttpServletResponse resp) {
        try {
            //支付订单id
            int orderId = DataUtil.getInt(req.getParameter("orderId"));
            JsonResultObject jsonResultObject=fatherService.notifyWeChatPnPay(orderId);
            return jsonResultObject;
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResultObject.getErroeResult(e.getMessage());
        }
    }

    /**
     * 支付宝下单起调
     * @param orderId
     * @param describe
     * @param req
     * @param resp
     * @return
     */

    @RequestMapping(value = "/goAlipay", produces = "text/html; charset=UTF-8")
    @AuthLoginAnnotation(authorityCode = "/Plugin/goAlipay")
    public String goAlipay(int orderId, String describe,HttpServletRequest req, HttpServletResponse resp) {
        try {
            //支付订单id
            JsonResultObject jsonResultObject=fatherService.AlipayPay(orderId,describe);
            return jsonResultObject.getData().toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "失败了";
        }
    }

    /**
     * 支付宝同步成功回调
     * @param request
     * @param resp
     * @return
     */
    @GetMapping("alipayReturnNotice")
    @AuthLoginAnnotation(login = false)
    public String alipayReturnNotice(HttpServletRequest request, HttpServletResponse resp) {
        try {
            //获取支付宝GET过来反馈信息
            //获取支付宝GET过来反馈信息
            Map<String, String> params = new HashMap<String, String>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                //乱码解决，这段代码在出现乱码时使用
                valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
                params.put(name, valueStr);
            }
            //支付宝配置
            JsonResultObject data=fatherService.alipayReturnNotice();
            Map<Object, Object> jsonToMap = JsonUtil.getJsonToMap(data.getData());
//            data.put("alipayPublicKey", alipay.getAlipayPublicKey());
//            data.put("alipayCharset", alipay.getAlipayCharset());
//            data.put("signType", alipay.getSignType());
            boolean signVerified = AlipaySignature.rsaCheckV1(params,
                    DataUtil.getString(jsonToMap.get("alipayPublicKey")),
                    DataUtil.getString(jsonToMap.get("alipayCharset")),
                    DataUtil.getString(jsonToMap.get("signType"))); //调用SDK验证签名
            String msg = "";
            Map map=new HashMap();
            //——请在这里编写您的程序（以下代码仅作参考）——
            if (signVerified) {

                //商户订单号
                String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
                //支付宝交易号
                String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
                //付款金额
                String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");
                //System.out.println("支付, 验签成功:订单号为：" + out_trade_no);
                //判断是否已支付
                JsonResultObject entData=fatherService.updatealipayReturnNotice(out_trade_no,total_amount,trade_no);
                map=JsonUtil.getJsonToMap(entData.getData());
                //根据订单号修改支付状态
                int i =DataUtil.getInt(map.get("state"));
                if (i == -1) {
                    msg = "订单有误";
                } else if (i == -2) {
                    msg = "重复充值";
                } else if (i == -3) {
                    msg = "系统错误，充值失败";
                } else if (i == 0) {
                    msg = "充值失败";
                } else if (i == 1) {
                    msg = "充值成功";
                }
            } else {
                msg = "付款失败";
            }
            msg = DataUtil.getUrlEncode(msg);
            String html = "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "\t<head>\n" +
                    "\t\t<meta charset=\"utf-8\">\n" +
                    "\t\t<script language=\"javascript\" type=\"text/javascript\">\n" +
                    "\t\t   window.location.href='" + DataUtil.getString(map.get("infUrl")) + "?msg=" + msg + "'" +
                    "\t\t</script>\n" +
                    "\t</head>\n" +
                    "</html>\n";
            return html;


        } catch (Exception e) {
            e.printStackTrace();
            return "失败了";
        }
    }

}
