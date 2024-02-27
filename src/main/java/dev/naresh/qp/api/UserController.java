package dev.naresh.qp.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.naresh.qp.constant.API;
import dev.naresh.qp.constant.ProductCategory;
import dev.naresh.qp.dto.OrderConfirmation;
import dev.naresh.qp.dto.OrderProduct;
import dev.naresh.qp.dto.ProductRecord;
import dev.naresh.qp.service.UserProductService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(API.USER_API_CONTEXT_PATH)
public class UserController {

  private final UserProductService userProductService;

  @GetMapping
  public ResponseEntity<List<ProductRecord>> getProducts() {
    return ResponseEntity.ok(userProductService.getProducts());
  }

  @GetMapping("/by-category/{category}")
  public ResponseEntity<List<ProductRecord>> getProductsByCategory(
      @PathVariable ProductCategory category) {
    return ResponseEntity.ok(userProductService.getProductsByCategory(category));
  }

  @GetMapping("/{productId}")
  public ResponseEntity<ProductRecord> getProductById(@PathVariable UUID productId) {
    return ResponseEntity.ok(userProductService.getProductById(productId));
  }

  @PostMapping("/order")
  public ResponseEntity<OrderConfirmation> confirmOrder(@RequestBody List<OrderProduct> products)
      throws JsonProcessingException {
    return ResponseEntity.ok(userProductService.confirmOrder(products));
  }
}
