package com.bookingservice.services;

import com.bookingservice.models.dtos.RequestDataDto;
import com.bookingservice.models.dtos.RequestStatusDto;
import com.bookingservice.models.dtos.SendBookDto;

import java.util.List;
import java.util.UUID;

public interface BookService {
    List<SendBookDto> findAll();
    SendBookDto findById(UUID id);
    SendBookDto save(RequestDataDto bookDto);
    SendBookDto update(UUID id, RequestDataDto bookDto);
    void deleteById(UUID id);
    SendBookDto changeStatus(UUID id, RequestStatusDto requestStatusDto);
}
