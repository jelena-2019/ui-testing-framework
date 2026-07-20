package com.testsmith.api.clients;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Brand API Client
 * Handles all Brand-related API operations
 */
public class BrandApiClient extends ApiClient {

    private static final Logger logger = LogManager.getLogger(BrandApiClient.class);
    private static final String BRANDS_PATH = "/brands";

    public BrandApiClient() {
        super(BRANDS_PATH);
    }

    @Step("GET /brands - Retrieve all brands")
    public Response getAllBrands() {
        logger.info("Fetching all brands");
        return get("");
    }

    @Step("GET /brands/{brandId} - Retrieve brand by ID")
    public Response getBrandById(String brandId) {
        logger.info("Fetching brand with ID: {}", brandId);
        return get("/{brandId}", brandId);
    }

    @Step("POST /brands - Create new brand")
    public Response createBrand(Object brandRequest) {
        logger.info("Creating new brand");
        return post("", brandRequest);
    }

    @Step("PUT /brands/{brandId} - Update brand")
    public Response updateBrand(String brandId, Object brandRequest) {
        logger.info("Updating brand with ID: {}", brandId);
        return put("/{brandId}", brandRequest);
    }

    @Step("DELETE /brands/{brandId} - Delete brand")
    public Response deleteBrand(String brandId) {
        logger.info("Deleting brand with ID: {}", brandId);
        return delete("/{brandId}");
    }

    @Step("GET /brands/search?q={query} - Search brands")
    public Response searchBrands(String query) {
        logger.info("Searching brands with query: {}", query);
        return get("/search?q={query}", query);
    }
}
