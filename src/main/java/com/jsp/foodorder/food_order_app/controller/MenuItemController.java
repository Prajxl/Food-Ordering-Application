package com.jsp.foodorder.food_order_app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.foodorder.food_order_app.dto.MenuItemRequest;
import com.jsp.foodorder.food_order_app.dto.ResponseStructure;
import com.jsp.foodorder.food_order_app.entity.MenuItem;
import com.jsp.foodorder.food_order_app.entity.Restaurent;
import com.jsp.foodorder.food_order_app.service.MenuItemService;

@RequestMapping("/api/menuitem")
@RestController
public class MenuItemController {
	@Autowired
	private MenuItemService menuService;
	
	@PostMapping
    public ResponseEntity<MenuItem> saveMenuItem(@RequestBody MenuItemRequest request) {
        return new ResponseEntity<>(menuService.saveMenuItem(request),HttpStatus.CREATED);
	}
	
	@PostMapping("/all")
	public ResponseEntity<ResponseStructure<List<MenuItem>>> saveItem(@RequestBody List<MenuItemRequest> requests)
	{
		return new ResponseEntity<>(menuService.saveAllItems(requests),HttpStatus.CREATED);
	}
	
	@GetMapping("/all")
	public ResponseEntity<ResponseStructure<List<MenuItem>>> getAllItems()
	{
		return new ResponseEntity<>(menuService.getAllMenuItems(),HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<MenuItem>> getItemById(@PathVariable Integer id)
	{
		return new ResponseEntity<>(menuService.getMenuItemById(id),HttpStatus.OK);
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<ResponseStructure<String>> getItemById(@PathVariable Integer id, @RequestBody Map<String,Object> data)
	{
		return new ResponseEntity<>(menuService.updatePriceAndAvailability(id,data),HttpStatus.OK);
	}
	
	@GetMapping("/sortPrice")
	public ResponseEntity<ResponseStructure<List<MenuItem>>> getBYSortingPrice()
	{
		return new ResponseEntity<>(menuService.getBySorting(),HttpStatus.OK);
	}
	
	@GetMapping("/name/{itemName}")
	public ResponseEntity<ResponseStructure<List<MenuItem>>> getItemByItemName(@PathVariable String itemName)
	{
		return new ResponseEntity<>(menuService.getItemByName(itemName),HttpStatus.OK);
	}
	
	@GetMapping("/name/{name}/items")
	public ResponseEntity<ResponseStructure<List<MenuItem>>> getItemsByRestaurantName(@PathVariable String name)
	{
      return new ResponseEntity<>(menuService.getItemsByRestaurantName(name),HttpStatus.OK);
	}
}
