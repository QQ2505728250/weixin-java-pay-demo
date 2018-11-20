package com.github.binarywang.demo.wx.pay.utils;

import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;

/**
 * <p>
 * 微信支付工具类
 *
 * @author denghaijing
 */
public class WxPayUtil {


    /**
     * 微信支付异步回调通知验证签名
     * 注意：商户key不可外传，防止数据泄漏导致出现“假通知”
     *
     * @param result sdk封装通知对象
     * @param mchKey 商户key
     * @return
     */
    public static boolean validateSign(WxPayOrderNotifyResult result, String mchKey) {
        //微信返回签名拼接
        String validateSign = "appid=" + result.getAppid() + "&bank_type=" + result.getBankType()
                + "&cash_fee=" + result.getCashFee() + "&fee_type=" + result.getFeeType()
                + "&is_subscribe=" + result.getIsSubscribe() + "&mch_id=" + result.getMchId()
                + "&nonce_str=" + result.getNonceStr() + "&openid=" + result.getOpenid()
                + "&out_trade_no=" + result.getOutTradeNo() + "&result_code=" + result.getResultCode()
                + "&return_code=" + result.getReturnCode() + "&time_end=" + result.getTimeEnd()
                + "&total_fee=" + result.getTotalFee() + "&trade_type=" + result.getTradeType()
                + "&transaction_id=" + result.getTransactionId() + "&key=" + mchKey;
        if (!result.getSign().equals(MD5Util.MD5Encode(validateSign).toUpperCase())) {
            return false;
        }
        return true;
    }

}
