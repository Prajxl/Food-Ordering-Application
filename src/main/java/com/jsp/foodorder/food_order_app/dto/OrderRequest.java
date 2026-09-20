package com.jsp.foodorder.food_order_app.dto;

import java.util.List;

import com.jsp.foodorder.food_order_app.entity.PaymentMethod;

public class OrderRequest {
	private Integer customerId;
	private List<OrderItemRequest> items;
	private PaymentMethod paymentMethod;
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public List<OrderItemRequest> getItems() {
		return items;
	}
	public void setItems(List<OrderItemRequest> items) {
		this.items = items;
	}
	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	
}
