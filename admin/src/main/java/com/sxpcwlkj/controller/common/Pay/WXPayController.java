package com.sxpcwlkj.controller.common.Pay;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.sxpcwlkj.annotation.AuthLoginAnnotation;
import com.sxpcwlkj.controller.common.CommonController;
import com.sxpcwlkj.entity.*;
import com.sxpcwlkj.mapper.SysMoneyMapper;
import com.sxpcwlkj.mapper.WxPayConfigMapper;
import com.sxpcwlkj.service.PayService;
import com.sxpcwlkj.utils.*;
import com.sxpcwlkj.wxpay.MyWXConfig;
import com.sxpcwlkj.wxpay.WXPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 微信支付
 */
@RestController
@RequestMapping("wxPay")
@CrossOrigin(origins = "*", maxAge = 3600)
public class WXPayController extends CommonController {

    @Autowired
    private PayService payService;

    @Autowired
    private SysMoneyMapper sysMoneyMapper;
    @Autowired
    private WxPayConfigMapper wxPayConfigMapper;


    @GetMapping("getQRCode")
    @AuthLoginAnnotation(authorityCode = "/wxPay/getQRCode")
    public JsonResultPage<Object> alipayReturnNotice(HttpServletRequest request, HttpServletRequest response) {
        JsonResultPage<Object> resultJson = new JsonResultPage<Object>();
        //支付订单id
        int orderId= DataUtil.getInt(request.getParameter("orderId"));
        //描述
        String describe= DataUtil.getString(request.getParameter("describe"));
        //查询订单
        PayOrder payOrder = payService.selectPayOrder(DataUtil.getInt(orderId));
        //查出产品
        PayProduct payProduct = payService.selectPayProduct(payOrder.getProductId());
        try {
            User user=this.getUser();
            WxPayConfig iswxPayConfig = wxPayConfigMapper.selectOne(new QueryWrapper<WxPayConfig>().eq("user_id",user.getUserId()));
            MyWXConfig config = new MyWXConfig(iswxPayConfig);
            WXPay wxpay = new WXPay(config);
            // 拼接统一下单需要的参数
            SortedMap<String, String> packageParams = new TreeMap<String, String>();
            packageParams.put("appid", config.getAppID()); // 公众账号appid
            packageParams.put("mch_id", config.getMchID()); // 商户号
            packageParams.put("nonce_str", DataUtil.getRandomString(16)); // 随机字符串
            packageParams.put("body", describe); // 商品描述
            packageParams.put("out_trade_no", payOrder.getOrderNum()); // 订单编号
            packageParams.put("total_fee", BigDecimalUtil.getBigDecimalDian(payOrder.getOrderPice(), BigDecimalUtil.TWO).toString()); // 小数点向右移动n位 价格的单位为分  "1"=0.01元
            packageParams.put("spbill_create_ip", DataUtil.getIp()); //终端IP
            packageParams.put("notify_url", config.getNotiey_URL()); // 回调URLhttp://www.sxpcwlkj/notify
            packageParams.put("trade_type", "NATIVE"); // 交易类型
            packageParams.put("product_id", payOrder.getProductId() + "");     //商品ID
            Map<String, String> map = wxpay.unifiedOrder(packageParams);

            String urlCode = DataUtil.getString(map.get("code_url"));
            //System.out.println("微信支付url"+urlCode);
            //进行生成二维码
            String text = urlCode;
            int width = 300;
            int height = 300;
            // 二维码的图片格式
            String format = "png";
            Hashtable hints = new Hashtable();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            BitMatrix bitMatrix;
            bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);

            //微信支付，避免同事生成，同一个会员多次生成，所以设置每个会员生成同一个二维码  二次被覆盖
            String url="";
            if(user!=null){
                url = QRCodeUtil.uploadQRcode(request, bitMatrix, "pay_userid" + user.getUserId());
            }else {
               return null;
            }
            //为了安全生成的二维码图片 转成base64
            url = ImageToBase64Util.ImageToBase64(url);
            map.put("wxCode", url);
            resultJson.setCode(EnumUtil.Result.SUCCESS.getValue());
            resultJson.setData(url);
            resultJson.setMsg("获取二维码成功");
        } catch (Exception ex) {
            ex.printStackTrace();
            resultJson.setCode(EnumUtil.Result.ERROR.getValue());
        }
        return resultJson;
    }

    /**
     * timer回调
     * @param req
     * @param resp
     * @return
     */
    @RequestMapping("notifyWeChatPnPay")
    @AuthLoginAnnotation(authorityCode = "/wxPay/notifyWeChatPnPay")
    public JsonResultPage<Object> notifyWeChatPnPay(HttpServletRequest req, HttpServletResponse resp, Model model) {
        JsonResultPage<Object> resultJson = new JsonResultPage<Object>();
        //支付订单id
        int orderId= DataUtil.getInt(req.getParameter("orderId"));
        //查询订单
        PayOrder payOrder = payService.selectPayOrder(DataUtil.getInt(orderId));
        WxPayConfig iswxPayConfig = wxPayConfigMapper.selectOne(new QueryWrapper<WxPayConfig>().eq("user_id",payOrder.getUserId()));
        MyWXConfig config = new MyWXConfig(iswxPayConfig);
        try {
            WXPay wxpay = new WXPay(config);
            Map<String, String> data = new HashMap<String, String>();
            data.put("out_trade_no", payOrder.getOrderNum());
            //System.out.println(data);
            Map<String, String> resMap = wxpay.orderQuery(data);
            String return_code = resMap.get("return_code");
            String returnMsg=resMap.get("return_msg ");
            if(return_code.equals("SUCCESS")) {
                String trade_state = resMap.get("trade_state");//返回成功可以写逻辑代码了，为了安全限制可以在加判断防止攻击
                if(trade_state.equals("SUCCESS")) {//当trade_state 返回success时说明已经支付成功
                    //已支付的订单不在执行下面的业务
                    if(payOrder.getOrderState()==2){
                        resultJson.setCode(EnumUtil.Result.SUCCESS.getValue());
                        resultJson.setState(1);
                        resultJson.setMsg("已支付");
                        return resultJson;
                    }
                    //充值
                    SysMoney isSysMoney=sysMoneyMapper.selectOne(new QueryWrapper<SysMoney>().eq("sys_code",EnumUtil.SysIifo.SYSCODE.getValue()));
                    System.out.println("A:"+isSysMoney.getSysAllpice());
                    System.out.println("B:"+payOrder.getOrderPice());
                    isSysMoney.setSysAllpice(isSysMoney.getSysAllpice().add(payOrder.getOrderPice()));
                    isSysMoney.setSysPice(isSysMoney.getSysPice().add(payOrder.getOrderPice()));
                    sysMoneyMapper.updateForSyscode(isSysMoney);
                    //改变订单状态
                    int i=payService.updatePayOrdeType(payOrder.getUserId(),payOrder.getOrderNum(), DataUtil.getString(payOrder.getOrderPice()),returnMsg);
                    if(i==1){
                        resultJson.setCode(EnumUtil.Result.SUCCESS.getValue());
                        resultJson.setState(1);
                        resultJson.setMsg("支付成功！");
                    }else {
                        resultJson.setCode(EnumUtil.Result.FAILURE.getValue());
                        resultJson.setMsg("支付成功,系统余额充值出错了，联系管理员解决！");
                    }
                    //System.out.println("支付宝回调修改订单"+i);
                    //System.out.println("--------支付成功可以写逻辑代码了！！！！！！");
                    return resultJson;

                }
            }else {
                resultJson.setCode(EnumUtil.Result.SUCCESS.getValue());
                resultJson.setMsg("支付失败！");
                resultJson.setState(2);
                return resultJson;
            }
        } catch (Exception e) {
            resultJson.setCode(EnumUtil.Result.ERROR.getValue());
            e.printStackTrace();
        }
        return resultJson;
    }
}
