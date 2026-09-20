package com.jsp.foodorder.food_order_app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.foodorder.food_order_app.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer>{
	public Optional<OrderItem> findOrderByOrders(Integer order_Id);
	public List<OrderItem> findOrderItemByOrders(Integer order_Id);

}
