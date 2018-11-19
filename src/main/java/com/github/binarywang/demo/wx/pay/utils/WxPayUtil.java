package com.github.binarywang.demo.wx.pay.utils;

import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;

/**
 * <p>
 * 微信支付工具类
 *
 * @author denghaijing
 */
public class WxPayUtil {


    public static boolean checkSign(WxPayOrderNotifyResult result, String mchKey) {

        //微信返回签名
        String checkSign = "appid=" + result.getAppid() + "&bank_type=" + result.getBankType()
                + "&cash_fee=" + result.getCashFee() + "&fee_type=" + result.getFeeType()
                + "&is_subscribe=" + result.getIsSubscribe() + "&mch_id=" + result.getMchId()
                + "&nonce_str=" + result.getNonceStr() + "&openid=" + result.getOpenid()
                + "&out_trade_no=" + result.getOutTradeNo() + "&result_code=" + result.getResultCode()
                + "&return_code=" + result.getReturnCode() + "&time_end=" + result.getTimeEnd()
                + "&total_fee=" + result.getTotalFee() + "&trade_type=" + result.getTradeType()
                + "&transaction_id=" + result.getTransactionId() + "&key=" + mchKey;

        return true;

    }
}
