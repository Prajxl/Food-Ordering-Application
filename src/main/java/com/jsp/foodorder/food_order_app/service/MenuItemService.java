package com.jsp.foodorder.food_order_app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.jsp.foodorder.food_order_app.dto.MenuItemRequest;
import com.jsp.foodorder.food_order_app.dto.ResponseStructure;
import com.jsp.foodorder.food_order_app.entity.MenuItem;
import com.jsp.foodorder.food_order_app.entity.Restaurent;
import com.jsp.foodorder.food_order_app.exception.IdNotFoundException;
import com.jsp.foodorder.food_order_app.exception.NoRecordAvailableException;
import com.jsp.foodorder.food_order_app.repository.MenuItemRepository;
import com.jsp.foodorder.food_order_app.repository.RestaurentRepo;

@Service
public class MenuItemService {
	@Autowired
	private MenuItemRepository menuRepo;
	
	@Autowired
	private RestaurentRepo restRepo;
	
	
//	public ResponseStructure<String> saveItems(MenuItem item)
//	{
//		ResponseStructure<String> res = new ResponseStructure<String>();
//		menuRepo.save(item);'
//		res.setStatusCode(HttpStatus.CREATED.value());
//		res.setMessage("one item are added to menu");
//		res.setData("Success");
//		return res;
//	}
	
	public MenuItem saveMenuItem(MenuItemRequest request) {

        Optional<Restaurent> opt = restRepo.findById(request.getRestaurantId());

        if (opt.isEmpty())
        {
            throw new IdNotFoundException("Restaurant ID not available");
        }
        Restaurent restaurant = opt.get();
        MenuItem item = new MenuItem();

        item.setItemName(request.getItemName());
        item.setPrice(request.getPrice());
        item.setAvailability(request.isAvailability());

        // Associate MenuItem with Restaurant
        item.setRest(restaurant);
        return menuRepo.save(item);
    }
	
//	public ResponseStructure<List<MenuItem>> saveAllItems(List<MenuItem> items)
//	{
//		ResponseStructure<List<MenuItem>> res = new ResponseStructure<>();
//		res.setStatusCode(HttpStatus.CREATED.value());
//		res.setMessage("All items are added to menu");
//		res.setData(menuRepo.saveAll(items));
//		return res;
//	}
	
	public ResponseStructure<List<MenuItem>> saveAllItems(List<MenuItemRequest> requests) {

	    ResponseStructure<List<MenuItem>> res =new ResponseStructure<>();
	    List<MenuItem> items = new ArrayList<>();

	    // Get restaurant ID from first request
	    Integer restaurantId = requests.get(0).getRestaurantId();
	    Optional<Restaurent> opt = restRepo.findById(restaurantId);
	    if (opt.isEmpty())
	    {
	        throw new IdNotFoundException("Restaurant ID not available");
	    }
	    
	    Restaurent restaurant = opt.get();
	    for (MenuItemRequest request : requests) {

	        // Make sure every item belongs to same restaurant
	        if (!request.getRestaurantId().equals(restaurantId))
	        {
	            throw new IllegalArgumentException("All items must belong to the same restaurant");
	        }

	        MenuItem item = new MenuItem();

	        item.setItemName(request.getItemName());
	        item.setPrice(request.getPrice());
	        item.setAvailability(request.isAvailability());

	        // Associate item with restaurant
	        item.setRest(restaurant);

	        items.add(item);
	    }

	    List<MenuItem> savedItems = menuRepo.saveAll(items);

	    res.setStatusCode(HttpStatus.CREATED.value());
	    res.setMessage("All items are added to menu");
	    res.setData(savedItems);
	    return res;
	}
	
	
	public ResponseStructure<List<MenuItem>> getAllMenuItems()
	{
		ResponseStructure<List<MenuItem>> res = new ResponseStructure<List<MenuItem>>();
		List<MenuItem> list = menuRepo.findAll();
		if(list.isEmpty()) {
			throw new NoRecordAvailableException("No Items are in menu");
		}else {
			res.setStatusCode(HttpStatus.OK.value());
			res.setMessage("All the Items in the menu are fetched");
			res.setData(list);
			return res;
		}
	}
	
	public ResponseStructure<MenuItem> getMenuItemById(Integer id)
	{
		ResponseStructure<MenuItem> res = new ResponseStructure<MenuItem>();
		Optional<MenuItem> opt = menuRepo.findById(id);
		if(opt.isEmpty()) {
			throw new IdNotFoundException("No Items are in this id");
		}else {
			res.setStatusCode(HttpStatus.OK.value());
			res.setMessage("item in this id fetched");
			res.setData(opt.get());
			return res;
		}
	}
	
	public ResponseStructure<String> updatePriceAndAvailability(Integer id,Map<String, Object> data)
	{
		ResponseStructure<String> res = new ResponseStructure<>();
		Optional<MenuItem> opt = menuRepo.findById(id);
		if(opt.isPresent())
		{
			MenuItem menuitem = opt.get();
			for(Map.Entry<String, Object> entry : data.entrySet())
			{
				String key = entry.getKey();
				Object value = entry.getValue();
				switch (key) {
				case "price" : menuitem.setPrice((Double)value);
				break;
				case "availability" : menuitem.setAvailability((Boolean)value);
				break;
				}
			}
			menuRepo.save(menuitem);
			res.setMessage("Partially Updated the price and Availability");
			res.setStatusCode(HttpStatus.OK.value());
			res.setData("Successfull");
			return res;
		}else {
			throw new IdNotFoundException("No Record Available by this Id");
		}
	}
	
	public ResponseStructure<List<MenuItem>> getBySorting()
	{
		ResponseStructure<List<MenuItem>> res = new ResponseStructure<List<MenuItem>>();
		List<MenuItem> items = menuRepo.findAll(Sort.by("price").ascending()); // price in string by using " " 
		if(items.isEmpty())
		{
			throw new NoRecordAvailableException("No Items Present");
		}else {
			res.setMessage("Prices Sorted in Ascecnding order");
			res.setStatusCode(HttpStatus.OK.value());
			res.setData(items);
			return res;
		}
	}
	
	public ResponseStructure<List<MenuItem>> getItemByName(String name)
	{
		ResponseStructure<List<MenuItem>> res = new ResponseStructure<List<MenuItem>>();
		List<MenuItem> list = menuRepo.findMenuItemByitemName(name);
		if(list.isEmpty()) {
			throw new NoRecordAvailableException("No Items Available by this name");
		}else {
			res.setStatusCode(HttpStatus.OK.value());
			res.setMessage("Items are fetched");
			res.setData(list);
			return res;
		}
	}
	
	public ResponseStructure<List<MenuItem>> getItemsByRestaurantName(String name) {

	    ResponseStructure<List<MenuItem>> res = new ResponseStructure<>();
	    Optional<Restaurent> opt = restRepo.findByName(name);

	    if (opt.isEmpty()) {
	        throw new IdNotFoundException("Restaurant name not found");
	    }

	    Restaurent restaurant = opt.get();

	    List<MenuItem> items = restaurant.getItemlist();

	    res.setStatusCode(HttpStatus.OK.value());
	    res.setMessage("All items of restaurant are fetched");
	    res.setData(items);

	    return res;
	}
}
