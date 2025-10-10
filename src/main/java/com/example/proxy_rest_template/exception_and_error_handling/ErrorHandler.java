package com.example.proxy_rest_template.exception_and_error_handling;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

public class ErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return new DefaultResponseErrorHandler().hasError(response);
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        HttpStatus statusCode = (HttpStatus) response.getStatusCode();
        String body = new String(response.getBody().readAllBytes());

        if (statusCode.is5xxServerError()) {
            throw new RuntimeException("Server error: " + statusCode + " — " + body);
        } else if (statusCode.is4xxClientError()) {
            throw new RuntimeException("Client error: " + statusCode + " — " + body);
        }
    }

//    @Override
//    public void handleError(ClientHttpResponse response) throws IOException {
//
//        if (response.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR) {
//            // handle 5xx errors
//            // raw http status code e.g `500`
//            System.out.println(response.getRawStatusCode());
//
//            // http status code e.g. `500 INTERNAL_SERVER_ERROR`
//            System.out.println(response.getStatusCode());
//
//        } else if (response.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR) {
//            // handle 4xx errors
//            // raw http status code e.g `404`
//            System.out.println(response.getRawStatusCode());
//
//            // http status code e.g. `404 NOT_FOUND`
//            System.out.println(response.getStatusCode());
//
//            // get response body
//            System.out.println(response.getBody());
//
//            // get http headers
//            HttpHeaders headers = response.getHeaders();
//            System.out.println(headers.get("Content-Type"));
//            System.out.println(headers.get("Server"));
//        }
//    }
}
