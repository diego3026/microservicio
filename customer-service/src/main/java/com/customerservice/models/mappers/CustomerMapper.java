package com.customerservice.models.mappers;

import com.customerservice.models.dtos.RequestCustomerDto;
import com.customerservice.models.dtos.SendCustomerDto;
import com.customerservice.models.entities.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    Customer toEntity(RequestCustomerDto requestCustomerDto);
    RequestCustomerDto toSaveDto(Customer customer);
    SendCustomerDto toSendDto(Customer customer);
}
