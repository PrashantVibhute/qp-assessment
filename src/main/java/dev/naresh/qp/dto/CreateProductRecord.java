package dev.naresh.qp.dto;

import dev.naresh.qp.constant.ProductCategory;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CreateProductRecord(
    @NotEmpty String name,
    String description,
    @NotNull double price,
    @NotNull ProductCategory category,
    int quantity) {}
