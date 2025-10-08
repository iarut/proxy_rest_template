package com.example.proxy_rest_template.controller;

import com.example.proxy_rest_template.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class PostRestController {
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value="/getall")
    public List<Post> getPosts(){
        return restTemplate.getForObject("https://jsonplaceholder.typicode.com/posts", List.class);
    }
}
