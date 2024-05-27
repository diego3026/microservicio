package com.paymentservice.models.dto;

import com.paymentservice.models.entities.enums.PaymentStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class SendPaymentDto {
    private UUID id;
    private UUID booking_id;
    private int credit_card;
    private PaymentStatus status;
    private UUID transaction_id;
}
