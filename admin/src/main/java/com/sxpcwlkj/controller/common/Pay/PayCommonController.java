package com.sxpcwlkj.controller.common.Pay;


import com.sxpcwlkj.annotation.AuthLoginAnnotation;
import com.sxpcwlkj.controller.common.CommonController;
import com.sxpcwlkj.entity.PayOrder;
import com.sxpcwlkj.entity.User;

import com.sxpcwlkj.service.PayService;
import com.sxpcwlkj.utils.DataUtil;
import com.sxpcwlkj.utils.EnumUtil;
import com.sxpcwlkj.utils.JsonResultPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 支付公共类
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("commonPay")
public class PayCommonController extends CommonController {
    @Autowired
    private PayService payService;

    /**
     *  创建订单
     * @param req
     * @param resp
     * @return  订单id
     */

    @GetMapping("voucher")
    @AuthLoginAnnotation(authorityCode = "/commonPay/voucher")
    public JsonResultPage queryAdvertisingPage(HttpServletRequest req, HttpServletResponse resp) {
        JsonResultPage resultJson = new JsonResultPage();
        //支付金额
        BigDecimal piceNum = DataUtil.getBigDecimal(req.getParameter("piceNum"));
        if(DataUtil.getInt(req.getParameter("piceNum"))<=0){
            resultJson.setCode(EnumUtil.Result.FAILURE.getValue());
            resultJson.setMsg("订单创建失败");
            resultJson.setState(2);
            return resultJson;
        }
        //产品id
        int productId= DataUtil.getInt(req.getParameter("productId"));
        //产品数量
        int orderCount= DataUtil.getInt(req.getParameter("orderCount"));       //充值类型  =1

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
        //充值会员id
        User user = this.getUser();
        if(user!=null){
            payOrder.setUserId(user.getUserId());
        }else {
            return null;
        }

        try {
            //添加订单
            payOrder.setSysCode(EnumUtil.SysIifo.SYSCODE.getValue());
            int num = payService.addPayOrder(payOrder);
            if (num == 1) {
                //进行支付
                resultJson.setData(payOrder.getOrderId());
                resultJson.setCode(EnumUtil.Result.SUCCESS.getValue());
                resultJson.setMsg("订单创建成功");
                resultJson.setState(1);
            } else {
                resultJson.setCode(EnumUtil.Result.FAILURE.getValue());
                resultJson.setMsg("订单创建失败");
                resultJson.setState(2);
            }
        } catch (Exception e) {
            resultJson.setCode(EnumUtil.Result.ERROR.getValue());
            e.printStackTrace();

        }

        return resultJson;

    }

}
