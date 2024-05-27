package com.bookingservice.controllers;

import com.bookingservice.models.dtos.RequestDataDto;
import com.bookingservice.models.dtos.RequestStatusDto;
import com.bookingservice.services.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/booking")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @GetMapping("/getBookings")
    public ResponseEntity<?> getAll(){
        try {
            return ResponseEntity.ok().body(bookService.findAll());
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getBookingById/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id){
        try {
            // UUID uuid = UUID.nameUUIDFromBytes(String.valueOf(id).getBytes());
            return ResponseEntity.ok().body(bookService.findById(id));
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/createBooking")
    public ResponseEntity<?> create(@RequestBody RequestDataDto requestDataDto){
        return ResponseEntity.ok().body(bookService.save(requestDataDto));
    }

    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody RequestStatusDto requestStatusDto){
        return ResponseEntity.ok().body(bookService.changeStatus(id, requestStatusDto));
    }
}
