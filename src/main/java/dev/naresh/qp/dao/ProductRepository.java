package dev.naresh.qp.dao;

import dev.naresh.qp.constant.ProductCategory;
import dev.naresh.qp.model.Product;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.ListCrudRepository;

public interface ProductRepository extends ListCrudRepository<Product, UUID> {
  Optional<Product> findByName(String name);

  List<Product> findByCategory(ProductCategory category);
}
