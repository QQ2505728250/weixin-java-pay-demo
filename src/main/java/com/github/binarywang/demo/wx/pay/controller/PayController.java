package com.github.binarywang.demo.wx.pay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.binarywang.demo.wx.pay.bean.OrderRequestDTO;
import com.github.binarywang.demo.wx.pay.service.PayService;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;

/**
 * TODO 〈一句话功能简述〉
 * <p>
 * 〈功能详细描述〉 描述
 *
 * @author denghaijing
 * @see [相关类/方法][可选]
 * @since [产品/模块版本] 2018/10/29
 */
@Controller
@RequestMapping("/test/pay")
public class PayController {

    private WxPayService wxService;

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
     * @throws WxPayException
     */
    @PostMapping("/order")
    public Object order(@RequestBody OrderRequestDTO dto){
        return this.payService.order(dto);
    }

    @PostMapping("/createOrder")
    public <T> T createOrder(@RequestBody WxPayUnifiedOrderRequest request) throws WxPayException {
        return this.wxService.createOrder(request);
    }
}
