package com.jsp.foodorder.food_order_app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.jsp.foodorder.food_order_app.entity.Restaurent;

@Repository
public interface RestaurentRepo extends JpaRepository<Restaurent, Integer>{
	public List<Restaurent> findRestaurentByLocation(String location);
	public List<Restaurent> findRestaurentByRatingGreaterThan(Double rating);
	public Optional<Restaurent> findByName(String name);
}


