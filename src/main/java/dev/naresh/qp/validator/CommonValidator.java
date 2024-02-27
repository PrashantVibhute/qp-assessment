package dev.naresh.qp.validator;

import dev.naresh.qp.constant.ErrorCode;
import dev.naresh.qp.exception.ApplicationException;
import jakarta.validation.Validator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommonValidator {

  private final Validator validator;

  public <T> void validate(T source) {
    var violations = validator.validate(source);
    if (violations.iterator().hasNext()) {
      var errorMessage =
          violations.stream()
              .findFirst()
              .map(
                  cv ->
                      String.format(
                          ErrorCode.ERR_VALIDATION_FAILURE.getErrorMessage(),
                          cv.getPropertyPath(),
                          cv.getMessage()))
              .orElse(ErrorCode.ERR_VALIDATION_FAILURE.getErrorMessage());
      throw ApplicationException.builder()
          .httpCode(ErrorCode.ERR_VALIDATION_FAILURE.getHttpCode())
          .errorCode(ErrorCode.ERR_VALIDATION_FAILURE.getErrorCode())
          .errorMessage(errorMessage)
          .build();
    }
  }

  public <T> void validateList(List<T> source) {
    if (source != null) {
      var iterator = source.iterator();
      if (iterator.hasNext()) validate(iterator.next());
    }
  }
}
