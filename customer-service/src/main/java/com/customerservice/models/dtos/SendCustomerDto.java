package com.customerservice.models.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class SendCustomerDto {
    private UUID id;
    private String full_name;
}
