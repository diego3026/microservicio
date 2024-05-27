package com.carinventoryservice.services;

import com.carinventoryservice.models.dtos.RequestCarDto;
import com.carinventoryservice.models.dtos.SendCarDto;

import java.util.List;
import java.util.UUID;

public interface CarService {
    List<SendCarDto> findAll();
    SendCarDto findById(UUID id);
    SendCarDto save(RequestCarDto requestCarDto);
    SendCarDto update(UUID id, RequestCarDto requestCarDto);
    void deleteById(UUID id);
    List<SendCarDto> findAvailableCars();
    SendCarDto reserve(UUID id);
}
