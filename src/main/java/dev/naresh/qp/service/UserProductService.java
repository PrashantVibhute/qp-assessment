package dev.naresh.qp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.naresh.qp.constant.ErrorCode;
import dev.naresh.qp.constant.ProductCategory;
import dev.naresh.qp.dao.OrderRepository;
import dev.naresh.qp.dao.ProductRepository;
import dev.naresh.qp.dto.OrderConfirmation;
import dev.naresh.qp.dto.OrderProduct;
import dev.naresh.qp.dto.ProductRecord;
import dev.naresh.qp.exception.ApplicationException;
import dev.naresh.qp.mapper.ProductMapper;
import dev.naresh.qp.model.Order;
import dev.naresh.qp.model.Product;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserProductService {

  private final ProductMapper productMapper;
  private final OrderRepository orderRepository;
  private final ProductRepository productRepository;

  public List<ProductRecord> getProducts() {
    return productRepository.findAll().stream()
        .filter(product -> product.getQuantity() > 0)
        .map(productMapper::mapProductRecord)
        .toList();
  }

  public List<ProductRecord> getProductsByCategory(ProductCategory category) {
    return productRepository.findByCategory(category).stream()
        .map(productMapper::mapProductRecord)
        .toList();
  }

  public ProductRecord getProductById(UUID id) {
    return productRepository
        .findById(id)
        .map(productMapper::mapProductRecord)
        .orElseThrow(() -> throwProductNotFound(id.toString()));
  }

  @Transactional(rollbackOn = Exception.class)
  public OrderConfirmation confirmOrder(List<OrderProduct> products)
      throws JsonProcessingException {
    return processOrder(products);
  }

  private OrderConfirmation processOrder(List<OrderProduct> products)
      throws JsonProcessingException {
    var orderId = UUID.randomUUID();
    var orderAmount = 0.0;
    for (OrderProduct orderProduct : products) {
      Product product =
          productRepository
              .findById(orderProduct.id())
              .orElseThrow(() -> throwProductNotFound(orderProduct.id().toString()));
      if (orderProduct.quantity() > product.getQuantity())
        throwProductQuantityNotAvailable(product);

      product.setQuantity(product.getQuantity() - orderProduct.quantity());
      product.setUpdatedAt(LocalDateTime.now());
      orderAmount += (product.getPrice() * orderProduct.quantity());
      productRepository.save(product);
    }

    var order = new Order();
    order.setId(orderId);
    order.setAmount(orderAmount);
    var mapper = new ObjectMapper();
    String orderProductsString = mapper.writeValueAsString(products);
    order.setDescription(orderProductsString);
    order.setCreatedAt(LocalDateTime.now());
    orderRepository.save(order);
    return new OrderConfirmation(orderId, orderAmount);
  }

  private ApplicationException throwProductNotFound(String identifier) {
    throw ApplicationException.builder()
        .httpCode(ErrorCode.ERR_PRODUCT_NOT_FOUND.getHttpCode())
        .errorCode(ErrorCode.ERR_PRODUCT_NOT_FOUND.getErrorCode())
        .errorMessage(String.format(ErrorCode.ERR_PRODUCT_NOT_FOUND.getErrorMessage(), identifier))
        .build();
  }

  private void throwProductQuantityNotAvailable(Product product) {
    throw ApplicationException.builder()
        .httpCode(ErrorCode.ERR_PRODUCT_QUANTITY_NOT_AVAILABLE.getHttpCode())
        .errorCode(ErrorCode.ERR_PRODUCT_QUANTITY_NOT_AVAILABLE.getErrorCode())
        .errorMessage(
            String.format(
                ErrorCode.ERR_PRODUCT_QUANTITY_NOT_AVAILABLE.getErrorMessage(), product.getName()))
        .build();
  }
}
