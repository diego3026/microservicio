package com.paymentservice.services;

import com.paymentservice.models.dto.RequestPaymentDto;
import com.paymentservice.models.dto.SendPaymentDto;

import java.util.List;
import java.util.UUID;

public interface PaymentService {
    List<SendPaymentDto> findAll();
    SendPaymentDto findById(UUID id);
    SendPaymentDto save(RequestPaymentDto requestPaymentDto);
    SendPaymentDto update(UUID id, RequestPaymentDto requestPaymentDto);
    void delete(UUID id);
}
