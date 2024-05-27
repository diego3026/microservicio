package com.paymentservice.controllers;

import com.paymentservice.models.dto.RequestPaymentDto;
import com.paymentservice.services.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/getPayments")
    public ResponseEntity<?> getAll(){
        try {
            return ResponseEntity.ok().body(paymentService.findAll());
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getPaymentById/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id){
        try {
            // UUID uuid = UUID.nameUUIDFromBytes(String.valueOf(id).getBytes());
            return ResponseEntity.ok().body(paymentService.findById(id));
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/processPayment")
    public ResponseEntity<?> save(@RequestBody RequestPaymentDto requestPaymentDto){
        return ResponseEntity.ok().body(paymentService.save(requestPaymentDto));
    }
}
