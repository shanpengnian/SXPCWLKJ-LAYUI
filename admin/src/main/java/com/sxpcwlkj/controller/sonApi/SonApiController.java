package com.sxpcwlkj.controller.sonApi;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.StringUtils;
import com.sxpcwlkj.annotation.KeyToken;
import com.sxpcwlkj.controller.common.CommonController;
import com.sxpcwlkj.entity.*;
import com.sxpcwlkj.mapper.*;
import com.sxpcwlkj.service.LogService;
import com.sxpcwlkj.service.PayService;
import com.sxpcwlkj.service.SmsService;
import com.sxpcwlkj.utils.*;
import com.sxpcwlkj.wxpay.MyWXConfig;
import com.sxpcwlkj.wxpay.WXPay;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/guest/api/")
public class SonApiController extends CommonController {

    @Autowired
    private LogService logService;
    @Autowired
    private SmsService smsService;
    @Autowired
    private PayService payService;
    @Autowired
    private ObjectEntityMapper objectEntityMappe;
    @Autowired
    private SysMoneyMapper sysMoneyMapper;
    @Autowired
    private WxPayConfigMapper wxPayConfigMapper;
    @Autowired
    private AlipayMapper alipayMapper;
    @Autowired
    private PayOrderMapper payOrderMapper;

    private int objectId = 0;

    /**
     * 执行先锋
     *
     * @param request
     * @param resp
     */
    @ModelAttribute
    void beforeFunction(HttpServletRequest request, HttpServletResponse resp) {
        String tokenKet = DataUtil.getString(request.getHeader("token"));
        Map<String, Object> data = TokenUtil.getpareJwt(tokenKet);
        if (data == null) {
            throw new RuntimeException("非法请求");
        }
        objectId = DataUtil.getInt(data.get("objectId"));
    }

    /**
     * 验证码发送/营销短信发送
     *
     * @param phone  目标手机号
     * @param codeId 模板ID
     * @param type   类型1：验证码  2：营销短信
     * @return 返回验证码   营销短信返回：1
     */
    @KeyToken
    @GetMapping("sendPhone")
    public JsonResultObject<Object> sendPhone(String phone, String codeId, int type) {
        if(StringUtil.isNullOrEmpty(phone)||StringUtil.isNullOrEmpty(codeId)){
            return JsonResultObject.getFailureResult("参数有误");
        }
        try {
            String code = smsService.querySmsInfo(phone, codeId, type);
            return JsonResultObject.getSuccessResult(code, "验证码发送成功");
        } catch (Exception e) {
            logService.addLog(new Log(3,"增,objectId="+objectId+"请求出错，参数为 phone:"+phone+"codeId:"+codeId+"type:"+type+""+e.getMessage()));
           return JsonResultObject.getFailureResult(e.getMessage());
        }

    }


