package com.pblgllgs.spring.security.service;

import com.pblgllgs.spring.security.dto.CustomerDTO;
import com.pblgllgs.spring.security.dto.CustomerRequestDTO;
import com.pblgllgs.spring.security.dto.CustomerUpdateRequest;
import com.pblgllgs.spring.security.entity.Customer;
import com.pblgllgs.spring.security.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository("jpa")
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;


    @Override
    public List<CustomerDTO> selectAllCustomers() {
        return customerRepository.findAll().stream().map(customer -> modelMapper.map(customer,CustomerDTO.class)).collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(Integer customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        return modelMapper.map(customer,CustomerDTO.class);
    }

    @Override
    public CustomerDTO insertCustomer(CustomerRequestDTO customerRequestDTO) {
        Customer customerSaved= customerRepository.save(modelMapper.map(customerRequestDTO, Customer.class));
        return modelMapper.map(customerSaved, CustomerDTO.class);
    }

    @Override
    public boolean existsPersonWithEmail(String email) {
        return customerRepository.existsCustomerByEmail(email);
    }

    @Override
    public void deleteCustomerById(Integer customerId) {
        customerRepository.deleteById(customerId);
    }

    @Override
    public boolean existsPersonWithId(Integer customerId) {
        return customerRepository.existsCustomerById(customerId);
    }

    @Override
    public CustomerDTO updateCustomer(Integer id, CustomerUpdateRequest customerUpdateRequest) {
        Customer customerDb = customerRepository.findById(id).get();
        customerDb.setName(customerUpdateRequest.getName());
        customerDb.setGender(customerUpdateRequest.getGender());
        customerDb.setAge(customerUpdateRequest.getAge());
        Customer customerUpdated = customerRepository.save(customerDb);
        return modelMapper.map(customerUpdated,CustomerDTO.class);
    }

    @Override
    public Optional<Customer> selectUserByEmail(String email) {
        return customerRepository.findCustomerByEmail(email);
    }

}