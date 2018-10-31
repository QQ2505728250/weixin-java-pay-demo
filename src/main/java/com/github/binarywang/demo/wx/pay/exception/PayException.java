package com.github.binarywang.demo.wx.pay.exception;

/**
 * 支付异常
 *
 * @author denghaijing
 */
public class PayException extends RuntimeException {

    private final String returnCode;

    private final String returnMsg;

    public PayException(String returnCode, String returnMsg) {
        //初始化类内的全局变量。
        this.returnCode = returnCode;
        this.returnMsg = returnMsg;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }
}
