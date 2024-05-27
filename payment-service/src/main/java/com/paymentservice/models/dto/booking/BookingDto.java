package com.paymentservice.models.dto.booking;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.paymentservice.models.dto.booking.enums.BookStatus;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class BookingDto {
    private UUID id;
    private UUID car_id;
    private UUID customer_id;
    private BookStatus status;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate start_date;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate end_date;
}
