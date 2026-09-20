package com.jsp.foodorder.food_order_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jsp.foodorder.food_order_app.entity.MenuItem;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Integer>{
	public List<MenuItem> findMenuItemByitemName(String itemName); 
	public List<MenuItem> findMenuItemByrest(Integer id);
}
