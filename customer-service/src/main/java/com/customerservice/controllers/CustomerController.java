package com.customerservice.controllers;

import com.customerservice.models.dtos.RequestCustomerDto;
import com.customerservice.services.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/getCustomers")
    public ResponseEntity<?> getAll(){
        try {
            return ResponseEntity.ok().body(customerService.findAll());
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getCustomerById/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id){
        try {
            // UUID uuid = UUID.nameUUIDFromBytes(String.valueOf(id).getBytes());
            return ResponseEntity.ok().body(customerService.findById(id));
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> save(@RequestBody RequestCustomerDto requestCustomerDto){
        return ResponseEntity.ok().body(customerService.save(requestCustomerDto));
    }
}
