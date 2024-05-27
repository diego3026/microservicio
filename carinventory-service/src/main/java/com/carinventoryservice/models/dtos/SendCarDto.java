package com.carinventoryservice.models.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class SendCarDto {
    private UUID id;
    private String model;
    private String maker;
    private Boolean available;
}
