package com.ninamadeira.soap.webservices.customers.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ninamadeira.soap.webservices.customers.soap.bean.Customer;


public interface CustomerRepository extends JpaRepository<Customer, Integer>{

}