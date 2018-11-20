package com.github.binarywang.demo.wx.pay.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.github.binarywang.demo.wx.pay.domain.request.PayRequestDTO;
import com.github.binarywang.demo.wx.pay.service.PayService;
import com.github.binarywang.wxpay.exception.WxPayException;
import org.slf4j.Logger;


/**
 * 测试支付总流程web层
 *
 * @author denghaijing
 */
@Controller
@RequestMapping("/test/pay")
public class PayController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PayController.class);

    @Autowired
    private PayService payService;

    /**
     * 统一下单并订单入库处理
     *
     * @param dto
     * @return
     */
    @PostMapping("/order")
    @ResponseBody
    public ResponseEntity<Object> dealOrder(@RequestBody PayRequestDTO dto) {
        Object order = this.payService.order(dto);
        return ResponseEntity.ok().body(order);
    }

    /**
     * 支付异步通知处理
     *
     * @param request
     * @param response
     * @throws IOException
     * @throws WxPayException
     */
    @PostMapping("/notify")
    public void dealNotify(HttpServletRequest request, HttpServletResponse response) throws IOException, WxPayException {
        this.payService.dealNotify(request, response);
    }

}
