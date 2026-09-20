package com.jsp.foodorder.food_order_app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.foodorder.food_order_app.dto.ResponseStructure;
import com.jsp.foodorder.food_order_app.entity.Customer;
import com.jsp.foodorder.food_order_app.service.CustomerService;

@RequestMapping("/api/customer")
@RestController
public class CustomerController {
	@Autowired
	private CustomerService customerService;
	
	@PostMapping
	public ResponseEntity<ResponseStructure<Customer>> saveCustomer(@RequestBody Customer cust)
	{
		return new ResponseEntity<>(customerService.saveCustomer(cust),HttpStatus.CREATED);
	}
	
	@PostMapping("/all")
	public ResponseEntity<ResponseStructure<List<Customer>>> saveAllCustomer(@RequestBody List<Customer> cust)
	{
		return new ResponseEntity<>(customerService.saveAllCustomer(cust),HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<Customer>> getCustomerById(@PathVariable Integer id)
	{
		return new ResponseEntity<>(customerService.getByID(id),HttpStatus.OK);
	}
	
	@PutMapping
	public ResponseEntity<ResponseStructure<String>> updateCustomerById(@RequestBody Customer cust)
	{
		return new ResponseEntity<>(customerService.updateById(cust),HttpStatus.OK);
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<ResponseStructure<String>> updatePartialCustomer(@PathVariable Integer id , @RequestBody Map<String , Object> data)
	{
		return new ResponseEntity<>(customerService.updatePartialCustomer(id, data),HttpStatus.OK);
	}
	
	// 6
	
	@GetMapping("/contact/{contact}")
	public ResponseEntity<ResponseStructure<Customer>> getByContact(@PathVariable String contact)
	{
		return new ResponseEntity<>(customerService.getByContact(contact),HttpStatus.OK);
	}
	
	// 7
	
	@GetMapping("/email/{email}")
	public ResponseEntity<ResponseStructure<Customer>> getByEmail(@PathVariable String email)
	{
		return new ResponseEntity<>(customerService.getByEmail(email),HttpStatus.OK);
	}
	
	// 8 
	
	@GetMapping("/name/{name}")
	public ResponseEntity<ResponseStructure<List<Customer>>> getByName(@PathVariable String name)
	{
		return new ResponseEntity<>(customerService.getByName(name),HttpStatus.OK);
	}
}
