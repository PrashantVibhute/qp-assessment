package dev.naresh.qp.mapper;

import dev.naresh.qp.dto.AdminProductRecord;
import dev.naresh.qp.dto.CreateProductRecord;
import dev.naresh.qp.dto.ProductRecord;
import dev.naresh.qp.model.Product;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

  public Product map(CreateProductRecord productRecord) {
    var product = new Product();
    product.setName(productRecord.name());
    product.setDescription(productRecord.description());
    product.setPrice(productRecord.price());
    product.setCategory(productRecord.category());
    product.setQuantity(productRecord.quantity());
    product.setCreatedAt(LocalDateTime.now());
    return product;
  }

  public AdminProductRecord mapAdminProductRecord(Product product) {
    return AdminProductRecord.builder()
        .id(product.getId())
        .name(product.getName())
        .description(product.getDescription())
        .price(product.getPrice())
        .category(product.getCategory())
        .quantity(product.getQuantity())
        .createdAt(product.getCreatedAt())
        .updatedAt(product.getUpdatedAt())
        .build();
  }

  public ProductRecord mapProductRecord(Product product) {
    return ProductRecord.builder()
        .id(product.getId())
        .name(product.getName())
        .description(product.getDescription())
        .price(product.getPrice())
        .category(product.getCategory())
        .build();
  }
}
