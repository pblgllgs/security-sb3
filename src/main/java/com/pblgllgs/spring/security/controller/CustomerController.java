package com.pblgllgs.spring.security.controller;

import com.pblgllgs.spring.security.dto.CustomerDTO;
import com.pblgllgs.spring.security.dto.CustomerRequestDTO;
import com.pblgllgs.spring.security.dto.CustomerUpdateRequest;
import com.pblgllgs.spring.security.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public List<CustomerDTO> getCustomers() {
        return customerService.selectAllCustomers();
    }

    @GetMapping("/{customerId}")
    public CustomerDTO getCustomerById(@PathVariable("customerId") Integer customerId) {
        return customerService.getCustomerById(customerId);
    }

    @PostMapping
    public CustomerDTO addCustomer(@RequestBody CustomerRequestDTO customerRequestDTO) {
        return customerService.insertCustomer(customerRequestDTO);
    }

    @DeleteMapping("/{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Integer customerId) {
        customerService.deleteCustomerById(customerId);
    }

    @PutMapping("/{customerId}")
    public void updateCustomer(
            @PathVariable("customerId") Integer customerId,
            @RequestBody CustomerUpdateRequest customerUpdateRequest) {
        customerService.updateCustomer(customerId,customerUpdateRequest);
    }
}

