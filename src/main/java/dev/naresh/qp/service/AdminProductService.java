package dev.naresh.qp.service;

import dev.naresh.qp.constant.ErrorCode;
import dev.naresh.qp.dao.ProductRepository;
import dev.naresh.qp.dto.AdminProductRecord;
import dev.naresh.qp.dto.CreateProductRecord;
import dev.naresh.qp.dto.UpdateProductRecord;
import dev.naresh.qp.exception.ApplicationException;
import dev.naresh.qp.mapper.ProductMapper;
import dev.naresh.qp.model.Product;
import dev.naresh.qp.validator.CommonValidator;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminProductService {

  private final ProductMapper productMapper;
  private final CommonValidator commonValidator;
  private final ProductRepository productRepository;

  @Transactional(rollbackOn = Exception.class)
  public void createProducts(List<CreateProductRecord> productRecords) {
    commonValidator.validateList(productRecords);
    List<Product> products = productRecords.stream().map(productMapper::map).toList();
    productRepository.saveAll(products);
  }

  public List<AdminProductRecord> getProducts() {
    return productRepository.findAll().stream().map(productMapper::mapAdminProductRecord).toList();
  }

  public AdminProductRecord getProductById(UUID id) {
    return productRepository
        .findById(id)
        .map(productMapper::mapAdminProductRecord)
        .orElseThrow(() -> throwProductNotFound(id.toString()));
  }

  @Transactional(rollbackOn = Exception.class)
  public void deleteProducts(List<UUID> productIds) {
    productRepository.deleteAllById(productIds);
  }

  @Transactional(rollbackOn = Exception.class)
  public void updateProduct(UpdateProductRecord productRecord) {
    commonValidator.validate(productRecord);
    Product product =
        productRepository
            .findByName(productRecord.name())
            .orElseThrow(() -> throwProductNotFound(productRecord.name()));
    if (StringUtils.hasLength(productRecord.name())) product.setName(productRecord.name());
    if (StringUtils.hasLength(productRecord.description()))
      product.setDescription(productRecord.description());
    if ((productRecord.quantity()) > 0) product.setQuantity(productRecord.quantity());
    if (productRecord.price() > 0) product.setPrice(productRecord.price());
    product.setUpdatedAt(LocalDateTime.now());
    productRepository.save(product);
  }

  private ApplicationException throwProductNotFound(String identifier) {
    throw ApplicationException.builder()
        .httpCode(ErrorCode.ERR_PRODUCT_NOT_FOUND.getHttpCode())
        .errorCode(ErrorCode.ERR_PRODUCT_NOT_FOUND.getErrorCode())
        .errorMessage(String.format(ErrorCode.ERR_PRODUCT_NOT_FOUND.getErrorMessage(), identifier))
        .build();
  }
}
