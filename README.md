#### 本Demo基于Spring Boot构建，演示微信支付后端接口使用方法。

[![Build Status](https://travis-ci.org/binarywang/weixin-java-pay-demo.svg?branch=master)](https://travis-ci.org/binarywang/weixin-java-pay-demo)
-----------------------

## 使用步骤：
1. 配置：复制`/src/main/resources/application.yml.template`或者修改其扩展名生成application.yml文件，并根据自己需要填写相关配置，其中支付相关参数含义请参考WxPayProperties类中的注释（需要注意的是：yml文件内的属性冒号后面的文字之前需要加空格，可参考已有配置，否则属性会设置不成功）；	
1. 其中各参数的含义请参考 /src/main/java/com/github/binarywang/demo/wxpay/config/WxPayProperties.java 文件里的注释；
1. 需要特别注意的，是sub开头的两个参数属于服务商模式使用的，如果是普通模式，请不要配置这两个参数，最好从配置文件中移除相关项；
1. 查看`WxPayController`类代码，根据自己需要修改调整相关实现；
1. 运行Java程序：`WxPayDemoApplication`；
1. 打开网页浏览器，输入想要访问的地址，比如`http://localhost:8080/pay/closeOrder/123`查看效果。
	
## 个人总结：
1.小程序支付成功：(createOrder)
参数 ：{
​    	"openid":"o_4nS5LcinmATINYVUti4-f_n9mM",
​    	"body":"测试",
​    	"outTradeNo":"20181029125346",
​    	"totalFee":1,
​    	"spbillCreateIp":"123.12.12.123",
​    	"notifyUrl":"http://localhost:8082/pay/test",
​    	"tradeType":"JSAPI"
​    }
返回 ：{
​        "appId": "wx99285903f308b094",
​        "timeStamp": "1540779950",
​        "nonceStr": "1540779950709",
​        "packageValue": "prepay_id=wx291025522405308460c6c8920822835822",
​        "signType": "MD5",
​        "paySign": "AFC044BEB51CF99012CADB210C4D26D7"
​    }
示例： payoff: function () {
​       var that = this;
​       wx.request({
​         url: 'http://localhost:8082/pay/createOrder',
​         method: 'POST',
​         header: {
​           'content-type': 'application/json'
​         },
​         data: {
​           "openid": "o_4nS5LcinmATINYVUti4-f_n9mM",
​           "body": "测试",
​           "outTradeNo": "20181029125346",
​           "totalFee": 1,
​           "spbillCreateIp": "123.12.12.123",
​           "notifyUrl": "http://localhost:8082/pay/test",
​           "tradeType": "JSAPI"
​         },
​         success: function (res) {
​           console.log(res.data);
​           that.requestPayment(res.data);
​         }
​       })

     },
     requestPayment: function (obj) {
       var that = this;
       wx.requestPayment({
         'timeStamp': obj.timeStamp,
         'nonceStr': obj.nonceStr,
         'package': obj.packageValue,
         'signType': obj.signType,
         'paySign': obj.paySign,
         'success': function (res) {
           console.log("成功！");
         },
         'fail': function (res) {
           console.log("失败！");
         }
       })
     }   
2.查询订单接口：
2.1、支付成功
{
​    "returnCode": "SUCCESS",
​    "returnMsg": "OK",
​    "resultCode": "SUCCESS",
​    "errCode": null,
​    "errCodeDes": null,
​    "appid": "wx99285903f308b094",
​    "mchId": "1503185681",
​    "subAppId": null,
​    "subMchId": null,
​    "nonceStr": "GhQYaMYVYqjnGYVa",
​    "sign": "6D550036E4869593FF589C018209E4C5",
​    "xmlString": "<xml><return_code><![CDATA[SUCCESS]]></return_code>\n<return_msg><![CDATA[OK]]></return_msg>\n<appid><![CDATA[wx99285903f308b094]]></appid>\n<mch_id><![CDATA[1503185681]]></mch_id>\n<nonce_str><![CDATA[GhQYaMYVYqjnGYVa]]></nonce_str>\n<sign><![CDATA[6D550036E4869593FF589C018209E4C5]]></sign>\n<result_code><![CDATA[SUCCESS]]></result_code>\n<openid><![CDATA[o_4nS5LcinmATINYVUti4-f_n9mM]]></openid>\n<is_subscribe><![CDATA[N]]></is_subscribe>\n<trade_type><![CDATA[JSAPI]]></trade_type>\n<bank_type><![CDATA[CFT]]></bank_type>\n<total_fee>1</total_fee>\n<fee_type><![CDATA[CNY]]></fee_type>\n<transaction_id><![CDATA[4200000169201810095626345756]]></transaction_id>\n<out_trade_no><![CDATA[0f013c80ac7b49128fdf93fec014c4c6]]></out_trade_no>\n<attach><![CDATA[]]></attach>\n<time_end><![CDATA[20181009095519]]></time_end>\n<trade_state><![CDATA[SUCCESS]]></trade_state>\n<cash_fee>1</cash_fee>\n<trade_state_desc><![CDATA[支付成功]]></trade_state_desc>\n</xml>",
​    "promotionDetail": null,
​    "deviceInfo": null,
​    "openid": "o_4nS5LcinmATINYVUti4-f_n9mM",
​    "isSubscribe": "N",
​    "tradeType": "JSAPI",
​    "tradeState": "SUCCESS",
​    "bankType": "CFT",
​    "totalFee": 1,
​    "settlementTotalFee": null,
​    "feeType": "CNY",
​    "cashFee": 1,
​    "cashFeeType": null,
​    "couponFee": null,
​    "couponCount": null,
​    "coupons": null,
​    "transactionId": "4200000169201810095626345756",
​    "outTradeNo": "0f013c80ac7b49128fdf93fec014c4c6",
​    "attach": "",
​    "timeEnd": "20181009095519",
​    "tradeStateDesc": "支付成功"
}

2.2、订单未支付
{
​    "returnCode": "SUCCESS",
​    "returnMsg": "OK",
​    "resultCode": "SUCCESS",
​    "errCode": null,
​    "errCodeDes": null,
​    "appid": "wx99285903f308b094",
​    "mchId": "1503185681",
​    "subAppId": null,
​    "subMchId": null,
​    "nonceStr": "BVfzXqRZ2mWkj3QH",
​    "sign": "7C1F03EE142D0BBBB55BAF2837368966",
​    "xmlString": "<xml><return_code><![CDATA[SUCCESS]]></return_code>\n<return_msg><![CDATA[OK]]></return_msg>\n<appid><![CDATA[wx99285903f308b094]]></appid>\n<mch_id><![CDATA[1503185681]]></mch_id>\n<nonce_str><![CDATA[BVfzXqRZ2mWkj3QH]]></nonce_str>\n<sign><![CDATA[7C1F03EE142D0BBBB55BAF2837368966]]></sign>\n<result_code><![CDATA[SUCCESS]]></result_code>\n<out_trade_no><![CDATA[02a79385268b4d768b38a85d758fcd28]]></out_trade_no>\n<trade_state><![CDATA[NOTPAY]]></trade_state>\n<trade_state_desc><![CDATA[订单未支付]]></trade_state_desc>\n</xml>",
​    "promotionDetail": null,
​    "deviceInfo": null,
​    "openid": null,
​    "isSubscribe": null,
​    "tradeType": null,
​    "tradeState": "NOTPAY",
​    "bankType": null,
​    "totalFee": null,
​    "settlementTotalFee": null,
​    "feeType": null,
​    "cashFee": null,
​    "cashFeeType": null,
​    "couponFee": null,
​    "couponCount": null,
​    "coupons": null,
​    "transactionId": null,
​    "outTradeNo": "02a79385268b4d768b38a85d758fcd28",
​    "attach": null,
​    "timeEnd": null,
​    "tradeStateDesc": "订单未支付"
}
应用场景：
​     * ◆ 当商户后台、网络、服务器等出现异常，商户系统最终未接收到支付通知；（是指开发者服务器？）
​     * ◆ 调用支付接口后，返回系统错误或未知交易状态情况；
​     * ◆ 调用被扫支付API，返回USERPAYING的状态；
​     * ◆ 调用关单或撤销接口API之前，需确认支付状态；
支付成功： "resultCode": "SUCCESS", "returnCode": "SUCCESS", "tradeState": "SUCCESS", "tradeStateDesc": "支付成功"
支付取消： "resultCode": "SUCCESS", "returnCode": "SUCCESS",  "tradeState": "NOTPAY",  "tradeStateDesc": "订单未支付"


3.企业付款到用户零钱
{
​    "returnCode": "SUCCESS",
​    "returnMsg": "",
​    "resultCode": "SUCCESS",
​    "errCode": null,
​    "errCodeDes": null,
​    "appid": null,
​    "mchId": "1503185681",
​    "subAppId": null,
​    "subMchId": null,
​    "nonceStr": "1539049400432",
​    "sign": null,
​    "xmlString": "<xml>\n<return_code><![CDATA[SUCCESS]]></return_code>\n<return_msg><![CDATA[]]></return_msg>\n<mch_appid><![CDATA[wx4bafad42722b0622]]></mch_appid>\n<mchid><![CDATA[1503185681]]></mchid>\n<device_info><![CDATA[013467007045764]]></device_info>\n<nonce_str><![CDATA[1539049400432]]></nonce_str>\n<result_code><![CDATA[SUCCESS]]></result_code>\n<partner_trade_no><![CDATA[10ff3c39xcl7ki35nte5pagj6zcfxo05]]></partner_trade_no>\n<payment_no><![CDATA[1000018301201810092669402502]]></payment_no>\n<payment_time><![CDATA[2018-10-09 09:43:26]]></payment_time>\n</xml>",
​    "mchAppid": "wx4bafad42722b0622",
​    "deviceInfo": "013467007045764",
​    "partnerTradeNo": "10ff3c39xcl7ki35nte5pagj6zcfxo05",
​    "paymentNo": "1000018301201810092669402502",
​    "paymentTime": "2018-10-09 09:43:26"
}