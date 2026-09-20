package com.jsp.foodorder.food_order_app.controller;

import java.io.ObjectInputFilter.Status;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.foodorder.food_order_app.dto.ResponseStructure;
import com.jsp.foodorder.food_order_app.entity.Payment;
import com.jsp.foodorder.food_order_app.entity.PaymentMethod;
import com.jsp.foodorder.food_order_app.entity.PaymentStatus;
import com.jsp.foodorder.food_order_app.service.PaymentService;

@RequestMapping("/api/payment")
@RestController
public class PaymentController {
	@Autowired
	private PaymentService payService;
	
	@GetMapping("/{paymentId}")
	public ResponseEntity<ResponseStructure<Payment>> getPaymentById(@PathVariable  Integer paymentId)
	{
		return new ResponseEntity<ResponseStructure<Payment>>(payService.getPaymentById(paymentId),HttpStatus.OK);
	}
	@GetMapping("/order/{orderId}")
	public ResponseEntity<ResponseStructure<Payment>> getPaymentByorder(@PathVariable  Integer orderId)
	{
		return new ResponseEntity<ResponseStructure<Payment>>(payService.getPaymentByOrder(orderId),HttpStatus.OK);
	}
	@GetMapping("/paymentstatus/{paymentstatus}")
	public ResponseEntity<ResponseStructure<List<Payment>>> getPaymentByStatus(@PathVariable  PaymentStatus paymentstatus)
	{
		return new ResponseEntity<>(payService.getPaymentByStatus(paymentstatus),HttpStatus.OK);
	}
	@GetMapping("/paymentmethod/{paymentMethod}")
	public ResponseEntity<ResponseStructure<List<Payment>>> getPaymentByPaymentMethod(@PathVariable  PaymentMethod paymentMethod)
	{
		return new ResponseEntity<>(payService.getPaymentByPaymentMethod(paymentMethod),HttpStatus.OK);
	}
	@PatchMapping("/{paymentId}/{paymentStatus}")
	public ResponseEntity<ResponseStructure<Payment>> updatePaymentStatus(@PathVariable  Integer paymentId ,@PathVariable PaymentStatus paymentStatus )
	{
		return new ResponseEntity<ResponseStructure<Payment>>(payService.UpdatePaymentStatus(paymentId, paymentStatus),HttpStatus.OK);
	}
	
}
