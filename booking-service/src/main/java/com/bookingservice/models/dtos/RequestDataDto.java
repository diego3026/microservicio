package com.bookingservice.models.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class RequestDataDto {
    private UUID car_id;
    private UUID customer_id;
}
