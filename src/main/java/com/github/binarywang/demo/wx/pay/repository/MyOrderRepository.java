package com.github.binarywang.demo.wx.pay.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.github.binarywang.demo.wx.pay.domain.MyOrder;


/**
 * 订单数据层仓库
 *
 * @author denghaijing
 */
public interface MyOrderRepository extends JpaRepository<MyOrder, Integer>, PagingAndSortingRepository<MyOrder, Integer> {

    /**
     * 根据商户订单号查询订单
     *
     * @param outTradeNo 商户订单号
     * @return MyOrder
     */
    public MyOrder findByOutTradeNo(String outTradeNo);
}
