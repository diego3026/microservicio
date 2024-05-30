package com.carinventoryservice.controllers;

import com.carinventoryservice.models.dtos.RequestCarDto;
import com.carinventoryservice.services.CarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class CarController {
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/getCars")
    public ResponseEntity<?> getAll(){
        try {
            return ResponseEntity.ok().body(carService.findAll());
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> save(@RequestBody RequestCarDto requestCustomerDto){
        return ResponseEntity.ok().body(carService.save(requestCustomerDto));
    }

    @GetMapping("/getAvailableCars")
    public ResponseEntity<?> getAvaliableCars(){
        try {
            return ResponseEntity.ok().body(carService.findAvailableCars());
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/reserve/{id}")
    public ResponseEntity<?> reserve(@PathVariable UUID id){
        return ResponseEntity.ok().body(carService.reserve(id));
    }

    @GetMapping("/getCar/{id}")
    public ResponseEntity<?> returnCar(@PathVariable UUID id){
        try {
            // UUID uuid = UUID.nameUUIDFromBytes(String.valueOf(id).getBytes());
            return ResponseEntity.ok().body(carService.findById(id));
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
}
