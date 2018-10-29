package com.github.binarywang.demo.wx.pay.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import com.github.binarywang.demo.wx.pay.bean.MyOrder;
import com.github.binarywang.demo.wx.pay.bean.OrderRequestDTO;

/**
 * TODO 〈一句话功能简述〉
 * <p>
 * 〈功能详细描述〉 描述
 *
 * @author denghaijing
 * @see [相关类/方法][可选]
 * @since [产品/模块版本] 2018/10/29
 */
@Service
public class PayService {

    public Object order(OrderRequestDTO dto) {

        //根据dto.getServiceId()查询服务 serviceBean

        // 得到服务价格
        Integer price = 1;

        //服务描述
        String body = "服务描述";

        //生成商户订单号入库
        String outTradeNo = "201810291660949702";

        //由dto.getOpenId() 、服务价格 、outTradeNo 入库（订单表）

        //生成终端IP返回
        String spbillCreateIp = "123.12.12.123";

        //组装返回数据
        MyOrder myOrder = new MyOrder();
        myOrder.setBody(body);
        myOrder.setNotifyUrl("http://localhost:8082/pay/test");
        myOrder.setOpenid(dto.getOpenId());
        myOrder.setOutTradeNo(outTradeNo);
        myOrder.setSpbillCreateIp(spbillCreateIp);
        myOrder.setTotalFee(price);
        myOrder.setTradeType("JSAPI");
        return myOrder;
    }
}
