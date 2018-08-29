package com.jaspercloud.shipkeeper.service;

import com.jaspercloud.shipkeeper.exception.ImageCompressException;

import java.io.InputStream;

public interface ImageCompressService {

    byte[] compress(String url, int w, int h) throws Exception;

    byte[] compress(String url, int w, int h, boolean overflow) throws Exception;

    byte[] compress(byte[] bytes, int w, int h, boolean overflow) throws Exception;

    byte[] compress(InputStream inputStream, int w, int h, boolean overflow) throws ImageCompressException;
}
