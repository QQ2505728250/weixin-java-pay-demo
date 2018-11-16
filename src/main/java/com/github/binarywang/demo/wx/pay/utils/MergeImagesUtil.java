package com.github.binarywang.demo.wx.pay.utils;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 合并两张图片工具类。
 * <p>
 *
 * @author denghaijing
 */
public class MergeImagesUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(MergeImagesUtil.class);

    public static void main(String[] args) throws IOException {

//        //模拟已生成的小程序码File
//        File qrCodeImageFile = new File("D:/imgs/test2/test.jpg");
//
//        BufferedImage bi = buildBufferedImage(ImageIO.read(qrCodeImageFile).getWidth(), 100, "广工大学招聘活动小程序码", 1.0);
//
//        //调用方法生成图片
//        byte[] b = mergeImages(qrCodeImageFile, bi);
//        System.out.println(b.length);
        BufferedImage bi = buildBufferedImage(430, 100, "广工大学招聘活动小程序码招聘活动小程序码", 1.5);

//        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//        String[] fontFamilies = ge.getAvailableFontFamilyNames();
//        for (String s : fontFamilies) {
//            System.out.println(s);
//        }

//        test01();

    }


    /**
     * 合并两张图片
     * 注意：宽度要保持一致，否则数组越界异常
     *
     * @param file          小程序二维码File
     * @param bufferedImage 需合并图片bufferedImage
     * @return 异常返回null, 成功返回byte[]
     */
    public static byte[] mergeImages(File file, BufferedImage bufferedImage) {

        BufferedImage[] images = new BufferedImage[2];
        try {
            images[0] = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("File读入BufferedImage异常");
        }
        images[1] = bufferedImage;

        int[][] imageArrays = new int[2][];
        for (int i = 0; i < images.length; i++) {
            int width = images[i].getWidth();
            int height = images[i].getHeight();
            // 从图片中读取RGB
            imageArrays[i] = new int[width * height];
            imageArrays[i] = images[i].getRGB(0, 0, width, height, imageArrays[i], 0, width);
        }

        //计算高度
        int dstHeight = 0;
        int dstWidth = images[0].getWidth();
        for (int i = 0; i < images.length; i++) {
            dstWidth = dstWidth > images[i].getWidth() ? dstWidth : images[i].getWidth();
            dstHeight += images[i].getHeight();
        }
        LOGGER.debug("" + dstWidth);
        LOGGER.debug("" + dstHeight);
        if (dstHeight < 1) {
            LOGGER.error("The height cannot be less than 1");
            return null;
        }

        //生成合并后的图片byte数组，可存入数据库
        byte[] imageInByte = null;
        // 生成新图片
        try {
            BufferedImage imageNew = new BufferedImage(dstWidth, dstHeight, BufferedImage.TYPE_INT_RGB);
            int height_i = 0;
            for (int i = 0; i < images.length; i++) {
                imageNew.setRGB(0, height_i, dstWidth, images[i].getHeight(), imageArrays[i], 0, dstWidth);
                height_i += images[i].getHeight();
            }

            //输出到指定磁盘目录,可安全删除
            ImageIO.write(imageNew, "png", new File("D:/imgs/test2/build.png"));

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(imageNew, "png", baos);
            baos.flush();
            imageInByte = baos.toByteArray();
            LOGGER.debug("合并生成的新图片byte长度:{}", imageInByte.length);
            baos.close();
        } catch (Exception e) {
            LOGGER.error("合并生成的新图片异常");
            e.printStackTrace();
            return null;
        }

        return imageInByte;
    }


    /**
     * 通过指定参数生成图片BufferedImage
     *
     * @param width  宽度
     * @param height 高度
     * @param text   文字内容
     * @param rate   文字行距
     * @return BufferedImage
     */
    public static BufferedImage buildBufferedImage(Integer width, Integer height, String text, Double rate) throws IOException {

        //RGB形式
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) bi.getGraphics();
        //设置背景色
        g2.setBackground(Color.WHITE);
        //通过使用当前绘图表面的背景色进行填充来清除指定的矩形。
        g2.clearRect(0, 0, width, height);
        //设置画笔
        g2.setPaint(Color.RED);
        //设置字体
        g2.setFont(FontsUtil.getSimsun(10, 20.0f));  //20影响 tempX 的递增量  ， 同时rate间距比率也影响，，，tempX 的递增量 = 20.0*rate

        // 抗锯齿
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //居中显示
        int x = (int) (width / 2 - rate * g2.getFontMetrics().stringWidth(text) / 2);
        int y = height / 2 + g2.getFontMetrics().getHeight() / 3;

        String tempStr;
        int orgStringWight = g2.getFontMetrics().stringWidth(text);
        int orgStringLength = text.length();
        //当第一个字x轴为负值时，重置为0
        int tempX = x < 0 ? 0 : x;
        int tempY = y;

        System.out.println("第一个字x轴:" + tempX);
        System.out.println("第一个字y轴:" + tempY);
//        if(text.length())
        while (text.length() > 0) {
            //取得第一个字
            tempStr = text.substring(0, 1);
            //总字数减一
            text = text.substring(1, text.length());
            System.out.println("tempStr画文字:  " + tempStr);
            //画文字
            g2.drawString(tempStr, tempX, tempY);
//            int hh1 = tempX;
            System.out.println("文字tempX坐标:  " + tempX);
            tempX = (int) (tempX + (double) orgStringWight / (double) orgStringLength * rate);
//            int hh2 = tempX;
            //当文字tempX坐标超图片总长度时
//            if (tempX + (hh2 - hh1) > width) {
//                System.out.println("---------------这个字超出:  " + tempStr);
//            }
//            System.out.println("每一个字间距：" + (hh2 - hh1));

        }


//        //输出到指定磁盘目录,可安全删除
        ImageIO.write(bi, "png", new File("D:/imgs/test2/name.png"));

        g2.dispose();

        return bi;
    }

    public static void test01() {
        int width = 50;
        int height = 100;
        String s = "广工大学招聘活动小程序码";

        File file = new File("D:/imgs/test2/build.jpg");

        Font font = FontsUtil.getMyFont("simkai.ttf", 18.0f);
        //创建一个画布
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //获取画布的画笔
        Graphics2D g2 = (Graphics2D) bi.getGraphics();

        //开始绘图
        g2.setBackground(Color.WHITE);
        g2.clearRect(0, 0, width, height);
        g2.setPaint(new Color(0, 0, 255));
        g2.fillRect(0, 0, 100, 10);
        g2.setPaint(new Color(253, 2, 0));
        g2.fillRect(0, 10, 100, 10);
        g2.setPaint(Color.red);


        FontRenderContext context = g2.getFontRenderContext();
        Rectangle2D bounds = font.getStringBounds(s, context);
        double x = (width - bounds.getWidth()) / 2;
        double y = (height - bounds.getHeight()) / 2;
        double ascent = -bounds.getY();
        double baseY = y + ascent;

        //绘制字符串
        g2.drawString(s, (int) x, (int) baseY);

        try {
            //将生成的图片保存为jpg格式的文件。ImageIO支持jpg、png、gif等格式
            ImageIO.write(bi, "jpg", file);
        } catch (IOException e) {
            System.out.println("生成图片出错........");
            e.printStackTrace();
        }

    }


}