package com.bookingservice.services.impl;

import com.bookingservice.models.dtos.RequestDataDto;
import com.bookingservice.models.dtos.RequestStatusDto;
import com.bookingservice.models.dtos.SendBookDto;
import com.bookingservice.models.dtos.car.CarDto;
import com.bookingservice.models.dtos.customer.CustomerDTO;
import com.bookingservice.models.entities.Book;
import com.bookingservice.models.entities.enums.BookStatus;
import com.bookingservice.models.mappers.BookMapper;
import com.bookingservice.repositories.BookRepository;
import com.bookingservice.services.BookService;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final RestTemplate restTemplate;

    public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper, RestTemplate restTemplate){
        this.bookMapper = bookMapper;
        this.bookRepository = bookRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<SendBookDto> findAll() {
        List<Book> bookList = bookRepository.findAll();
        return bookList.stream().map(bookMapper::toSendDto).toList();
    }

    @Override
    public SendBookDto findById(UUID id) {
        Book book = bookRepository.findById(id).orElse(null);
        return bookMapper.toSendDto(book);
    }

    @Override
    public SendBookDto save(RequestDataDto bookDto) {
        Book book = bookMapper.toEntity(bookDto);
        boolean customerExists = checkCustomerExists(bookDto.getCustomer_id());
        boolean carExists = checkCarExists(bookDto.getCar_id());
        if (customerExists && carExists) {
            if(updateCarStatus(bookDto)){
                book.setStatus(BookStatus.CONFIRMED);
                book.setStart_date(LocalDate.now());
            } else {
                book.setStatus(BookStatus.FAILED);
            }
        } else {
            book.setStatus(BookStatus.FAILED);
        }
        return bookMapper.toSendDto(bookRepository.save(book));
    }

    private boolean updateCarStatus(RequestDataDto bookDto){
        UUID carid = bookDto.getCar_id();
        if(checkCarAvailable(carid)){
            String reserve = "http://localhost:8082/api/v1/car/reserve/" + carid;
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
            ResponseEntity<CarDto> carDtoResponseEntity = restTemplate.exchange(
                    reserve, HttpMethod.PUT, requestEntity, CarDto.class);
            return carDtoResponseEntity.getStatusCode().is2xxSuccessful();
        } else {
            return false;
        }
    }

    @Override
    public SendBookDto update(UUID id, RequestDataDto bookDto) {
        Book book = bookMapper.toEntity(bookDto);
        Book data = bookRepository.findById(id).orElse(null);
        data.updateWith(book);
        bookRepository.save(data);
        return bookMapper.toSendDto(data);
    }

    @Override
    public void deleteById(UUID id) {
        bookRepository.deleteById(id);
    }

    @Override
    public SendBookDto changeStatus(UUID id, RequestStatusDto requestStatusDto) {
        Book book = bookMapper.statusToEntity(requestStatusDto);
        Book data = bookRepository.findById(id).orElse(null);
        data.setStatus(book.getStatus());
        data.setStart_date(book.getStart_date());
        data.setEnd_date(book.getEnd_date());
        bookRepository.save(data);
        return bookMapper.toSendDto(data);
    }

    private boolean checkCustomerExists(UUID customerId) {
        String url = "http://localhost:8081/api/v1/customer/getCustomerById/" + customerId;
        try {
            ResponseEntity<CustomerDTO> response = restTemplate.getForEntity(url, CustomerDTO.class);
            return response.getStatusCode().is2xxSuccessful() && response.getBody() != null;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean checkCarExists(UUID carId) {
        String url = "http://localhost:8082/api/v1/car/getCar/" + carId;
        try {
            ResponseEntity<CarDto> response = restTemplate.getForEntity(url, CarDto.class);
            return response.getStatusCode().is2xxSuccessful() && response.getBody() != null;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean checkCarAvailable(UUID carId) {
        String url = "http://localhost:8082/api/v1/car/getCar/" + carId;
        try {
            ResponseEntity<CarDto> response = restTemplate.getForEntity(url, CarDto.class);
            return response.getStatusCode().is2xxSuccessful() && Objects.requireNonNull(response.getBody()).getAvailable();
        } catch (Exception e) {
            return false;
        }
    }
}
