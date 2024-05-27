package com.carinventoryservice.services.impl;

import com.carinventoryservice.models.dtos.RequestCarDto;
import com.carinventoryservice.models.dtos.SendCarDto;
import com.carinventoryservice.models.entities.Car;
import com.carinventoryservice.models.mappers.CarMapper;
import com.carinventoryservice.repositories.CarRepository;
import com.carinventoryservice.services.CarService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final CarMapper carMapper;

    public CarServiceImpl(CarRepository carRepository, CarMapper carMapper) {
        this.carRepository = carRepository;
        this.carMapper = carMapper;
    }

    @Override
    public List<SendCarDto> findAll() {
        List<Car> carList = carRepository.findAll();
        return carList.stream().map(carMapper::toSendDto).toList();
    }

    @Override
    public SendCarDto findById(UUID id) {
        Car car = carRepository.findById(id).orElse(null);
        return carMapper.toSendDto(car);
    }

    @Override
    public SendCarDto save(RequestCarDto requestCarDto) {
        Car car = carMapper.toEntity(requestCarDto);
        car.setAvailable(true);
        return carMapper.toSendDto(carRepository.save(car));
    }

    @Override
    public SendCarDto update(UUID id, RequestCarDto requestCarDto) {
        Car car = carMapper.toEntity(requestCarDto);
        Car data = carRepository.findById(id).orElse(null);
        data.updateWith(car);
        carRepository.save(data);
        return carMapper.toSendDto(data);
    }

    @Override
    public void deleteById(UUID id) {
        carRepository.deleteById(id);
    }

    @Override
    public List<SendCarDto> findAvailableCars() {
        List<Car> carList = carRepository.findByAvailableTrue();
        return carList.stream().map(carMapper::toSendDto).toList();
    }

    @Override
    public SendCarDto reserve(UUID id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found"));
        if(!car.getAvailable()){
            throw new RuntimeException("Car not avaliable");
        }
        car.setAvailable(false);
        carRepository.save(car);
        return carMapper.toSendDto(car);
    }
}
