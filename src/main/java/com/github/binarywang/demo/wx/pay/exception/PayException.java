package com.github.binarywang.demo.wx.pay.exception;

/**
 * 支付异常
 *
 * @author denghaijing
 */
public class PayException extends RuntimeException {


    private final String msg;

    public PayException(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
