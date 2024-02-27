package dev.naresh.qp.exception;

import dev.naresh.qp.constant.ErrorCode;
import dev.naresh.qp.dto.Error;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ApplicationExceptionHandler {

  @ExceptionHandler(ApplicationException.class)
  public ResponseEntity<Error> handleApplicationException(
      HttpServletRequest request, ApplicationException ex) {
    log.error(
        "error @ {}:{} errorCode: {} errorMessage: {}",
        request.getMethod(),
        request.getRequestURI(),
        ex.getErrorCode(),
        ex.getErrorMessage());
    return ResponseEntity.status(ex.getHttpCode())
        .body(
            Error.builder()
                .errorCode(ex.getErrorCode())
                .errorMessage(ex.getErrorMessage())
                .build());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Error> handleException(HttpServletRequest request, Exception ex) {
    log.error(
        "error @ {}:{} errorType: {}",
        request.getMethod(),
        request.getRequestURI(),
        ex.getMessage(),
        ex);
    return ResponseEntity.status(ErrorCode.ERR_SERVER_PROCESSING.getHttpCode())
        .body(
            Error.builder()
                .errorCode(ErrorCode.ERR_SERVER_PROCESSING.getErrorCode())
                .errorMessage(ErrorCode.ERR_SERVER_PROCESSING.getErrorMessage())
                .build());
  }
}
