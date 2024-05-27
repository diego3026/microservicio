package com.paymentservice.models.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class RequestPaymentDto {
    private UUID booking_id;
    private int credit_card;
    private int amount;
}
