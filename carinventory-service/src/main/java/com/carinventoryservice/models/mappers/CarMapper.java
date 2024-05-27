package com.carinventoryservice.models.mappers;

import com.carinventoryservice.models.dtos.RequestCarDto;
import com.carinventoryservice.models.dtos.SendCarDto;
import com.carinventoryservice.models.entities.Car;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CarMapper {
    Car toEntity(RequestCarDto requestCarDto);
    SendCarDto toSendDto(Car car);
    RequestCarDto toSaveDto(Car car);
}
