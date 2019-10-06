package com.xstudio.tool.utils.wartermarker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * @author xiaobiao
 * @date 2019/1/11
 */
public class WarterMarkerUtil {
    private static Logger logger = LogManager.getLogger(WarterMarkerUtil.class);

    public static void markImageByText(TextProperty text, File srcImgPath, String newImagePath, String formatName) {
        try {
            // 1、源图片
            Image srcImg = ImageIO.read(srcImgPath);
            BufferedImage buffImg = new BufferedImage(
                    srcImg.getWidth(null),
                    srcImg.getHeight(null),
                    BufferedImage.TYPE_INT_RGB);
            // 2、得到画笔对象
            Graphics2D g = buffImg.createGraphics();
            // 3、设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(
                    srcImg.getScaledInstance(
                            srcImg.getWidth(null),
                            srcImg.getHeight(null), Image.SCALE_SMOOTH)
                    , 0, 0, null);
            // 4、设置水印旋转
            if (null != text.getRoate()) {
                g.rotate(Math.toRadians(text.getRoate().getTheta()), text.getRoate().getX(), text.getRoate().getY());
            }
            // 5、设置水印文字颜色
            g.setColor(text.getColor());
            // 6、设置水印文字Font
            g.setFont(new Font(text.getFontName(), text.getStyle(), text.getSize()));
            // 7、设置水印文字透明度
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, text.getAlpha()));
            // 8、第一参数->设置的内容，后面两个参数->文字在图片上的坐标位置(x,y)
            g.drawString(text.getText(), text.getX(), text.getY());
            // 9、释放资源
            g.dispose();
            // 10、生成图片
            OutputStream os = new FileOutputStream(newImagePath);
            ImageIO.write(buffImg, formatName, os);
        } catch (Exception e) {
            logger.error("添加水印异常", e);
        }
    }

    /**
     * 给图片添加水印文字、可设置水印文字的旋转角度
     *
     * @param text         要写入的文字
     * @param srcImgPath   源图片路径
     * @param newImagePath 新图片路径
     * @param formatName   图片后缀
     */
    public static void markImageByText(TextProperty text, String srcImgPath, String newImagePath, String formatName) {
       markImageByText(text, new File(srcImgPath), newImagePath, formatName);
    }
}
