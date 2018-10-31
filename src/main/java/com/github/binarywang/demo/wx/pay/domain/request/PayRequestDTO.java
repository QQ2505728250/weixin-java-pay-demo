package com.github.binarywang.demo.wx.pay.domain.request;

import lombok.Data;

/**
 * 支付请求DTO
 *
 * @author denghaijing
 */
@Data
public class PayRequestDTO {

    /**
     * 服务id
     */
    private Integer serviceId;

    /**
     * 用户openid
     */
    private String openId;


}
