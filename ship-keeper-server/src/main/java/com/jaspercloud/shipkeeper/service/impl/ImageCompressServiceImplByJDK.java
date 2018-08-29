package com.jaspercloud.shipkeeper.service.impl;

import com.jaspercloud.shipkeeper.entity.ImageSize;
import com.jaspercloud.shipkeeper.exception.ImageCompressException;
import com.jaspercloud.shipkeeper.service.ImageCompressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

@Service
public class ImageCompressServiceImplByJDK implements ImageCompressService {

    private static Logger logger = LoggerFactory.getLogger(ImageCompressServiceImplByJDK.class);

    @Override
    public byte[] compress(String url, int w, int h) throws Exception {
        byte[] bytes = compress(url, w, h, false);
        return bytes;
    }

    @Override
    public byte[] compress(String url, int w, int h, boolean overflow) throws Exception {
        byte[] bytes = getImageFormNet(url, w, h, overflow);
        return bytes;
    }

    private byte[] getImageFormNet(String url, int w, int h, boolean overflow) throws Exception {
        byte[] bytes;
        InputStream inputStream = null;
        try {
            URL imageUrl = new URL(url);
            URLConnection connection = imageUrl.openConnection();
            connection.setReadTimeout(30 * 1000);
            inputStream = connection.getInputStream();

            bytes = compress(inputStream, w, h, overflow);
        } catch (ImageCompressException e) {
            logger.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return bytes;
    }

    @Override
    public byte[] compress(byte[] bytes, int w, int h, boolean overflow) throws Exception {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        byte[] compress = compress(inputStream, w, h, overflow);
        return compress;
    }

    @Override
    public byte[] compress(InputStream inputStream, int w, int h, boolean overflow) throws ImageCompressException {
        try {
            // 构造Image对象
            BufferedImage img = ImageIO.read(inputStream);
            ImageSize imageSize = resizeFix(img, w, h);
            BufferedImage outImage = resize(img, imageSize.getWidth(), imageSize.getHeight());
            if (overflow) {
                outImage = clip(outImage, w, h);
            }

            byte[] bytes = encodeImage(outImage);

            return bytes;
        } catch (Exception e) {
            throw new ImageCompressException(e.getMessage(), e);
        }
    }

    private byte[] encodeImage(BufferedImage outImage) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(outImage, "jpeg", out);
        byte[] bytes = out.toByteArray();
        return bytes;
    }

    /**
     * 剪切处理
     *
     * @param img
     * @param width
     * @param height
     */
    private BufferedImage clip(BufferedImage img, int width, int height) {
        BufferedImage newImg = new BufferedImage(width, height, BufferedImage.TYPE_USHORT_565_RGB);
        Graphics graphics = newImg.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);
        graphics.drawImage(img, 0, 0, width, height, null);

        return newImg;
    }

    /**
     * 按照宽度还是高度进行压缩
     *
     * @param w 最大宽度
     * @param h 最大高度
     */
    private ImageSize resizeFix(BufferedImage img, int w, int h) throws IOException {
        // 得到源图宽
        int width = img.getWidth();
        // 得到源图长
        int height = img.getHeight();

        ImageSize imageSize;
        if (width / height > w / h) {
            imageSize = resizeByWidth(img, w);
        } else {
            imageSize = resizeByHeight(img, h);
        }

        return imageSize;
    }

    /**
     * 以宽度为基准，等比例放缩图片
     *
     * @param w 新宽度
     */
    private ImageSize resizeByWidth(BufferedImage img, int w) throws IOException {
        // 得到源图宽
        int width = img.getWidth();
        // 得到源图长
        int height = img.getHeight();

        int h = (int) (1.0 * height * w / width);

        ImageSize imageSize = new ImageSize();
        imageSize.setWidth(w);
        imageSize.setHeight(h);

        return imageSize;
    }

    /**
     * 以高度为基准，等比例缩放图片
     *
     * @param h 新高度
     */
    private ImageSize resizeByHeight(BufferedImage img, int h) throws IOException {
        // 得到源图宽
        int width = img.getWidth();
        // 得到源图长
        int height = img.getHeight();

        int w = (int) (1.0 * width * h / height);

        ImageSize imageSize = new ImageSize();
        imageSize.setWidth(w);
        imageSize.setHeight(h);

        return imageSize;
    }

    /**
     * 强制压缩/放大图片到固定的大小
     *
     * @param img
     * @param w   新宽度
     * @param h   新高度
     */
    private BufferedImage resize(Image img, int w, int h) throws IOException {
        // SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_USHORT_565_RGB);
        image.getGraphics().drawImage(img, 0, 0, w, h, null); // 绘制缩小后的图
        return image;
    }
}
