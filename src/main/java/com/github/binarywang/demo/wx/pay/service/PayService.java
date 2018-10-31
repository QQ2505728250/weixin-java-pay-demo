package com.github.binarywang.demo.wx.pay.service;


import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.binarywang.demo.wx.pay.domain.MyService;
import com.github.binarywang.demo.wx.pay.domain.request.PayRequestDTO;
import com.github.binarywang.demo.wx.pay.exception.PayException;
import com.github.binarywang.demo.wx.pay.repository.MyOrderRepository;
import com.github.binarywang.demo.wx.pay.repository.MyServiceRepository;
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
public class PayService {

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

        //由dto.getOpenId() 、服务价格 、outTradeNo 入库（订单表）

        //生成终端IP返回
        String spbillCreateIp = "123.12.12.153343523";

        WxPayUnifiedOrderRequest request = new WxPayUnifiedOrderRequest();
        request.setBody(body);
        request.setNotifyUrl("http://localhost:8082/pay/test");
        request.setOpenid(dto.getOpenId());
        request.setOutTradeNo(outTradeNo);
        request.setSpbillCreateIp(spbillCreateIp);
        request.setTotalFee(price);
        request.setTradeType("JSAPI");

        Object o = null;
        try {
            o = this.wxService.createOrder(request);
        } catch (WxPayException e) {
            throw new PayException(e.getReturnCode(), e.getReturnMsg());
        }
        return o;
    }

}
