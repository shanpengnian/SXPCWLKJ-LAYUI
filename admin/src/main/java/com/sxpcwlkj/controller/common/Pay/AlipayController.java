package com.sxpcwlkj.controller.common.Pay;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.sxpcwlkj.annotation.AuthLoginAnnotation;
import com.sxpcwlkj.entity.AlipayConfigEntity;
import com.sxpcwlkj.entity.PayOrder;
import com.sxpcwlkj.entity.PayProduct;
import com.sxpcwlkj.mapper.AlipayMapper;
import com.sxpcwlkj.service.PayService;
import com.sxpcwlkj.utils.DataUtil;
import com.sxpcwlkj.utils.EnumUtil;
import com.sxpcwlkj.utils.JsonResultObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 支付宝
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("alipay")
public class AlipayController {
    @Autowired
    private PayService payService;

    @Autowired
    private AlipayMapper alipayMapper;

    /**
     * 前往支付宝第三方网关进行支付
     *
     * @param orderId  订单id
     * @param describe 订单描述
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/goAlipay", produces = "text/html; charset=UTF-8")
    @AuthLoginAnnotation(authorityCode = "/alipay/goAlipay")
    public String goAlipay(String orderId, String describe, HttpServletRequest request, HttpServletRequest response, Model model) throws Exception {

        try {
            //查询订单
            PayOrder payOrder = payService.selectPayOrder(DataUtil.getInt(orderId));
            //查出产品
            PayProduct payProduct = payService.selectPayProduct(payOrder.getProductId());

            //支付宝配置
            AlipayConfigEntity alipay = alipayMapper.selsetAlipay();

            //获得初始化的AlipayClient  网关  支付应用ID  商户私钥  格式  编码格式  支付宝公钥  签名方式
            AlipayClient alipayClient = new DefaultAlipayClient(alipay.getGatewayUrl(), alipay.getAppId(), alipay.getMerchantPrivateKey(), "json", alipay.getAlipayCharset(), alipay.getAlipayPublicKey(), alipay.getSignType());

            //设置请求参数
            AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
            //页面跳转 同步通知页面 接口
            alipayRequest.setReturnUrl(alipay.getReturnUrl());
            //支付宝支付宝服务器主动通知商户服务器里指定的页面http/https路径。 接口
            alipayRequest.setNotifyUrl(alipay.getNotifyUrl());
            //商户订单号，商户网站订单系统中唯一订单号，必填
            String out_trade_no = payOrder.getOrderNum();
            //付款金额，必填
            String total_amount = DataUtil.getString(payOrder.getOrderPice());
            //订单名称，必填
            String subject = payProduct.getProductName();
            //商品描述，可空
            String body = describe;

            // 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
            String timeout_express = "1c";
            //订单描述信息拼接
            alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
                    + "\"total_amount\":\"" + total_amount + "\","
                    + "\"subject\":\"" + subject + "\","
                    + "\"body\":\"" + body + "\","
                    + "\"timeout_express\":\"" + timeout_express + "\","
                    + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

            //请求
            String result = alipayClient.pageExecute(alipayRequest).getBody();
            //返回支付宝发起支付页面的url
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "出错了";
    }

    /**
     * 页面跳转 同步 通知页面
     * 验证签名
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */


    @RequestMapping(value = "/alipay/alipayReturnNotice")
    @AuthLoginAnnotation(login = false)
    public String alipayReturnNotice(HttpServletRequest request, HttpServletRequest response) throws Exception {
        JsonResultObject<Object> resultJson = new JsonResultObject<Object>();
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
        AlipayConfigEntity alipay = alipayMapper.selsetAlipay();
        boolean signVerified = AlipaySignature.rsaCheckV1(params, alipay.getAlipayPublicKey(), alipay.getAlipayCharset(), alipay.getSignType()); //调用SDK验证签名
        String msg = "";
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


            //根据订单号修改支付状态
            int i = payService.updatePayOrdeType(1, out_trade_no, total_amount, trade_no);
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
                "\t\t   window.location.href='" + alipay.getNotifyUrl() + "?msg=" + msg + "'" +
                "\t\t</script>\n" +
                "\t</head>\n" +
                "</html>\n";
        return html;
    }

    /**
     * 页面跳转 异步 通知页面
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/alipayNotifyNotice", produces = "application/json; charset=utf-8")
    @AuthLoginAnnotation(authorityCode = "/alipay/alipayNotifyNotice")
    public String alipayNotifyNotice(HttpServletRequest request, HttpServletRequest response, Model model) throws Exception {
        JsonResultObject<Object> resultJson = new JsonResultObject<Object>();
        //log.info("支付成功, 进入异步通知接口...");

        //获取支付宝POST过来反馈信息
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
//			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        //支付宝配置
        AlipayConfigEntity alipay = alipayMapper.selsetAlipay();
        boolean signVerified = AlipaySignature.rsaCheckV1(params, alipay.getAlipayPublicKey(), alipay.getAlipayCharset(), alipay.getSignType()); //调用SDK验证签名

        //——请在这里编写您的程序（以下代码仅作参考）——

		/* 实际验证过程建议商户务必添加以下校验：
		1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
		2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
		3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
		4、验证app_id是否为该商户本身。
		*/
        if (signVerified) {//验证成功
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

            //交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");

            //付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");
            //根据订单号修改支付状态
            int i = payService.updatePayOrdeType(1, out_trade_no, total_amount, trade_no);
            if (i == 1) {
                resultJson.setCode(EnumUtil.Result.SUCCESS.getValue());
                resultJson.setMsg("支付成功！");
                model.addAttribute("info", "充值成功！<p>返回系统刷新查余额！</p>");  //Model封装参数,携带到前台
            } else {
                resultJson.setCode(EnumUtil.Result.FAILURE.getValue());
                resultJson.setMsg("支付失败！");
                model.addAttribute("info", "充值成功,但是系统余额充值出错了");  //Model封装参数,携带到前台
            }


            if (trade_status.equals("TRADE_FINISHED")) {
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序

                //注意： 尚自习的订单没有退款功能, 这个条件判断是进不来的, 所以此处不必写代码
                //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
            } else if (trade_status.equals("TRADE_SUCCESS")) {
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //付款完成后，支付宝系统发送该交易状态通知

                // 修改叮当状态，改为 支付成功，已付款; 同时新增支付流水

            }
            //System.out.println("支付成功");

        } else {//验证失败
            System.out.println("支付, 验签失败...");
            resultJson.setCode(EnumUtil.Result.FAILURE.getValue());
            resultJson.setMsg("支付失败！");
            model.addAttribute("info", "充值失败！");
        }
        return "admin/pay/alipaySuccess";
    }

}
