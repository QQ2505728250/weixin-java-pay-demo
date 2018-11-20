package com.github.binarywang.demo.wx.pay.exception;

/**
 * 订单不存在异常
 *
 * @author denghaijing
 */
public class MyOrderNotExistException extends RuntimeException {


    private final String msg;

    public MyOrderNotExistException(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
