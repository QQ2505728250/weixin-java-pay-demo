package com.github.binarywang.demo.wx.pay.controller;

import java.io.IOException;
import javax.servlet.ServletInputStream;
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
import com.github.binarywang.demo.wx.pay.utils.JsonUtil;
import com.github.binarywang.demo.wx.pay.utils.StreamUtil;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;


/**
 * 测试支付总流程web层
 *
 * @author denghaijing
 */
@Controller
@RequestMapping("/test/pay")
public class PayController {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PayController.class);

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
    @ResponseBody
    public ResponseEntity<Object> order(@RequestBody PayRequestDTO dto) {
        Object order = this.payService.order(dto);
        return ResponseEntity.ok().body(order);
    }

    /**
     * 支付异步通知
     *
     * @param request
     * @param response
     * @throws IOException
     * @throws WxPayException
     */
    @PostMapping("/notify")
    public void notify(HttpServletRequest request, HttpServletResponse response) throws IOException, WxPayException {
        String read = StreamUtil.read(request.getInputStream());
        WxPayOrderNotifyResult notifyResult = this.wxService.parseOrderNotifyResult(read);
        System.out.println(JsonUtil.toJson(notifyResult));

        //1、支付状态
        if (!"SUCCESS".equals(notifyResult.getReturnCode())) {
            LOGGER.error("网络异常");
            return;
        }

        //2、支付金额
        //3、支付人（下单人 == 支付人）可选

        //向微信服务器响应成功处理
//        response.getWriter().append(new StringBuffer("<xml><return_code>SUCCESS</return_code><return_msg>OK</return_msg></xml>").toString());
//        if (map != null) {
//            if (map.get("return_code").equals("SUCCESS") && map.get("return_code").equals("SUCCESS")) {
//                logger.debug("交易成功!");
//                //完善兼职支付信息
//                this.weChatService.perfectParttimePayRecord(map);
//                //向微信服务器响应成功处理
//                response.getWriter().append(new StringBuffer("<xml><return_code>SUCCESS</return_code><return_msg>OK</return_msg></xml>").toString());
//            } else {
//                this.weChatService.deleteParttimeAndPayRecord(parttimePayRecord.getOutTradeNo(), parttimePayRecord.getParttimeId());
//                logger.error("业务结果不正确!");
//            }
//        } else {
//            this.weChatService.deleteParttimeAndPayRecord(parttimePayRecord.getOutTradeNo(), parttimePayRecord.getParttimeId());
//            logger.error("签名失败!");
//        }
    }

}
