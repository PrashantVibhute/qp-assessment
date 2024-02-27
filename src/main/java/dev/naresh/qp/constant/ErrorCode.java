package dev.naresh.qp.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
  //
  ERR_VALIDATION_FAILURE(400, "REQUEST_VALIDATION_ERROR", "error %s %s"),
  ERR_PRODUCT_NOT_FOUND(404, "PRODUCT_NOT_FOUND", "product not found with %s"),
  ERR_PRODUCT_ALREADY_EXISTS(409, "PRODUCT_ALREADY_EXISTS", "product already exists"),
  ERR_PRODUCT_QUANTITY_NOT_AVAILABLE(
      400, "PRODUCT_QUANTITY_NOT_AVAILABLE", "product quantity not available for product name %s"),
  ERR_SERVER_PROCESSING(500, "SERVER_PROCESSING_ERROR", "unknown server processing error happened");

  private final int httpCode;
  private final String errorCode;
  private final String errorMessage;
}
