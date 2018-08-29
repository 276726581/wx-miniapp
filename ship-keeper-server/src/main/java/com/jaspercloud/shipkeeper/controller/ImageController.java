package com.jaspercloud.shipkeeper.controller;

import com.jaspercloud.shipkeeper.entity.ImageInfo;
import com.jaspercloud.shipkeeper.service.FileStorageService;
import com.jaspercloud.shipkeeper.service.ImageCompressService;
import com.jaspercloud.shipkeeper.util.ImageInfoUtil;
import com.jaspercloud.shipkeeper.util.ShortUUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
public class ImageController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ImageCompressService imageCompressService;

    @PostMapping("/upload/image")
    public ResponseEntity<String> uploadImage(@RequestParam("img") MultipartFile multipartFile) throws IOException {
        byte[] bytes = multipartFile.getBytes();
        String uuid = ShortUUID.generate();
        File file = fileStorageService.saveFile(bytes, uuid, "jpg");
        String fileName = file.getName();
        ResponseEntity<String> entity = ResponseEntity.ok().body(fileName);
        return entity;
    }

    @GetMapping("/image/**")
    public ResponseEntity<byte[]> downloadImage(HttpServletRequest request) {
        try {
            ImageInfo imageInfo = ImageInfoUtil.parse(request);
            String imageName = (null == imageInfo.getImageName() ? imageInfo.getFullName() : imageInfo.getImageName());
            byte[] bytes = fileStorageService.readFile(imageName);
            byte[] result;
            if (null != imageInfo.getWidth() && null != imageInfo.getHeight()) {
                byte[] compress = imageCompressService.compress(bytes, imageInfo.getWidth(), imageInfo.getHeight(), false);
                result = compress;
            } else {
                result = bytes;
            }
            ResponseEntity<byte[]> entity = ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(result);
            return entity;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            ResponseEntity<byte[]> entity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getClass().getName().getBytes());
            return entity;
        }
    }
}
