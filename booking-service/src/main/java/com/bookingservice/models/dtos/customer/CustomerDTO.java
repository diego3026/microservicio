package com.bookingservice.models.dtos.customer;

import lombok.Data;

import java.util.UUID;

@Data
public class CustomerDTO {
    private UUID id;
    private String full_name;
}
