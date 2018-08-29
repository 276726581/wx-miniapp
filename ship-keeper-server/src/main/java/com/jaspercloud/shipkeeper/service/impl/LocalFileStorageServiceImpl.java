package com.jaspercloud.shipkeeper.service.impl;

import com.jaspercloud.shipkeeper.service.FileStorageService;
import com.jaspercloud.shipkeeper.util.ShortUUID;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class LocalFileStorageServiceImpl implements FileStorageService, InitializingBean {

    @Value("${fileStorage.dir}")
    private String rootStr;

    private File rootDir;

    @Override
    public void afterPropertiesSet() throws Exception {
        rootDir = new File(rootStr);
        if (!rootDir.exists()) {
            rootDir.mkdirs();
        }
    }

    @Override
    public File saveFile(byte[] bytes, String extension) throws IOException {
        String fileName = ShortUUID.generate();
        File file = saveFile(bytes, fileName, extension);
        return file;
    }

    @Override
    public File saveFile(byte[] bytes, String fileName, String extension) throws IOException {
        File file = new File(rootDir, fileName + "." + extension);
        Files.write(Paths.get(file.getAbsolutePath()), bytes);
        return file;
    }

    @Override
    public byte[] readFile(String fileName) throws IOException {
        File file = new File(rootDir, fileName);
        byte[] bytes = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
        return bytes;
    }
}
