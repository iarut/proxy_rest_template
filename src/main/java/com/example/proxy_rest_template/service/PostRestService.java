package com.example.proxy_rest_template.service;


import com.example.proxy_rest_template.controller.MultipartFileResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class PostRestService {

    @Autowired
    private RestTemplate restTemplate;

    public String downloadFile(String fileUrl, String destinationPath) throws IOException {
        File downloadedFile = restTemplate.execute(fileUrl, HttpMethod.GET, null, clientHttpResponse -> {
            File tempFile = new File(destinationPath);
            StreamUtils.copy(clientHttpResponse.getBody(), new FileOutputStream(tempFile));
            return tempFile;
        });
        return "File downloaded to: " + downloadedFile.getAbsolutePath();
    }

    public void uploadFile(String uploadUrl, String filePath) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new FileSystemResource(new File(filePath)));
        // You can add other form fields if needed:
        // body.add("description", "A description of the file");

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        String response = restTemplate.postForObject(uploadUrl, requestEntity, String.class);
        System.out.println("Upload response: " + response);
    }

    public void uploadFile(String uploadUrl, MultipartFile file) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new MultipartFileResource(file));
        // body.add("description", "A description of the file");

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        String response = restTemplate.postForObject(uploadUrl, requestEntity, String.class);
        System.out.println("Upload response: " + response);
    }
}