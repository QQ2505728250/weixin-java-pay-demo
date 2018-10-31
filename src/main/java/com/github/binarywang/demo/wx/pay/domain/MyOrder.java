package com.github.binarywang.demo.wx.pay.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 订单领域对象
 *
 * @author denghaijing
 */
@Entity
@Table(name = "myorder")
@Data
public class MyOrder {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "open_id")
    private String openId;

    @Column(name = "service_id")
    private String serviceId;

    @Column(name = "out_trade_no")
    private String outTradeNo;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;


}
