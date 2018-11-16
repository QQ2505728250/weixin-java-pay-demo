package com.github.binarywang.demo.wx.pay.controller;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.github.binarywang.demo.wx.pay.domain.request.PayRequestDTO;
import com.github.binarywang.demo.wx.pay.service.PayService;
import com.github.binarywang.demo.wx.pay.utils.JsonUtil;
import com.github.binarywang.demo.wx.pay.utils.MergeImagesUtil;
import com.github.binarywang.demo.wx.pay.utils.StreamUtil;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;


/**
 * 其他测试web层
 *
 * @author denghaijing
 */
@Controller
@RequestMapping("/other")
public class OtherTestController {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(OtherTestController.class);


    @GetMapping("/test")
    @ResponseBody
    public void test() throws IOException {
        BufferedImage bi = MergeImagesUtil.buildBufferedImage(427, 100, "广工大学招聘活动小程序码", 1.0);
        ImageIO.write(bi, "png", new File("/usr/local/test/name.png"));
    }

    @GetMapping("/myfont")
    @ResponseBody
    public List<String> myfont() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fontFamilies = ge.getAvailableFontFamilyNames();
        List<String> list = new ArrayList<>();
        for (String s : fontFamilies) {
            System.out.println(s);
            list.add(s);
        }
        return list;
    }


}
