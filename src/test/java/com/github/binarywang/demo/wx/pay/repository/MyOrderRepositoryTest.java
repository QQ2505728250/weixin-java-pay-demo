package com.github.binarywang.demo.wx.pay.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.github.binarywang.demo.wx.pay.WxPayDemoApplication;
import com.github.binarywang.demo.wx.pay.domain.MyOrder;
import com.github.binarywang.demo.wx.pay.utils.JsonUtil;

/**
 * 订单数据层仓库测试类
 * <p>
 *
 * @author denghaijing
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = WxPayDemoApplication.class)
public class MyOrderRepositoryTest {

    @Autowired
    MyOrderRepository myOrderRepository;


    @Test
    public void findByOutTradeNo() {
        MyOrder myOrder = this.myOrderRepository.findByOutTradeNo("a54c7ff8ee6e432aaa6d31ab3f1b86d1");
        System.out.println(JsonUtil.toJson(myOrder));

    }
}