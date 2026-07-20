package com.testsmith.api.clients;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Product API Client
 * Handles all Product-related API operations
 */
public class ProductApiClient extends ApiClient {

    private static final Logger logger = LogManager.getLogger(ProductApiClient.class);
    private static final String PRODUCTS_PATH = "/products";

    public ProductApiClient() {
        super(PRODUCTS_PATH);
    }

    @Step("GET /products - Retrieve all products")
    public Response getAllProducts() {
        logger.info("Fetching all products");
        return get("");
    }

    @Step("GET /products?page={page} - Retrieve products with pagination")
    public Response getProducts(int page) {
        logger.info("Fetching products page: {}", page);
        return get("?page={page}", page);
    }

    @Step("GET /products/{productId} - Retrieve product by ID")
    public Response getProductById(String productId) {
        logger.info("Fetching product with ID: {}", productId);
        return get("/{productId}", productId);
    }

    @Step("POST /products - Create new product")
    public Response createProduct(Object productRequest) {
        logger.info("Creating new product");
        return post("", productRequest);
    }

    @Step("PUT /products/{productId} - Update product")
    public Response updateProduct(String productId, Object productRequest) {
        logger.info("Updating product with ID: {}", productId);
        return getRequest().body(productRequest).put("/{productId}", productId);
    }

    @Step("DELETE /products/{productId} - Delete product")
    public Response deleteProduct(String productId) {
        logger.info("Deleting product with ID: {}", productId);
        return getRequest().delete("/{productId}", productId);
    }

    @Step("GET /products/search?q={query} - Search products")
    public Response searchProducts(String query) {
        logger.info("Searching products with query: {}", query);
        return get("/search?q={query}", query);
    }

    @Step("GET /products/{productId}/related - Retrieve related products")
    public Response getRelatedProducts(String productId) {
        logger.info("Fetching related products for: {}", productId);
        return get("/{productId}/related", productId);
    }
}
