package com.jsp.foodorder.food_order_app.repository;

import java.io.ObjectInputFilter.Status;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jsp.foodorder.food_order_app.entity.Payment;
import com.jsp.foodorder.food_order_app.entity.PaymentMethod;
import com.jsp.foodorder.food_order_app.entity.PaymentStatus;


@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer>{
	public Optional<Payment> findPaymentByOrderOrderId(Integer orderId);
	public List<Payment> findPaymentByPaymentStatus(PaymentStatus paymentStatus);
	public List<Payment> findPaymentByPaymentMethod(PaymentMethod paymentMethod);
}
