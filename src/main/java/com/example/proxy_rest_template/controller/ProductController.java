package com.example.proxy_rest_template.controller;

import com.example.proxy_rest_template.model.Product;
import com.example.proxy_rest_template.service.PostRestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class ProductController {

    @Autowired
    private RestTemplate restTemplate;

    private String url="https://cloudspringloadzip-80c5fd137b1c.herokuapp.com/api/v1/products";


    @GetMapping("getallproducts5")
    public ResponseEntity<?> getAllProducts5() {
        try {
            // Используем getForObject который просто возвращает данные
            Product[] products = restTemplate.getForObject(url, Product[].class);
            return ResponseEntity.ok(products);

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching products: " + ex.getMessage());
        }
    }

    @PostMapping("newproduct")
    public ResponseEntity<?> newProduct(@RequestBody Product product) {
        try {
            Product newProduct = restTemplate.postForObject(url, product, Product.class);
            return ResponseEntity.ok(newProduct);
        }
        catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating product: " + ex.getMessage());
        }
    }

    @PutMapping("/editproduct/{id}")
    public ResponseEntity<?> editProduct(@PathVariable int id, @RequestBody Product product) {
        try {
            String targetUrl = url + "/" + id;

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Product> requestEntity = new HttpEntity<>(product, headers);

            ResponseEntity<Product> response = restTemplate.exchange(
                    targetUrl,
                    HttpMethod.PUT,
                    requestEntity,
                    Product.class
            );

            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error editing product: " + ex.getMessage());
        }
    }

    @DeleteMapping("deleteproduct/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable int id) {
        try {
            restTemplate.delete(url+"/{id}", id);
            return ResponseEntity.ok().build();
        }
        catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting product: " + ex.getMessage());
        }
    }
}
