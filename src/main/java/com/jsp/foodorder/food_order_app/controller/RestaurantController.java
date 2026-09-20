package com.jsp.foodorder.food_order_app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.foodorder.food_order_app.dto.ResponseStructure;
import com.jsp.foodorder.food_order_app.entity.Restaurent;
import com.jsp.foodorder.food_order_app.service.RestaurantService;

@RequestMapping("/api/Restaurent")
@RestController
public class RestaurantController {
	@Autowired
	private RestaurantService restService;
	
	// 1
	@PostMapping
	public ResponseEntity<ResponseStructure<Restaurent>> saveRestaurent(@RequestBody Restaurent rest)
	{
		return new ResponseEntity<>(restService.saveRestaurent(rest),HttpStatus.CREATED);
	}
	
	// 2
	@PostMapping("/all")
    public ResponseEntity<ResponseStructure<List<Restaurent>>> saveAllRestaurent(@RequestBody List<Restaurent> rest)
	{
		return new ResponseEntity<>(restService.saveAllRestaurent(rest),HttpStatus.CREATED);
	}
	
	@GetMapping("/all")
	public ResponseEntity<ResponseStructure<List<Restaurent>>> getAllRestaurent()
	{
		return new ResponseEntity<>(restService.getAllRestaurent(),HttpStatus.OK);
	}
	// 3
	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<Restaurent>> getById(@PathVariable Integer id)
	{
		return new ResponseEntity<>(restService.getById(id),HttpStatus.OK);
	}
	
	//4 
	
	@PutMapping
	public ResponseEntity<ResponseStructure<String>> updateRestaurent(@RequestBody Restaurent rest)
	{
		return new ResponseEntity<ResponseStructure<String>>(restService.updateById(rest),HttpStatus.OK);
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<ResponseStructure<String>> updatePartialRestaurent(@PathVariable Integer id ,@RequestBody Map<String, Object> data)
	{
		return new ResponseEntity<ResponseStructure<String>>(restService.updatePartially(id,data),HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseStructure<String>> deleteRecord(@PathVariable Integer id)
	{
		return new ResponseEntity<ResponseStructure<String>>(restService.deleteRecord(id),HttpStatus.OK);
	}
	
	@GetMapping("location/{location}")
	public ResponseEntity<ResponseStructure<List<Restaurent>>> getRestaurentByLocation(@PathVariable String location)
	{
		return new ResponseEntity<>(restService.getRestaurentByLocation(location),HttpStatus.OK);
	}
	
	@GetMapping("rating/{rating}")
	public ResponseEntity<ResponseStructure<List<Restaurent>>> getRestaurentRatingGreaterThan(@PathVariable Double rating)
	{
		return new ResponseEntity<>(restService.getRestaurentGreaterThan(rating),HttpStatus.OK);
	}
	
	
	
}
