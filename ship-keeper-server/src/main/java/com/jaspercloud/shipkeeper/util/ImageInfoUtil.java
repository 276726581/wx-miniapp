package com.jaspercloud.shipkeeper.util;

import com.jaspercloud.shipkeeper.entity.ImageInfo;
import com.jaspercloud.shipkeeper.exception.ParseException;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ImageInfoUtil {

    private ImageInfoUtil() {

    }

    private static Pattern pattern;

    static {
        pattern = Pattern.compile("_\\d+x\\d+");
    }

    public static ImageInfo parse(HttpServletRequest request) {
        ImageInfo imageInfo = new ImageInfo();
        String requestURI = request.getRequestURI();

        String fullName = getFullName(requestURI);
        imageInfo.setFullName(fullName);

        Matcher matcher = pattern.matcher(fullName);
        if (!matcher.find()) {
            return imageInfo;
        }
        String imageSize = matcher.group();

        String imageName = getImageName(fullName, imageSize);
        imageInfo.setImageName(imageName);

        parseImageSize(imageInfo, imageSize);

        return imageInfo;
    }

    private static void parseImageSize(ImageInfo imageInfo, String imageSize) {
        String[] splits = imageSize.replace("_", "").split("x");
        if (splits.length < 2) {
            throw new ParseException();
        }
        try {
            imageInfo.setWidth(Integer.parseInt(splits[0]));
            imageInfo.setHeight(Integer.parseInt(splits[1]));
        } catch (Exception e) {
            throw new ParseException();
        }
    }

    private static String getImageName(String fullName, String imageSize) {
        String imageName;
        {
            int idx = fullName.indexOf(imageSize);
            if (-1 == idx) {
                throw new ParseException();
            }
            imageName = fullName.substring(0, idx);
        }
        String extension;
        {
            int idx = fullName.lastIndexOf(".");
            if (-1 == idx) {
                throw new ParseException();
            }
            extension = fullName.substring(idx + 1, fullName.length());
        }
        String result = imageName + "." + extension;

        return result;
    }

    private static String getFullName(String requestURI) {
        int index = requestURI.lastIndexOf("/");
        if (-1 == index) {
            throw new ParseException();
        }
        String fullName = requestURI.substring(index + 1, requestURI.length());
        return fullName;
    }
}
