package dev.naresh.qp.api;

import dev.naresh.qp.constant.API;
import dev.naresh.qp.dto.AdminProductRecord;
import dev.naresh.qp.dto.CreateProductRecord;
import dev.naresh.qp.dto.UpdateProductRecord;
import dev.naresh.qp.service.AdminProductService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(API.ADMIN_API_CONTEXT_PATH)
public class AdminController {

  private final AdminProductService adminProductService;

  @PostMapping
  public ResponseEntity<?> createProduct(@RequestBody List<CreateProductRecord> products) {
    adminProductService.createProducts(products);
    return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().build().toUri())
        .build();
  }

  @PutMapping
  public ResponseEntity<?> updateProduct(@RequestBody UpdateProductRecord productRecord) {
    adminProductService.updateProduct(productRecord);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping
  public ResponseEntity<?> deleteProduct(@RequestBody List<UUID> productIds) {
    adminProductService.deleteProducts(productIds);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{productId}")
  public ResponseEntity<?> getProductById(@PathVariable UUID productId) {
    return ResponseEntity.ok(adminProductService.getProductById(productId));
  }

  @GetMapping
  public ResponseEntity<List<AdminProductRecord>> getProducts() {
    return ResponseEntity.ok(adminProductService.getProducts());
  }
}