    /**
     * 创建订单
     *
     * @param req
     * @param resp
     * @return 订单id
     */
    @KeyToken
    @GetMapping("voucher")
    public JsonResultObject<Object> voucherOrder(HttpServletRequest req, HttpServletResponse resp) {
        //支付金额
        BigDecimal piceNum = DataUtil.getBigDecimal(req.getParameter("piceNum"));
        if (DataUtil.getInt(req.getParameter("piceNum")) <= 0) {
            return JsonResultObject.getFailureResult("金额有误");
        }
        //产品id
        int productId = DataUtil.getInt(req.getParameter("productId"));
        //产品数量
        int orderCount = DataUtil.getInt(req.getParameter("orderCount"));       //充值类型  =1

        //创建订单
        PayOrder payOrder = new PayOrder();
        //16位订单号
        payOrder.setOrderNum(DataUtil.getOrderIdByUUId());
        //待支付
        payOrder.setOrderState(EnumUtil.Pay.待付款.getValue());
        //订单金额
        payOrder.setOrderPice(piceNum);
        //实际支付金额  暂时为null
        //产品id   充值：1
        payOrder.setProductId(productId);
        //下单数量
        payOrder.setOrderCount(orderCount);
        //创建订单时间
        payOrder.setOrderAddTime(new Date());
        //支付时间     暂时为null
        //userId 默认为管理员待收 1
        payOrder.setUserId(1);
        //子系统唯一编码
        ObjectEntity objectEntity = objectEntityMappe.selectById(objectId);
        payOrder.setSysCode(objectEntity.getObjectCode());
        try {
            int num = payService.addPayOrder(payOrder);
            if (num == 1) {
                return JsonResultObject.getSuccessResult(payOrder.getOrderId(), "订单创建成功");
            } else {
                return JsonResultObject.getFailureResult("订单创建失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResultObject.getErroeResult(e.getMessage());
        }
    }

    /**
     * 微信下单 生成二维码 返回起调App
     *
     * @param request
     * @param response
     * @return
     */
    @KeyToken
    @GetMapping("getQRCode")
    public JsonResultObject<Object> getQRCode(HttpServletRequest request, HttpServletRequest response) {

        //支付订单id
        int orderId = DataUtil.getInt(request.getParameter("orderId"));
        //描述
        String describe = DataUtil.getString(request.getParameter("describe"));
        //查询订单
        PayOrder payOrder = payService.selectPayOrder(DataUtil.getInt(orderId));
        //查出产品
        PayProduct payProduct = payService.selectPayProduct(payOrder.getProductId());
        try {
            User user = this.getUser();
            WxPayConfig iswxPayConfig = wxPayConfigMapper.selectOne(new QueryWrapper<WxPayConfig>().eq("user_id", 1));
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
            //User user = (User) SessionRequsetUtil.getSessionAttribute(EnumUtil.Role.USER.getValue());
            //微信支付，避免同事生成，同一个会员多次生成，所以设置每个会员生成同一个二维码  二次被覆盖
            String url = "";
            url = QRCodeUtil.uploadQRcode(request, bitMatrix, "pay_userid" + payOrder.getUserId());
            //为了安全生成的二维码图片 转成base64
            url = ImageToBase64Util.ImageToBase64(url);
            map.put("wxCode", url);
            return JsonResultObject.getSuccessResult(url, "微信下单成功");
        } catch (Exception ex) {
            ex.printStackTrace();
            return JsonResultObject.getErroeResult(ex.getMessage());
        }
    }

    /**
     * timer回调
     *
     * @param req
     * @param resp
     * @return
     */
    @KeyToken
    @GetMapping("notifyWeChatPnPay")
    public JsonResultObject<Object> notifyWeChatPnPay(HttpServletRequest req, HttpServletResponse resp) {
        //支付订单id
        int orderId = DataUtil.getInt(req.getParameter("orderId"));
        //查询订单
        PayOrder payOrder = payService.selectPayOrder(DataUtil.getInt(orderId));

        WxPayConfig iswxPayConfig = wxPayConfigMapper.selectOne(new QueryWrapper<WxPayConfig>().eq("user_id", 1));
        MyWXConfig config = new MyWXConfig(iswxPayConfig);
        try {
            WXPay wxpay = new WXPay(config);
            Map<String, String> data = new HashMap<String, String>();
            data.put("out_trade_no", payOrder.getOrderNum());
            //System.out.println(data);
            Map<String, String> resMap = wxpay.orderQuery(data);
            String return_code = resMap.get("return_code");
            String returnMsg = resMap.get("return_msg ");
            if (return_code.equals("SUCCESS")) {
                String trade_state = resMap.get("trade_state");//返回成功可以写逻辑代码了，为了安全限制可以在加判断防止攻击
                if (trade_state.equals("SUCCESS")) {//当trade_state 返回success时说明已经支付成功
                    //已支付的订单不在执行下面的业务
                    if (payOrder.getOrderState() == 2) {
                        return JsonResultObject.getSuccessResult(null, "支付成功");
                    }
                    //充值
                    SysMoney isSysMoney = sysMoneyMapper.selectOne(new QueryWrapper<SysMoney>().eq("sys_code", payOrder.getSysCode()));
                    if (isSysMoney == null) {
                        isSysMoney = new SysMoney();
                        isSysMoney.setSysAllpice(payOrder.getOrderPice());
                        isSysMoney.setSysPice(payOrder.getOrderPice());
                        isSysMoney.setSysCode(payOrder.getSysCode());
                        sysMoneyMapper.insert(isSysMoney);
                    } else {
                        isSysMoney.setSysAllpice(isSysMoney.getSysAllpice().add(payOrder.getOrderPice()));
                        isSysMoney.setSysPice(isSysMoney.getSysPice().add(payOrder.getOrderPice()));
                        sysMoneyMapper.updateForSyscode(isSysMoney);
                    }
                    logService.addLog(new Log("增,子系统(" + payOrder.getSysCode() + ")微信充值:" + payOrder.getOrderPice() + "元"));

                    //改变订单状态
                    int i = payService.updatePayOrdeType(payOrder.getUserId(), payOrder.getOrderNum(), DataUtil.getString(payOrder.getOrderPice()), returnMsg);
                    if (i == 1) {
                        return JsonResultObject.getSuccessResult(null, "支付成功");
                    } else {
                        return JsonResultObject.getFailureResult("支付成功,系统余额充值出错了，联系管理员解决！");
                    }
                    //System.out.println("支付宝回调修改订单"+i);
                    //System.out.println("--------支付成功可以写逻辑代码了！！！！！！");
                }
            } else {
                return JsonResultObject.getFailureResult("支付失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResultObject.getErroeResult(e.getMessage());
        }
        return JsonResultObject.getSuccessResult(null, "未支付", -1);
    }

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
    @KeyToken
    @GetMapping("goAlipay")
    public JsonResultObject<Object> goAlipay(String orderId, String describe, HttpServletRequest request, HttpServletRequest response, Model model) throws Exception {

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
            return JsonResultObject.getSuccessResult(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JsonResultObject.getErroeResult("出错了");
    }

    /**
     * 查询支付宝配置
     *
     * @return
     * @throws Exception
     */
    @KeyToken
    @GetMapping("alipayReturnNotice")
    public JsonResultObject<Object> alipayReturnNotice(){
        try {
            //支付宝配置
            AlipayConfigEntity alipay = alipayMapper.selsetAlipay();
            Map<String, Object> data = new HashMap<>();
            data.put("alipayPublicKey", alipay.getAlipayPublicKey());
            data.put("alipayCharset", alipay.getAlipayCharset());
            data.put("signType", alipay.getSignType());
            return JsonResultObject.getSuccessResult(data);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResultObject.getErroeResult(e.getMessage());
        }
    }

    /**
     * 支付宝支付更新订单
     * @param outTradeNo
     * @param totalAmount
     * @param tradeNo
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @KeyToken
    @GetMapping("uddateAlipayOrder")
    public JsonResultObject<Object> uddateAlipayOrder(String outTradeNo, String totalAmount, String tradeNo, HttpServletRequest request, HttpServletRequest response) throws Exception {
        JsonResultObject<Object> resultJson = new JsonResultObject<Object>();
        try {

            PayOrder payOrder =payOrderMapper.selectOne(new QueryWrapper<PayOrder>().eq("order_num",outTradeNo));
            //充值
            SysMoney isSysMoney = sysMoneyMapper.selectOne(new QueryWrapper<SysMoney>().eq("sys_code", payOrder.getSysCode()));
            if (isSysMoney == null) {
                isSysMoney = new SysMoney();
                isSysMoney.setSysAllpice(payOrder.getOrderPice());
                isSysMoney.setSysPice(payOrder.getOrderPice());
                isSysMoney.setSysCode(payOrder.getSysCode());
                sysMoneyMapper.insert(isSysMoney);
            } else {
                isSysMoney.setSysAllpice(isSysMoney.getSysAllpice().add(payOrder.getOrderPice()));
                isSysMoney.setSysPice(isSysMoney.getSysPice().add(payOrder.getOrderPice()));
                sysMoneyMapper.updateForSyscode(isSysMoney);
            }
            logService.addLog(new Log("增,子系统(" + payOrder.getSysCode() + ")支付宝充值:" + payOrder.getOrderPice() + "元"));

            //更新订单状态
            int i = payService.updatePayOrdeType(1, outTradeNo, totalAmount, tradeNo);

            //返回子系统的返回通知页面
            ObjectEntity objectEntity = objectEntityMappe.selectById(objectId);
            Map map = new HashMap();
            map.put("state", i);
            map.put("infUrl", objectEntity.getAlipayInfo());
            return JsonResultObject.getSuccessResult(map);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResultObject.getErroeResult(e.getMessage());
        }
    }

    /**
     * 查询系余额
     *
     * @return
     */
    @KeyToken
    @GetMapping("selectSysMoney")
    public JsonResultObject<Object> selectSysMoney() {
        try {

            ObjectEntity objectEntity = objectEntityMappe.selectById(objectId);
            SysMoney isSysMoney = sysMoneyMapper.selectOne(new QueryWrapper<SysMoney>().eq("sys_code", objectEntity.getObjectCode()));
            if (isSysMoney==null){
                isSysMoney=new SysMoney();
                isSysMoney.setSysCode(objectEntity.getObjectCode());
                isSysMoney.setSysPice(new BigDecimal(0));
                isSysMoney.setSysAllpice(new BigDecimal(0));
                sysMoneyMapper.insert(isSysMoney);
                Map map = new HashMap();
                map.put("sysPice", isSysMoney.getSysPice());
                map.put("sysAllpice", isSysMoney.getSysAllpice());
                return JsonResultObject.getSuccessResult(map);
            }else {
                Map map = new HashMap();
                map.put("sysPice", isSysMoney.getSysPice());
                map.put("sysAllpice", isSysMoney.getSysAllpice());
                return JsonResultObject.getSuccessResult(map);
            }


        } catch (Exception e) {
            e.printStackTrace();
            return JsonResultObject.getErroeResult(e.getMessage());
        }
    }

    /**
     * 上传附件
     *
     * @param headImg
     * @param req
     * @param resp
     * @return
     */
    @KeyToken
    @RequestMapping("fileUpload")
    public JsonResultObject<Object> fileUpload(@RequestParam(value = "file", required = false) MultipartFile headImg, HttpServletRequest req, HttpServletResponse resp) {

        try {
            String img = AlipayOSSUtil.uploadFile("", headImg);
            if (img != null && img.length() > 0) {
                return JsonResultObject.getSuccessResult(img, "成功");
            } else {
                return JsonResultObject.getSuccessResult(img, "成功", -1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResultObject.getErroeResult(e.getMessage());
        }

    }

    /**
     * 根据地址换取  进度 维度
     * key 高德地图PIA
     * address 地址
     * @param req
     * @param resp
     * @return
     */
    @KeyToken
    @GetMapping("selectAdress")
    public JsonResultObject<Object> selectAdress(HttpServletRequest req, HttpServletResponse resp) {

        String key=DataUtil.getString(req.getParameter("key"));
        String name=DataUtil.getString(req.getParameter("address"));
        String url="https://restapi.amap.com/v3/geocode/geo?parameters&key="+key+"&address="+name;
        String res=HttpRequestUtil.GetHttpRequest(url);
        Map<Object, Object> jsonToMap = JsonUtil.getJsonToMap(res);
        if(DataUtil.getInt(jsonToMap.get("status"))==1){
            List<Map<String, String>> geocodes = JsonUtil.getJsionStrToListMap(DataUtil.getString(jsonToMap.get("geocodes")));
            return JsonResultObject.getSuccessResult(geocodes.get(0),DataUtil.getString(jsonToMap.get("info")));
        }else {
            return JsonResultObject.getFailureResult(DataUtil.getString(jsonToMap.get("info")));
        }

    }


    /**
     * 更具经纬度获取详细地址
     * @param req
     * @param resp
     * @return
     */
    @KeyToken
    @GetMapping("selectAdressInfo")
    public JsonResultObject<Object> selectAdressInfo(HttpServletRequest req, HttpServletResponse resp) {

        String key=DataUtil.getString(req.getParameter("key"));
        String longitude=DataUtil.getString(req.getParameter("longitude"));
        String latitude=DataUtil.getString(req.getParameter("latitude"));
        String url="https://restapi.amap.com/v3/geocode/regeo?output=json&location="+latitude+","+longitude+"&key="+key;
        String res=HttpRequestUtil.GetHttpRequest(url);
        Map<Object, Object> jsonToMap = JsonUtil.getJsonToMap(res);
        if(DataUtil.getInt(jsonToMap.get("status"))==1){
            Map<Object, Object> regeocode = JsonUtil.getJsonToMap(jsonToMap.get("regeocode"));
            return JsonResultObject.getSuccessResult(regeocode.get("formatted_address"));
        }else {
            return JsonResultObject.getFailureResult(DataUtil.getString(jsonToMap.get("info")));
        }

    }
}
