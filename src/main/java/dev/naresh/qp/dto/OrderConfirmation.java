package dev.naresh.qp.dto;

import java.util.UUID;

public record OrderConfirmation(UUID orderId, double totalAmount) {}
