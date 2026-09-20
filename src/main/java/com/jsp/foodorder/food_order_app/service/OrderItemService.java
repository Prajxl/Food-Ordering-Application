package com.jsp.foodorder.food_order_app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.jsp.foodorder.food_order_app.dto.OrderItemRequest;
import com.jsp.foodorder.food_order_app.dto.ResponseStructure;
import com.jsp.foodorder.food_order_app.entity.MenuItem;
import com.jsp.foodorder.food_order_app.entity.Order;
import com.jsp.foodorder.food_order_app.entity.OrderItem;
import com.jsp.foodorder.food_order_app.entity.Status;
import com.jsp.foodorder.food_order_app.exception.IdNotFoundException;
import com.jsp.foodorder.food_order_app.exception.NoRecordAvailableException;
import com.jsp.foodorder.food_order_app.repository.MenuItemRepository;
import com.jsp.foodorder.food_order_app.repository.OrderItemRepository;
import com.jsp.foodorder.food_order_app.repository.OrderRepository;

@Service
public class OrderItemService {
	@Autowired
	private OrderItemRepository orderItemRepo;
	
	@Autowired
	private OrderRepository orderRepo;
	
	@Autowired
	private MenuItemRepository menuRepo;
	
	public ResponseStructure<Order> addItemtoOrder(Integer orderId , OrderItemRequest request)
	{
		ResponseStructure<Order> res = new ResponseStructure<Order>();
		Optional<Order> opt = orderRepo.findById(orderId);
		
		if(opt.isEmpty())
		{
			throw new NoRecordAvailableException("No Order Available by this id");
		}
		if(request.getQuantity()<=0)
		{
			throw new IllegalArgumentException("Minimum Quantity is 1");
		}
		Order order = opt.get();
		if(order.getStatus() == Status.cancelled || order.getStatus()==Status.Delivered || order.getStatus() == Status.PreparingFood || order.getStatus() == Status.OutForDelivery)
		{
			throw new IllegalArgumentException("Order already in Process cant update now");
		}
		
		Optional<MenuItem> menuopt = menuRepo.findById(request.getMenuItemId());
		
		if(menuopt.isEmpty())
		{
			throw new IllegalArgumentException("No Record menu items are added");
		}
		
		MenuItem menu = menuopt.get();
		
		if(!menu.isAvailability())
		{
			throw new IllegalArgumentException("Item is Un available ");
		}
		double subtotal = menu.getPrice() * request.getQuantity();
		
		OrderItem oi = new OrderItem();
		oi.setMenu(menu);
		oi.setSubTotal(subtotal);
		oi.setQuantity(request.getQuantity());
		oi.setOrders(order);
		orderItemRepo.save(oi);
		
		double total = order.getTotalAmount() + subtotal;
		order.setTotalAmount(total);
		res.setMessage("Order is updated");
		res.setStatusCode(HttpStatus.OK.value());
		res.setData(orderRepo.save(order));
		return res;
	}
	
	// update item quantity
	
	public ResponseStructure<OrderItem> updateItemQuantity(Integer id,Integer quantity)
	{
		ResponseStructure<OrderItem> res = new ResponseStructure<>();
		Optional<OrderItem> opt = orderItemRepo.findById(id);
		if(opt.isEmpty())
		{
			throw new IdNotFoundException("No item is present with this id");
		}
		
		if(quantity<=0)
		{
			throw new IllegalArgumentException("minimum quantity 1");
		}
		OrderItem oi = opt.get();
		Order order = oi.getOrders();
		MenuItem menu = oi.getMenu();
		
		
		oi.setQuantity(quantity);
		
		double oldsubtotal =oi.getSubTotal(); 
		double newsubtotal = quantity * menu.getPrice();
		oi.setSubTotal(newsubtotal);
		orderItemRepo.save(oi);
		order.setTotalAmount(order.getTotalAmount() - oldsubtotal  + newsubtotal);
		orderRepo.save(order);
		
		res.setMessage("The quantity is updated");
		res.setStatusCode(HttpStatus.OK.value());
		res.setData(oi);
		return res;
		
		
	}
	
	// remove order item from order before out of deliver
	
	public ResponseStructure<String> deleteOrder(Integer orderItemId)
	{
		ResponseStructure<String> res = new ResponseStructure<String>();
		Optional<OrderItem> opt = orderItemRepo.findById(orderItemId);
		if(opt.isEmpty())
		{
			throw new IdNotFoundException("order itemid not available");
		}
		
		OrderItem oi = opt.get();
		
		Order order = oi.getOrders();
		if(order.getStatus() == Status.OutForDelivery || order.getStatus() == Status.Delivered)
		{
			throw new IllegalArgumentException("Order is already out of delivery");
		}
		
		order.setTotalAmount(order.getTotalAmount()-oi.getSubTotal());
		orderRepo.save(order);
		orderItemRepo.delete(oi);
		res.setMessage("Order itemis cancelled");
		res.setStatusCode(HttpStatus.OK.value());
		res.setData("Order item is removed");
		return res;
		
	}
	
	// get all the orderitems of an order
	
	public ResponseStructure<List<OrderItem>> getAllOrderItem(Integer orderId)
	{
		ResponseStructure<List<OrderItem>> res = new ResponseStructure<List<OrderItem>>();
		Optional<Order> opt = orderRepo.findById(orderId);
		if(opt.isEmpty())
		{
			throw new NoRecordAvailableException("No Record available by this id");
		}
		Order order = opt.get();
		List<OrderItem> olist = order.getOrderitemlist();
		if(olist.isEmpty())
		{
			throw new NoRecordAvailableException("No Items available by this id");
		}
		res.setStatusCode(HttpStatus.OK.value());
		res.setMessage("All the items asscoiated with order fetched");
		res.setData(olist);
		return res;
	}
}
