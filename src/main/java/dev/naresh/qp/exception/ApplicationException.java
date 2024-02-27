package dev.naresh.qp.exception;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationException extends RuntimeException {
  private int httpCode;
  private String errorCode;
  private String errorMessage;
  private Exception ex;
}
