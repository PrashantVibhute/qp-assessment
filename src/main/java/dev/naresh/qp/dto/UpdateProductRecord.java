package dev.naresh.qp.dto;

import dev.naresh.qp.constant.ProductCategory;
import jakarta.validation.constraints.NotEmpty;

public record UpdateProductRecord(
    @NotEmpty String name,
    String description,
    double price,
    ProductCategory category,
    int quantity) {}
