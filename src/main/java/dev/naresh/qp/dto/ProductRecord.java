package dev.naresh.qp.dto;

import dev.naresh.qp.constant.ProductCategory;
import java.util.UUID;
import lombok.Builder;

@Builder
public record ProductRecord(
    UUID id, String name, String description, double price, ProductCategory category) {}
