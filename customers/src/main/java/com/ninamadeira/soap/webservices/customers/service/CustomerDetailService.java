package com.ninamadeira.soap.webservices.customers.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ninamadeira.soap.webservices.customers.helper.Status;
import com.ninamadeira.soap.webservices.customers.repository.CustomerRepository;
import com.ninamadeira.soap.webservices.customers.soap.bean.Customer;

@Component
public class CustomerDetailService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	public Customer findById(Integer id) {
		Optional<Customer> customerOptional = customerRepository.findById(id);
		if(customerOptional.isPresent()) {
			return customerOptional.get();
		}
		return null;
	}
	
	public List<Customer> findAll(){
		return customerRepository.findAll();
	}
	
	public Status deleteById(Integer id) {
		try {
			customerRepository.deleteById(id);
			return Status.SUCCESS;
		}catch(Exception ex) {
			return Status.FAILURE;
		}
	}
	
	public Status insert(Customer customer) {
		try {
			customerRepository.save(customer);
			return Status.SUCCESS;
		}catch(Exception ex) {
			return Status.FAILURE;
		}
	}
}