package com.jsp.foodorder.food_order_app.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.foodorder.food_order_app.dto.OrderRequest;
import com.jsp.foodorder.food_order_app.dto.ResponseStructure;
import com.jsp.foodorder.food_order_app.entity.Customer;
import com.jsp.foodorder.food_order_app.entity.Order;
import com.jsp.foodorder.food_order_app.entity.Status;
import com.jsp.foodorder.food_order_app.service.OrderService;

@RequestMapping("/api/order")
@RestController
public class OrderController {
	@Autowired
	private OrderService orderService;
	
	@PostMapping("/placeorder")
	public ResponseEntity<ResponseStructure<Order>> placeOrder(@RequestBody OrderRequest request)
	{
		return new ResponseEntity<ResponseStructure<Order>>(orderService.placeOrder(request),HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public ResponseEntity<ResponseStructure<List<Order>>> getAllOrder()
	{
		return new ResponseEntity<>(orderService.getAllOrder(),HttpStatus.OK);
	}
	
	@GetMapping("/customer/{customer_id}")
	public ResponseEntity<ResponseStructure<List<Order>>> getAllCustomerOrder(@PathVariable Customer customer_id)
	{
		return new ResponseEntity<>(orderService.getAllOrderOfCustomer(customer_id),HttpStatus.OK);
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<ResponseStructure<Order>> getById(@PathVariable Integer id)
	{
		return new ResponseEntity<>(orderService.getById(id),HttpStatus.OK);
	}
	
	@PatchMapping("/update/{id}")
	public ResponseEntity<ResponseStructure<Order>> getById(@PathVariable Integer id, @RequestBody Map<String , Object> data)
	{
		return new ResponseEntity<>(orderService.updateOrderByStatus(id, data),HttpStatus.OK);
	}
	
	@GetMapping("/cancel/{id}")
	public ResponseEntity<ResponseStructure<Order>> updateOrderStatus(@PathVariable Integer id)
	{
		return new ResponseEntity<ResponseStructure<Order>>(orderService.cancelOrder(id),HttpStatus.OK);
	}
	
	@GetMapping("restuarant/{restuarantId}")
	public ResponseEntity<ResponseStructure<List<Order>>> getAllOrderRestuarant(@PathVariable Integer restuarantId)
	{
		return new ResponseEntity<ResponseStructure<List<Order>>>(orderService.getAllOrdersReasturant(restuarantId),HttpStatus.OK);
	}
	
	@GetMapping("date/{date}")
	public ResponseEntity<ResponseStructure<List<Order>>> getOrdersByDate(@PathVariable LocalDate date)
	{
		return new ResponseEntity<ResponseStructure<List<Order>>>(orderService.getOrderByDate(date),HttpStatus.OK);
	}
	
	@GetMapping("status/{status}")
	public ResponseEntity<ResponseStructure<List<Order>>> getOrderByStatus(@PathVariable Status status)
	{
		return new ResponseEntity<ResponseStructure<List<Order>>>(orderService.getOrderByStatus(status),HttpStatus.OK);
	}
}
