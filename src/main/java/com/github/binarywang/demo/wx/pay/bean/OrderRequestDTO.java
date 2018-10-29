package com.github.binarywang.demo.wx.pay.bean;

import lombok.Data;

/**
 * TODO 〈一句话功能简述〉
 * <p>
 * 〈功能详细描述〉 描述
 *
 * @author denghaijing
 * @see [相关类/方法][可选]
 * @since [产品/模块版本] 2018/10/29
 */
@Data
public class OrderRequestDTO {

    /**
     * 服务id
     */
    private String serviceId;

    /**
     * 用户openid
     */
    private String openId;


}
