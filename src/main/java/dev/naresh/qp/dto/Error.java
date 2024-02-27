package dev.naresh.qp.dto;

import lombok.Builder;

@Builder
public record Error(String errorCode, String errorMessage) {}
