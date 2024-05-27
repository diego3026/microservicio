package com.bookingservice.models.mappers;

import com.bookingservice.models.dtos.RequestDataDto;
import com.bookingservice.models.dtos.RequestStatusDto;
import com.bookingservice.models.dtos.SendBookDto;
import com.bookingservice.models.entities.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface BookMapper {

//    @Mapping(source = "car_id", target = "car_id", qualifiedByName = "convertLongToUUID")
//    @Mapping(source = "customer_id", target = "customer_id", qualifiedByName = "convertLongToUUID")
    Book toEntity(RequestDataDto requestDataDto);
    Book statusToEntity(RequestStatusDto requestStatusDto);

//    @Mapping(source = "car_id", target = "car_id", qualifiedByName = "convertUUIDToLong")
//    @Mapping(source = "customer_id", target = "customer_id", qualifiedByName = "convertUUIDToLong")
    RequestDataDto toSaveDto(Book book);

//    @Mapping(source = "id", target = "id", qualifiedByName = "convertUUIDToLong")
//    @Mapping(source = "car_id", target = "car_id", qualifiedByName = "convertUUIDToLong")
//    @Mapping(source = "customer_id", target = "customer_id", qualifiedByName = "convertUUIDToLong")
    SendBookDto toSendDto(Book book);

//    @Named("convertLongToUUID")
//    static UUID convertLongToUUID(Long longValue) {
//        return new UUID(0L, longValue);
//    }
//
//    @Named("convertUUIDToLong")
//    static Long convertUUIDToLong(UUID uuid) {
//        return uuid.getLeastSignificantBits();
//    }
}
