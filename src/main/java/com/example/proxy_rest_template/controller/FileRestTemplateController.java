package com.example.proxy_rest_template.controller;

import com.example.proxy_rest_template.service.PostRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
public class FileRestTemplateController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private final PostRestService postRestService;

    public FileRestTemplateController(PostRestService postRestService) {
        this.postRestService = postRestService;
    }

    @GetMapping("/test-download-v1")
    public void download() throws IOException {
        String url = "https://cloudspringloadzip-80c5fd137b1c.herokuapp.com/api/files/download/123.txt";
        byte[] imageBytes = restTemplate.getForObject(url, byte[].class);
        Files.write(Paths.get("123.txt"), imageBytes);
    }

    @GetMapping("/test-download-v2")
    public void download2() throws IOException {
        postRestService.downloadFile("https://cloudspringloadzip-80c5fd137b1c.herokuapp.com/api/files/download/123.txt", "./download");
    }

    @PostMapping("/test-upload-v1")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        postRestService.uploadFile("https://cloudspringloadzip-80c5fd137b1c.herokuapp.com/api/files/upload", "123.txt");
        return "File uploaded to: " + file.getOriginalFilename();
    }

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("targetUrl") String targetUrl) {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }

        try {
            postRestService.uploadFile(targetUrl, file);
            return ResponseEntity.ok("File uploaded and forwarded successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Upload failed: " + e.getMessage());
        }
    }
}


