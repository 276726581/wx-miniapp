package com.jaspercloud.shipkeeper.service;

import java.io.File;
import java.io.IOException;

public interface FileStorageService {

    File saveFile(byte[] bytes, String extension) throws IOException;

    File saveFile(byte[] bytes, String fileName, String extension) throws IOException;

    byte[] readFile(String fileName) throws IOException;
}
