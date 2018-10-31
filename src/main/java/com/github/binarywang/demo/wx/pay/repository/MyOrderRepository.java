package com.github.binarywang.demo.wx.pay.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.github.binarywang.demo.wx.pay.domain.MyOrder;


/**
 * 服务数据层仓库
 *
 * @author denghaijing
 */
public interface MyOrderRepository extends JpaRepository<MyOrder, Integer>, PagingAndSortingRepository<MyOrder, Integer> {

}
