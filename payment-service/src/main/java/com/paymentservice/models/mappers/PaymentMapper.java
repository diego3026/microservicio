package com.paymentservice.models.mappers;

import com.paymentservice.models.dto.RequestPaymentDto;
import com.paymentservice.models.dto.SendPaymentDto;
import com.paymentservice.models.entities.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    Payment toEntity(RequestPaymentDto requestPaymentDto);
    RequestPaymentDto toSaveDto(Payment payment);
    SendPaymentDto toSendDto(Payment payment);
}
