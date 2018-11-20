package com.github.binarywang.demo.wx.pay.service;


import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.binarywang.demo.wx.pay.config.WxPayProperties;
import com.github.binarywang.demo.wx.pay.domain.MyOrder;
import com.github.binarywang.demo.wx.pay.domain.MyService;
import com.github.binarywang.demo.wx.pay.domain.request.PayRequestDTO;
import com.github.binarywang.demo.wx.pay.exception.PayException;
import com.github.binarywang.demo.wx.pay.repository.MyOrderRepository;
import com.github.binarywang.demo.wx.pay.repository.MyServiceRepository;
import com.github.binarywang.demo.wx.pay.utils.JsonUtil;
import com.github.binarywang.demo.wx.pay.utils.StreamUtil;
import com.github.binarywang.demo.wx.pay.utils.UUIDUtil;
import com.github.binarywang.demo.wx.pay.utils.WxPayUtil;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import org.slf4j.Logger;

/**
 * 测试支付总流程service层
 *
 * @author denghaijing
 */
@Service
@Transactional
public class PayService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PayService.class);

    @Autowired
    private WxPayService wxService;

    @Autowired
    private WxPayProperties wxPayProperties;


    @Autowired
    private MyOrderRepository myOrderRepository;

    @Autowired
    private MyServiceRepository myServiceRepository;

    public Object order(PayRequestDTO dto) {
        //根据dto.getServiceId()查询服务 serviceBean
        MyService myService = this.myServiceRepository.findById(dto.getServiceId()).get();
        // 得到服务价格
        Integer price = myService.getPrice().multiply(new BigDecimal("100")).intValue();

        //服务描述
        String body = myService.getDetails();

        //生成商户订单号入库
        String outTradeNo = UUIDUtil.generateUUID();

        //生成终端IP返回
        String spbillCreateIp = "123.12.12.123";

        WxPayUnifiedOrderRequest request = new WxPayUnifiedOrderRequest();
        request.setBody(body);
        request.setNotifyUrl("http://8dprvk.natappfree.cc/test/pay/notify");
        request.setOpenid(dto.getOpenId());
        request.setOutTradeNo(outTradeNo);
        request.setSpbillCreateIp(spbillCreateIp);
        request.setTotalFee(price);
        request.setTradeType("JSAPI");

        Object o = null;
        try {
            o = this.wxService.createOrder(request);
        } catch (WxPayException e) {
            //当异常，直接关闭微信服务端的订单；因为sdk关闭订单接口对订单状态或是否存在无要求，所以无需查询订单接口
//            WxPayOrderCloseRequest closeRequest = new WxPayOrderCloseRequest();
//            closeRequest.setOutTradeNo(outTradeNo);
//            this.wxService.closeOrder(closeRequest);
            LOGGER.error("ReturnCode:{},ReturnMsg:{}" + e.getReturnCode(), e.getReturnMsg());
            throw new PayException("服务器连接超时，稍候重试");
        }

        //订单入库
        MyOrder myOrder = new MyOrder();
        myOrder.setOpenId(dto.getOpenId());
        myOrder.setOutTradeNo(outTradeNo);
        myOrder.setIsValid(1);
        myOrder.setServiceId(dto.getServiceId());
        myOrder.setState(1);
        myOrder.setCreateTime(new Date());
        myOrder.setUpdateTime(new Date());
        MyOrder saveMyOrder = this.myOrderRepository.save(myOrder);
        System.out.println(JsonUtil.toJson(saveMyOrder));
        if (saveMyOrder == null) {
            throw new RuntimeException("保存订单异常");
        }
        return o;
    }

    public void dealNotify(HttpServletRequest request, HttpServletResponse response) throws IOException, WxPayException {
        String read = StreamUtil.read(request.getInputStream());
        WxPayOrderNotifyResult notifyResult = this.wxService.parseOrderNotifyResult(read);
        notifyResult.getResultCode();
        //1、判断是否通信成功，若通信失败不走以下程序
        if (!"SUCCESS".equals(notifyResult.getReturnCode())) {
            LOGGER.warn("服务器网络异常");
            return;
        }
        //2、检验签名
        if (!WxPayUtil.validateSign(notifyResult, wxPayProperties.getMchKey())) {
            LOGGER.warn("签名不正确,假通知");
            return;
        }
        //3、支付状态
        if (!"SUCCESS".equals(notifyResult.getResultCode())) {
            //调用关闭订接口,更改订单失效，小程序端需要重新调用统一下单接口
            LOGGER.warn("业务结果不正确");
            return;
        }
        //4、支付金额
        if (!WxPayUtil.validateTotalFee(notifyResult.getTotalFee(), new BigDecimal("0.01"))) {
            //调用关闭订接口,更改订单失效，小程序端需要重新调用统一下单接口
            LOGGER.warn("支付金额不正确");
            return;
        }
        //TODO 5、支付人（下单人 == 支付人）可选

        //更改订单状态

        //向微信服务器响应成功处理
        response.getWriter().append(new StringBuffer("<xml><return_code>SUCCESS</return_code><return_msg>OK</return_msg></xml>").toString());

    }
}
