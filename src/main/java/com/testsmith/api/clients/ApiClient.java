package com.testsmith.api.clients;

import com.testsmith.config.ConfigProperties;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * API Client
 * Base REST Assured configuration for API testing
 */
public class ApiClient {

    private static final Logger logger = LogManager.getLogger(ApiClient.class);
    protected RequestSpecification request;

    public ApiClient() {
        RestAssured.baseURI = ConfigProperties.getApiBaseUrl();
        request = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .log().uri();
        logger.info("API Client initialized with base URI: {}", ConfigProperties.getApiBaseUrl());
    }

    public ApiClient(String basePath) {
        this();
        request = request.basePath(basePath);
    }

    protected RequestSpecification getRequest() {
        return request;
    }

    protected Response get(String path) {
        logger.info("GET {}", path);
        return request.get(path);
    }

    protected Response get(String path, Object... pathParams) {
        logger.info("GET {}", path);
        return request.get(path, pathParams);
    }

    protected Response post(String path, Object body) {
        logger.info("POST {}", path);
        return request.body(body).post(path);
    }

    protected Response put(String path, Object body) {
        logger.info("PUT {}", path);
        return request.body(body).put(path);
    }

    protected Response patch(String path, Object body) {
        logger.info("PATCH {}", path);
        return request.body(body).patch(path);
    }

    protected Response delete(String path) {
        logger.info("DELETE {}", path);
        return request.delete(path);
    }
}
