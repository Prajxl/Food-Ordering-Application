package com.jsp.foodorder.food_order_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.foodorder.food_order_app.dto.OrderItemRequest;
import com.jsp.foodorder.food_order_app.dto.ResponseStructure;
import com.jsp.foodorder.food_order_app.entity.Order;
import com.jsp.foodorder.food_order_app.entity.OrderItem;
import com.jsp.foodorder.food_order_app.service.OrderItemService;

@RequestMapping("/api/orderitem")
@RestController
public class OrderItemController {
	@Autowired
	private OrderItemService orderItemService;
	
	@PostMapping("/{orderId}/additem")
	public ResponseEntity<ResponseStructure<Order>> addItemToExistingOrder(@PathVariable Integer orderId,@RequestBody OrderItemRequest request)
	{
		return new ResponseEntity<ResponseStructure<Order>>(orderItemService.addItemtoOrder(orderId, request),HttpStatus.OK);
	}
	
	@PostMapping("/quantity/{orderItemId}/{quantity}")
	public ResponseEntity<ResponseStructure<OrderItem>> updateItemQuantity(@PathVariable Integer orderItemId,@PathVariable  Integer quantity)
	{
		return new ResponseEntity<ResponseStructure<OrderItem>>(orderItemService.updateItemQuantity(orderItemId, quantity),HttpStatus.OK);
	}
	
	@DeleteMapping("/remove/{orderItemId}")
	public ResponseEntity<ResponseStructure<String>> removeItemfromOrder(@PathVariable Integer orderItemId)
	{
		return new ResponseEntity<ResponseStructure<String>>(orderItemService.deleteOrder(orderItemId),HttpStatus.OK);
	}
	
	@GetMapping("/all/{orderid}")
	public ResponseEntity<ResponseStructure<List<OrderItem>>> getAllOrderItem(@PathVariable Integer orderid)
	{
		return new ResponseEntity<ResponseStructure<List<OrderItem>>>(orderItemService.getAllOrderItem(orderid),HttpStatus.OK);
	}
}
