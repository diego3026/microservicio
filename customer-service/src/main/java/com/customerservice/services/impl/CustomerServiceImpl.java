package com.customerservice.services.impl;

import com.customerservice.models.dtos.RequestCustomerDto;
import com.customerservice.models.dtos.SendCustomerDto;
import com.customerservice.models.entities.Customer;
import com.customerservice.models.mappers.CustomerMapper;
import com.customerservice.repositories.CustomerRepository;
import com.customerservice.services.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public List<SendCustomerDto> findAll() {
        List<Customer> customerList = customerRepository.findAll();
        return customerList.stream().map(customerMapper::toSendDto).toList();
    }

    @Override
    public SendCustomerDto findById(UUID id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        return customerMapper.toSendDto(customer);
    }

    @Override
    public SendCustomerDto save(RequestCustomerDto requestCustomerDto) {
        Customer customer = customerMapper.toEntity(requestCustomerDto);
        return customerMapper.toSendDto(customerRepository.save(customer));
    }

    @Override
    public SendCustomerDto update(UUID id, RequestCustomerDto requestCustomerDto) {
        Customer customer = customerMapper.toEntity(requestCustomerDto);
        Customer data = customerRepository.findById(id).orElse(null);
        data.updateWith(customer);
        customerRepository.save(data);
        return customerMapper.toSendDto(data);
    }

    @Override
    public void deleteById(UUID id) {
        customerRepository.deleteById(id);
    }
}
