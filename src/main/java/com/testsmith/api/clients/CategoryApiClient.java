package com.testsmith.api.clients;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Category API Client
 * Handles all Category-related API operations
 */
public class CategoryApiClient extends ApiClient {

    private static final Logger logger = LogManager.getLogger(CategoryApiClient.class);
    private static final String CATEGORIES_PATH = "/categories";

    public CategoryApiClient() {
        super(CATEGORIES_PATH);
    }

    @Step("GET /categories - Retrieve all categories")
    public Response getAllCategories() {
        logger.info("Fetching all categories");
        return get("");
    }

    @Step("GET /categories/tree - Retrieve category tree")
    public Response getCategoryTree() {
        logger.info("Fetching category tree");
        return get("/tree");
    }

    @Step("GET /categories/tree/{categoryId} - Retrieve category by ID")
    public Response getCategoryById(String categoryId) {
        logger.info("Fetching category with ID: {}", categoryId);
        return get("/tree/{categoryId}", categoryId);
    }

    @Step("POST /categories - Create new category")
    public Response createCategory(Object categoryRequest) {
        logger.info("Creating new category");
        return post("", categoryRequest);
    }

    @Step("PUT /categories/{categoryId} - Update category")
    public Response updateCategory(String categoryId, Object categoryRequest) {
        logger.info("Updating category with ID: {}", categoryId);
        return getRequest().body(categoryRequest).put("/{categoryId}", categoryId);
    }

    @Step("DELETE /categories/{categoryId} - Delete category")
    public Response deleteCategory(String categoryId) {
        logger.info("Deleting category with ID: {}", categoryId);
        return getRequest().delete("/{categoryId}", categoryId);
    }

    @Step("GET /categories/search?q={query} - Search categories")
    public Response searchCategories(String query) {
        logger.info("Searching categories with query: {}", query);
        return get("/search?q={query}", query);
    }
}
