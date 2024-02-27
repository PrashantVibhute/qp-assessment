package dev.naresh.qp.dto;

import dev.naresh.qp.constant.ProductCategory;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record AdminProductRecord(
    UUID id,
    String name,
    String description,
    double price,
    ProductCategory category,
    int quantity,
    LocalDateTime createdAt,
    LocalDateTime updatedAt) {}
