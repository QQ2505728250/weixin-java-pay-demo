package com.github.binarywang.demo.wx.pay.utils;

import java.awt.*;
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

        BufferedImage bi = buildBufferedImage(430, 100, "广工大学招聘活动小程序码招聘活动", 1.0);

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
            ImageIO.write(imageNew, "png", new File("D:/imgs/test2/name.png"));

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
        g2.setFont(FontsUtil.getSimsun(10, 20.0f));
        // 抗锯齿
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        String tempStr;
        int orgStringWight = g2.getFontMetrics().stringWidth(text);
        //文字长度
        int orgStringLength = text.length();
        //文字长度相对于图片宽度应该有多少行
        int line = orgStringWight / width + 1;

        //初始化x,y坐标值
        int x = (int) (width / 2 - rate * g2.getFontMetrics().stringWidth(text) / 2);
        int y = height / (line + 1) + g2.getFontMetrics().getHeight() / 3;

        //当第一个字x轴为负值时，重置为0
        int tempX = x < 0 ? 0 : x;
        int tempY = y;
        //文字间距
        double spacing = (double) orgStringWight / (double) orgStringLength * rate;

        while (text.length() > 0) {
            //取得第一个字
            tempStr = text.substring(0, 1);
            //总文本数减一个
            text = text.substring(1, text.length());
            //画文字
            g2.drawString(tempStr, tempX, tempY);
            tempX = (int) (tempX + spacing);
            //自动换行，重新设置参数
            if ((tempX + spacing) > width ) {
                tempX = 0;
                tempY = tempY + 20;
            }
        }

        //输出到指定磁盘目录,可安全删除
        ImageIO.write(bi, "png", new File("D:/imgs/test2/name.png"));

        g2.dispose();

        return bi;
    }

}