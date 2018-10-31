package com.github.binarywang.demo.wx.pay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.github.binarywang.demo.wx.pay.domain.request.PayRequestDTO;
import com.github.binarywang.demo.wx.pay.service.PayService;
import com.github.binarywang.demo.wx.pay.utils.ErrorDTO;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;

/**
 * 测试支付总流程web层
 *
 * @author denghaijing
 */
@RestController
@RequestMapping("/test/pay")
public class PayController {

    private WxPayService wxService;

    @Autowired
    private PayService payService;

    @Autowired
    public PayController(WxPayService wxService) {
        this.wxService = wxService;
    }


    /**
     * 订单入库
     *
     * @param dto
     * @return
     */
    @PostMapping("/order")
    public ResponseEntity<Object> order(@RequestBody PayRequestDTO dto) {
        Object order = this.payService.order(dto);
        return ResponseEntity.ok().body(order);
    }


    @PostMapping("/createOrder")
    public <T> T createOrder(WxPayUnifiedOrderRequest request) throws WxPayException {
        return this.wxService.createOrder(request);
    }
}
