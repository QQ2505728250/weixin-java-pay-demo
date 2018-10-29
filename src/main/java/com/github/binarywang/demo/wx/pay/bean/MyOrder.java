package com.github.binarywang.demo.wx.pay.bean;

import io.swagger.models.auth.In;
import lombok.Data;

/**
 * 统一下单参数类
 *
 * @author denghaijing
 */
@Data
public class MyOrder {

    private String openid;

    private String body;

    private String outTradeNo;

    private Integer totalFee;

    private String spbillCreateIp;

    private String notifyUrl;

    private String tradeType;

}
