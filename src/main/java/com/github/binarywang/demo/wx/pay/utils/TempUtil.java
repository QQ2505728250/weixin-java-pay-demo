package com.github.binarywang.demo.wx.pay.utils;

/**
 * TODO 〈一句话功能简述〉
 * <p>
 * 〈功能详细描述〉 描述
 *
 * @author denghaijing
 * @see [相关类/方法][可选]
 * @since [产品/模块版本] 2018/11/26
 */
public class TempUtil {
    private static final Double MONEY_RANGE = 0.01;

    /**
     * 比较2个Double类型金额在偏差值里是否相等
     *
     * @param d1
     * @param d2
     * @return
     */
    public static Boolean equals(Double d1, Double d2) {
        Double result = Math.abs(d1 - d2);
        if (result < MONEY_RANGE) {
            return true;
        } else {
            return false;
        }
    }
}
