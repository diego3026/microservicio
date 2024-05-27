package com.paymentservice.services.impl;

import com.paymentservice.models.dto.RequestPaymentDto;
import com.paymentservice.models.dto.SendPaymentDto;
import com.paymentservice.models.dto.booking.BookingDto;
import com.paymentservice.models.dto.booking.enums.BookStatus;
import com.paymentservice.models.entities.Payment;
import com.paymentservice.models.entities.enums.PaymentStatus;
import com.paymentservice.models.mappers.PaymentMapper;
import com.paymentservice.repositories.PaymentRepository;
import com.paymentservice.services.PaymentService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final RestTemplate restTemplate;

    public PaymentServiceImpl(PaymentRepository paymentRepository, PaymentMapper paymentMapper, RestTemplate restTemplate) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<SendPaymentDto> findAll() {
        List<Payment> paymentList = paymentRepository.findAll();
        return paymentList.stream().map(paymentMapper::toSendDto).toList();
    }

    @Override
    public SendPaymentDto findById(UUID id) {
        Payment payment = paymentRepository.findById(id).orElse(null);
        return paymentMapper.toSendDto(payment);
    }

    @Override
    public SendPaymentDto save(RequestPaymentDto requestPaymentDto) {
        Payment payment = paymentMapper.toEntity(requestPaymentDto);
        payment.setTransaction_id(UUID.randomUUID());
        boolean bookExistsAndNotCompleted = checkBook(requestPaymentDto.getBooking_id());

        if(bookExistsAndNotCompleted){
            if(updateBookStatus(requestPaymentDto)){
                payment.setStatus(PaymentStatus.COMPLETED);
            } else {
                payment.setStatus(PaymentStatus.FAILED);
            }
        } else {
            payment.setStatus(PaymentStatus.FAILED);
        }

        return paymentMapper.toSendDto(paymentRepository.save(payment));
    }

    private boolean updateBookStatus(RequestPaymentDto requestPaymentDto) throws JSONException {
        String url = "http://localhost:8081/api/v1/booking/getBookingById/" + requestPaymentDto.getBooking_id();
        String process_payment = "http://localhost:8081/api/v1/booking/updateStatus/" + requestPaymentDto.getBooking_id();

        ResponseEntity<BookingDto> bookingResponseEntity =
                restTemplate.getForEntity(url, BookingDto.class);
        BookingDto booking = bookingResponseEntity.getBody();

        if (booking.getStatus().equals(BookStatus.CONFIRMED)) {
            boolean failedTransaction = false;
            JSONObject requestBody = new JSONObject();
            if (requestPaymentDto.getAmount() >= 0) {
                requestBody.put("status", "COMPLETED");
                requestBody.put("start_date", LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                requestBody.put("end_date", LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            } else {
                requestBody.put("status", "FAILED");
                requestBody.put("start_date", JSONObject.NULL);
                requestBody.put("end_date", JSONObject.NULL);
                failedTransaction = true;
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody.toString(), headers);

            ResponseEntity<Void> responseEntity = restTemplate.exchange(
                    process_payment, HttpMethod.PUT, requestEntity, Void.class);
            if(failedTransaction){
                return false;
            }
            return responseEntity.getStatusCode().is2xxSuccessful();
        }
        return false;
    }

    private boolean checkBook(UUID bookingId) {
        String url = "http://localhost:8081/api/v1/booking/getBookingById/" + bookingId;
        try {
            ResponseEntity<BookingDto> response = restTemplate.getForEntity(url, BookingDto.class);
            return response.getStatusCode().is2xxSuccessful() && response.getBody() != null
                    && !response.getBody().getStatus().equals(BookStatus.COMPLETED);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public SendPaymentDto update(UUID id, RequestPaymentDto requestPaymentDto) {
        Payment payment = paymentMapper.toEntity(requestPaymentDto);
        Payment data = paymentRepository.findById(id).orElse(null);
        data.updateWith(payment);
        paymentRepository.save(data);
        return paymentMapper.toSendDto(data);
    }

    @Override
    public void delete(UUID id) {
        paymentRepository.deleteById(id);
    }
}
