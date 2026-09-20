package com.jsp.foodorder.food_order_app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jsp.foodorder.food_order_app.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>{
	public Optional<Customer> findCustomerByContact(String contact);
	public Optional<Customer> findCustomerByEmail(String email);
	public List<Customer> findCustomerByName(String name);

}
