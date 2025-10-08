package com.example.proxy_rest_template.controller;

import org.springframework.core.io.AbstractResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public class MultipartFileResource extends AbstractResource {

    private final MultipartFile file;

    public MultipartFileResource(MultipartFile file) {
        this.file = file;
    }

    @Override
    public String getFilename() {
        return file.getOriginalFilename();
    }

    @Override
    public long contentLength() {
        return file.getSize();
    }

    @Override
    public String getDescription() {
        return "MultipartFile resource [" + file.getOriginalFilename() + "]";
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return file.getInputStream();
    }
}
