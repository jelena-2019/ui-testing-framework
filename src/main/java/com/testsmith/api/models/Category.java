package com.testsmith.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Category {

    private String id;
    @JsonProperty("parent_id")
    private String parentId;
    private String name;
    private String slug;
    @JsonProperty("sub_categories")
    private List<Category> subCategories;

    public Category() {
    }

    public Category(String name, String slug) {
        this.name = name;
        this.slug = slug;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public List<Category> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<Category> subCategories) {
        this.subCategories = subCategories;
    }
}
