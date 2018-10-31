package com.github.binarywang.demo.wx.pay.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 服务领域对象
 *
 * @author denghaijing
 */
@Entity
@Table(name = "myservice")
@Data
public class MyService {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "price")
    private Double price;

    @Column(name = "details")
    private String details;


}
