package com.github.binarywang.demo.wx.pay.utils;


import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 字体工具类
 *
 * @author jane
 */
public class FontsUtil {

    private static final Logger logger = LoggerFactory.getLogger(FontsUtil.class);

    /**
     * 宋体
     *
     * @param style
     * @param size
     */
    public static Font getSimsun(int style, float size) {

        Font font = null;
        //获取字体流
        InputStream simsunFontFile = FontsUtil.class.getResourceAsStream("/fonts/simkai.ttf");
        try {
            //创建字体
            font = Font.createFont(Font.PLAIN, simsunFontFile).deriveFont(style, size);
        } catch (FontFormatException e) {
            logger.error("", e);
        } catch (IOException e) {
            logger.error("", e);
        }
        return font;
    }

    public static Font getMyFont(String fontName,float fontSize) {

        String fontPath = FontsUtil.class.getClassLoader().getResource("fonts/"+fontName).getFile();
//        String fontPath = FontsUtil.class.getClassLoader().getResource("fonts/"+fontName).getFile();
        Font myFont = null;
        try {
            //创建字体
            myFont = Font.createFont(Font.TRUETYPE_FONT, new File(fontPath));
            //设置大小
            myFont = myFont.deriveFont(fontSize);
        } catch (FontFormatException e) {
            logger.error("Custom font exception", e);
        } catch (IOException e) {
            logger.error("Custom font exception", e);
        }
        return myFont;

    }


    public static void main(String[] args) {
        Font font1 = getSimsun(10, 30.0f);
        System.out.println(font1);
//        Font font2 = getMyFont("simkai.ttf",30.0f);
//        System.out.println(font2);


    }

}
