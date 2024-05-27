package com.customerservice.services;

import com.customerservice.models.dtos.RequestCustomerDto;
import com.customerservice.models.dtos.SendCustomerDto;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    List<SendCustomerDto> findAll();
    SendCustomerDto findById(UUID id);
    SendCustomerDto save(RequestCustomerDto requestCustomerDto);
    SendCustomerDto update(UUID id, RequestCustomerDto requestCustomerDto);
    void deleteById(UUID id);
}
