package com.ninamadeira.soap.webservices.customers.soap;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.ninamadeira.soap.webservices.customers.service.CustomerDetailService;
import com.ninamadeira.soap.webservices.customers.soap.bean.Customer;
import com.ninamadeira.soap.webservices.customers.soap.exception.CustomerNotFoundException;

import br.com.ninamadeira.CustomerDetail;
import br.com.ninamadeira.DeleteCustomerRequest;
import br.com.ninamadeira.DeleteCustomerResponse;
import br.com.ninamadeira.GetAllCustomerDetailRequest;
import br.com.ninamadeira.GetAllCustomerDetailResponse;
import br.com.ninamadeira.GetCustomerDetailRequest;
import br.com.ninamadeira.GetCustomerDetailResponse;
import br.com.ninamadeira.InsertCustomerRequest;
import br.com.ninamadeira.InsertCustomerResponse;

@Endpoint
public class CustomerDetailEndPoint {
	
	@Autowired
	CustomerDetailService service;

	@PayloadRoot(namespace = "http://ninamadeira.com.br", localPart = "GetCustomerDetailRequest")
	@ResponsePayload
	public GetCustomerDetailResponse processCustomerDetailRequest(@RequestPayload GetCustomerDetailRequest req) throws Exception {
		Customer customer = service.findById(req.getId());
		if(customer == null) {
			throw new CustomerNotFoundException("Invalid Customer id "+req.getId());
		}
		return convertToGetCustomerDetailResponse(customer);
	}
	
	private GetCustomerDetailResponse convertToGetCustomerDetailResponse(Customer customer) {
		GetCustomerDetailResponse resp = new GetCustomerDetailResponse();
		resp.setCustomerDetail(convertToCustomerDetail(customer));
		return resp;
	}
	
	private CustomerDetail convertToCustomerDetail(Customer customer) {
		CustomerDetail customerDetail = new CustomerDetail();
		customerDetail.setId(customer.getId());
		customerDetail.setName(customer.getName());
		customerDetail.setPhone(customer.getPhone());
		customerDetail.setEmail(customer.getEmail());
		return customerDetail;
	}
	
	@PayloadRoot(namespace="http://ninamadeira.com.br", localPart="GetAllCustomerDetailRequest")
	@ResponsePayload
	public GetAllCustomerDetailResponse processGetAllCustomerDetailRequest(@RequestPayload GetAllCustomerDetailRequest req) {
		List<Customer> customers = service.findAll();
		return convertToGetAllCustomerDetailResponse(customers);
	}
	
	private GetAllCustomerDetailResponse convertToGetAllCustomerDetailResponse(List<Customer> customers) {
		GetAllCustomerDetailResponse resp = new GetAllCustomerDetailResponse();
		customers.stream().forEach(c -> resp.getCustomerDetail().add(convertToCustomerDetail(c)));
		return resp;
	}
	
	private Customer convertCustomer(CustomerDetail customerDetail) {
		return new Customer(customerDetail.getId(),customerDetail.getName(),customerDetail.getPhone(),customerDetail.getEmail());
	}
	
	@PayloadRoot(namespace="http://ninamadeira.com.br", localPart="DeleteCustomerRequest")
	@ResponsePayload
	public DeleteCustomerResponse deleteCustomerRequest(@RequestPayload DeleteCustomerRequest req) {
		DeleteCustomerResponse resp = new DeleteCustomerResponse();
		resp.setStatus(convertStatusSoap(service.deleteById(req.getId())));
		return resp;
	}
	
	private br.com.ninamadeira.Status convertStatusSoap(
			com.ninamadeira.soap.webservices.customers.helper.Status statusService) {
		if(statusService == com.ninamadeira.soap.webservices.customers.helper.Status.FAILURE) {
			return br.com.ninamadeira.Status.FAILURE;
		}
		return br.com.ninamadeira.Status.SUCCESS;
	}
	
	@PayloadRoot(namespace="http://ninamadeira.com.br", localPart="InsertCustomerRequest")
	@ResponsePayload
	public InsertCustomerResponse insertCustomerRequest(@RequestPayload InsertCustomerRequest req) {
		InsertCustomerResponse resp = new InsertCustomerResponse();
		resp.setStatus(convertStatusSoap(service.insert(convertCustomer(req.getCustomerDetail()))));
		return resp;
	}
	

}
