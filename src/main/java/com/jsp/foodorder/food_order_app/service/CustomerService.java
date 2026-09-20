package com.jsp.foodorder.food_order_app.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.jsp.foodorder.food_order_app.dto.ResponseStructure;
import com.jsp.foodorder.food_order_app.entity.Customer;
import com.jsp.foodorder.food_order_app.exception.IdNotFoundException;
import com.jsp.foodorder.food_order_app.exception.InvalidPhoneNumber;
import com.jsp.foodorder.food_order_app.exception.NoRecordAvailableException;
import com.jsp.foodorder.food_order_app.repository.CustomerRepository;

@Service
public class CustomerService {
	@Autowired
	private CustomerRepository customerRepo;
	
	// 1 . create one customer 
	public ResponseStructure<Customer> saveCustomer(Customer cust)
	{
		ResponseStructure<Customer> res = new ResponseStructure<Customer>();
		if(cust.getContact().length()==10) {
		res.setMessage("One Customer Account Created");
		res.setStatusCode(HttpStatus.CREATED.value());
		res.setData(customerRepo.save(cust));
		return res;
		}else {
			throw new InvalidPhoneNumber("Invalid Phone Number");
		}
	}
	// 2 . saveAll Customer
	
	public ResponseStructure<List<Customer>> saveAllCustomer(List<Customer> customers)
	{
		ResponseStructure<List<Customer>> res = new ResponseStructure<List<Customer>>();
		for(Customer c : customers)
		{
			if(c.getContact().length()!=10)
			{
				throw new InvalidPhoneNumber("Invalid Phone Number"+c.getContact());
			}
		}
		res.setMessage("Customers got saved");
		res.setStatusCode(HttpStatus.CREATED.value());
		res.setData(customerRepo.saveAll(customers));
		return res;
	}
	
	// 3. get by id
	
	public ResponseStructure<Customer> getByID(Integer id)
	{
		Optional<Customer> opt = customerRepo.findById(id);
		ResponseStructure<Customer> res = new ResponseStructure<Customer>();
		if(opt.isEmpty())
		{
			throw new IdNotFoundException("Id not Found "+id);
		}else {
			res.setMessage("Customer fetched by id "+opt.get());
			res.setStatusCode(HttpStatus.OK.value());
			res.setData(opt.get());
			return res;
		}
	}
	
	// 4. update customer using id
	
	public ResponseStructure<String> updateById(Customer cust)
	{
		ResponseStructure<String> res = new ResponseStructure<>();
		if(cust.getId()==null)
		{
			res.setMessage("Id must be passed");
			res.setStatusCode(HttpStatus.BAD_REQUEST.value());
			res.setData("Failure");
			return res;
		}
		Optional<Customer> opt = customerRepo.findById(cust.getId());
		if(opt.isEmpty())
		{
			throw new IdNotFoundException("Id not Found "+cust.getId());
		}else {
			customerRepo.save(cust);
			res.setMessage("Updated Successfully");
			res.setStatusCode(HttpStatus.OK.value());
			res.setData("Success");
			return res;
		}
	}
	
	// 5 update partial record
	public ResponseStructure<String> updatePartialCustomer(Integer id , Map<String , Object> data)
	{
		ResponseStructure<String> res = new ResponseStructure<String>();
		Optional<Customer> opt = customerRepo.findById(id);
		if(opt.isPresent())
		{
			Customer customer = opt.get();
			for(Map.Entry<String, Object> entry : data.entrySet())
			{
				String key = entry.getKey();
				Object value = entry.getValue();
				
				switch(key) {
				case "name" : customer.setName((String)value);
				break;
				case "email": customer.setEmail((String)value);
				break;
				case "contact":
					String contact = (String) value;
					if(contact.length()!=10)
					{
						throw new InvalidPhoneNumber("Contact Number Not proper");
					}else {
						customer.setContact(contact);
					}
				break;
				case "address":customer.setAddress((String)value);
				break;
				}
			}
			customerRepo.save(customer);
			res.setStatusCode(HttpStatus.OK.value());
			res.setMessage("Customer Partially Updated Successfully");
			res.setData("Success");
			return res;
		}else {
			throw new IdNotFoundException("Id not Exist");
		}
	}
	
	// 6 find by contact
	
	public ResponseStructure<Customer> getByContact(String contact)
	{
		ResponseStructure<Customer> res = new ResponseStructure<Customer>();
		Optional<Customer> opt = customerRepo.findCustomerByContact(contact);
		if(opt.isEmpty())
		{
			throw new NoRecordAvailableException("No Record Available by THis contact Number");
		}else {
			res.setMessage("Contact Present");
			res.setStatusCode(HttpStatus.OK.value());
			res.setData(opt.get());
			return res;
		}
	}
	
	// 7 find By email
	
	public ResponseStructure<Customer> getByEmail(String email)
	{
		ResponseStructure<Customer> res = new ResponseStructure<Customer>();
		Optional<Customer> opt = customerRepo.findCustomerByEmail(email);
		if(opt.isEmpty())
		{
			throw new NoRecordAvailableException("No Record Available by THis Email");
		}else {
			res.setMessage("User with this Email is Present");
			res.setStatusCode(HttpStatus.OK.value());
			res.setData(opt.get());
			return res;
		}
	}
	
	// 8 get customer by name
	public ResponseStructure<List<Customer>> getByName(String name)
	{
		ResponseStructure<List<Customer>>  res = new ResponseStructure<List<Customer>>();
		List<Customer> customers = customerRepo.findCustomerByName(name);
		if(customers.isEmpty())
		{
			throw new NoRecordAvailableException("No Record Available by this name");
		}else {
			res.setMessage("Records available");
			res.setStatusCode(HttpStatus.OK.value());
			res.setData(customers);
			return res;
		}
		
	}
}
