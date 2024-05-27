package com.bookingservice.models.dtos.car;

import lombok.Data;

import java.util.UUID;

@Data
public class CarDto {
    private UUID id;
    private String model;
    private String maker;
    private Boolean available;
}
