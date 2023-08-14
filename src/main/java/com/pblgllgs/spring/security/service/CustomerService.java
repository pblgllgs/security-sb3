package com.pblgllgs.spring.security.service;


import com.pblgllgs.spring.security.dto.CustomerDTO;
import com.pblgllgs.spring.security.dto.CustomerRequestDTO;
import com.pblgllgs.spring.security.dto.CustomerUpdateRequest;
import com.pblgllgs.spring.security.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    List<CustomerDTO> selectAllCustomers();

    CustomerDTO getCustomerById(Integer customerId);

    CustomerDTO insertCustomer(CustomerRequestDTO customerRequestDTO);

    boolean existsPersonWithEmail(String email);

    void deleteCustomerById(Integer customerId);

    boolean existsPersonWithId(Integer customerId);

    CustomerDTO updateCustomer(Integer customerId,CustomerUpdateRequest customerUpdateRequest);

    Optional<Customer> selectUserByEmail(String email);
}
