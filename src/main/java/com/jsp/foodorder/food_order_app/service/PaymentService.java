package com.jsp.foodorder.food_order_app.service;

import java.io.ObjectInputFilter.Status;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.jsp.foodorder.food_order_app.dto.ResponseStructure;
import com.jsp.foodorder.food_order_app.entity.Payment;
import com.jsp.foodorder.food_order_app.entity.PaymentMethod;
import com.jsp.foodorder.food_order_app.entity.PaymentStatus;
import com.jsp.foodorder.food_order_app.exception.IdNotFoundException;
import com.jsp.foodorder.food_order_app.exception.NoRecordAvailableException;
import com.jsp.foodorder.food_order_app.repository.PaymentRepository;

@Service
public class PaymentService {
	@Autowired
	private PaymentRepository payRepo;
	
	public ResponseStructure<Payment> getPaymentById(Integer paymentId)
	{
		ResponseStructure<Payment> res = new ResponseStructure<Payment>();
		Optional<Payment> opt = payRepo.findById(paymentId);
		if(opt.isEmpty())
		{
			throw new IdNotFoundException("payment Id not found");
		}
		res.setStatusCode(HttpStatus.OK.value());
		res.setMessage("Payment done");
		res.setData(opt.get());
		return res;
	}
	
	public ResponseStructure<Payment> getPaymentByOrder(Integer orderId)
	{
		ResponseStructure<Payment> res = new ResponseStructure<Payment>();
		Optional<Payment> opt = payRepo.findPaymentByOrderOrderId(orderId);
		if(opt.isEmpty())
		{
			throw new IdNotFoundException("payment Id not found");
		}
		res.setStatusCode(HttpStatus.OK.value());
		res.setMessage("Payment done");
		res.setData(opt.get());
		return res;
	}
	
	public ResponseStructure<List<Payment>> getPaymentByStatus(PaymentStatus paymentStatus)
	{
		ResponseStructure<List<Payment>> res = new ResponseStructure<>();
		List<Payment> olist = payRepo.findPaymentByPaymentStatus(paymentStatus);
		if(olist.isEmpty())
		{
			throw new NoRecordAvailableException("No Record available by this status");
		}
		res.setStatusCode(HttpStatus.OK.value());
		res.setMessage("Payment with this status is fetched");
		res.setData(olist);
		return res;
	}
	
	public ResponseStructure<List<Payment>> getPaymentByPaymentMethod( PaymentMethod paymentMethod)
	{
		ResponseStructure<List<Payment>> res = new ResponseStructure<>();
		List<Payment> olist = payRepo.findPaymentByPaymentMethod(paymentMethod);
		if(olist.isEmpty())
		{
			throw new NoRecordAvailableException("No Record available by this payment method");
		}
		res.setStatusCode(HttpStatus.OK.value());
		res.setMessage("Payment with this method is fetched");
		res.setData(olist);
		return res;
	}
	
	public ResponseStructure<Payment> UpdatePaymentStatus(Integer paymentId,PaymentStatus status)
	{
		ResponseStructure<Payment> res = new ResponseStructure<Payment>();
		Optional<Payment> opt = payRepo.findById(paymentId);
		if(opt.isEmpty())
		{
			throw new IdNotFoundException("payment Id not found");
		}
		Payment pay = opt.get();
		pay.setPaymentStatus(status);
		
		res.setStatusCode(HttpStatus.OK.value());
		res.setMessage("Payment Status updated");
		res.setData(payRepo.save(pay));
		return res;
	}
	
}
