package com.github.binarywang.demo.wx.pay.service;


import java.math.BigDecimal;
import java.util.Date;

import javax.transaction.Transactional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.binarywang.demo.wx.pay.domain.MyOrder;
import com.github.binarywang.demo.wx.pay.domain.MyService;
import com.github.binarywang.demo.wx.pay.domain.request.PayRequestDTO;
import com.github.binarywang.demo.wx.pay.exception.PayException;
import com.github.binarywang.demo.wx.pay.repository.MyOrderRepository;
import com.github.binarywang.demo.wx.pay.repository.MyServiceRepository;
import com.github.binarywang.demo.wx.pay.utils.JsonUtil;
import com.github.binarywang.demo.wx.pay.utils.UUIDUtil;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;

/**
 * 测试支付总流程service层
 *
 * @author denghaijing
 */
@Service
@Transactional
public class PayService {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PayService.class);

    @Autowired
    private WxPayService wxService;


    @Autowired
    private MyOrderRepository myOrderRepository;

    @Autowired
    private MyServiceRepository myServiceRepository;

    public Object order(PayRequestDTO dto) {
        //根据dto.getServiceId()查询服务 serviceBean
        MyService myService = this.myServiceRepository.findById(dto.getServiceId()).get();
        // 得到服务价格
        Integer price = new BigDecimal(myService.getPrice()).multiply(new BigDecimal("100")).intValue();

        //服务描述
        String body = myService.getDetails();

        //生成商户订单号入库
        String outTradeNo = UUIDUtil.generateUUID();

        //生成终端IP返回
        String spbillCreateIp = "123.12.12.123";

        WxPayUnifiedOrderRequest request = new WxPayUnifiedOrderRequest();
        request.setBody(body);
        request.setNotifyUrl("http://ipqr9z.natappfree.cc/test/pay/notify");
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

}
