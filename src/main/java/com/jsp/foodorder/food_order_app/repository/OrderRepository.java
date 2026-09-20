package com.jsp.foodorder.food_order_app.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jsp.foodorder.food_order_app.entity.Customer;
import com.jsp.foodorder.food_order_app.entity.Order;
import com.jsp.foodorder.food_order_app.entity.Status;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{
	public List<Order> findOrderByC(Customer customer_id);
	public List<Order> findOrderByStatus(Status status);
	
	@Query("""
		    SELECT DISTINCT o
		    FROM Order o, OrderItem oi, MenuItem mi, Restaurent r
		    WHERE o.orderId = oi.orders.orderId
		    AND oi.menu.itemId = mi.itemId
		    AND mi.rest.id = r.id
		    AND r.id = :restaurantId
		""")
	public 	List<Order> findAllOrdersByRestaurant(@Param("restaurantId") Integer restaurantId);
	
	@Query(" Select o from Order o where function('DATE',o.orderDateTime) = :date ")
	public List<Order> findOrderByDate(@Param("date") LocalDate date );
	
}
