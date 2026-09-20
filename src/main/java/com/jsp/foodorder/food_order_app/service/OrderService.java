package com.jsp.foodorder.food_order_app.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.jsp.foodorder.food_order_app.dto.OrderItemRequest;
import com.jsp.foodorder.food_order_app.dto.OrderRequest;
import com.jsp.foodorder.food_order_app.dto.ResponseStructure;
import com.jsp.foodorder.food_order_app.entity.Customer;
import com.jsp.foodorder.food_order_app.entity.MenuItem;
import com.jsp.foodorder.food_order_app.entity.Order;
import com.jsp.foodorder.food_order_app.entity.OrderItem;
import com.jsp.foodorder.food_order_app.entity.Payment;
import com.jsp.foodorder.food_order_app.entity.PaymentStatus;
import com.jsp.foodorder.food_order_app.entity.Status;
import com.jsp.foodorder.food_order_app.exception.IdNotFoundException;
import com.jsp.foodorder.food_order_app.exception.NoRecordAvailableException;
import com.jsp.foodorder.food_order_app.repository.CustomerRepository;
import com.jsp.foodorder.food_order_app.repository.MenuItemRepository;
import com.jsp.foodorder.food_order_app.repository.OrderRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderService {
	@Autowired
	private OrderRepository orderRepo;
	
	@Autowired
	private CustomerRepository custRepo;
	
	@Autowired
	private MenuItemRepository menuRepo;
	
	@Transactional
	public ResponseStructure<Order> placeOrder(OrderRequest request)
	{
		ResponseStructure<Order> res = new ResponseStructure<Order>();
		Optional<Customer> custopt = custRepo.findById(request.getCustomerId());
		Customer customer = custopt.get();
		
		Order order = new Order();
		order.setC(customer);
		order.setStatus(Status.OrderPlaced);
		
		List<OrderItem> orderitems = new ArrayList<>();
		double total=0;
		for(OrderItemRequest itemReq : request.getItems())
		{
			if(itemReq.getQuantity()<=0)
			{
				throw new IllegalArgumentException("Minimum Quantity 1");
			}
			
			Optional<MenuItem> menuopt = menuRepo.findById(itemReq.getMenuItemId());
			
			if(menuopt.isEmpty())
			{
				throw new IdNotFoundException("No Item in the menu by this id");
			}
			MenuItem menu = menuopt.get();
			
			if(!menu.isAvailability())
			{
				throw new IllegalArgumentException("this item not available");
			}
			
			double subtotal = menu.getPrice() * itemReq.getQuantity();
			
			OrderItem oi = new OrderItem();
			oi.setMenu(menu);
			oi.setSubTotal(subtotal);
			oi.setOrders(order);
			oi.setQuantity(itemReq.getQuantity());
			orderitems.add(oi);
			total = total+subtotal;
		}
		order.setOrderitemlist(orderitems);
		order.setTotalAmount(total);
		
		Payment payment = new Payment();
		payment.setPaymentMethod(request.getPaymentMethod());
		payment.setPaymentStatus(PaymentStatus.paid);
		payment.setAmount(total);
		payment.setOrder(order);
		order.setPay(payment);
	
		
		res.setMessage("Order Is Placed");
		res.setStatusCode(HttpStatus.OK.value());
		res.setData(orderRepo.save(order));
		return res;
	}
	
	// get all orders
	
	public ResponseStructure<List<Order>> getAllOrder()
	{
		ResponseStructure<List<Order>> res = new ResponseStructure<>();
		List<Order> olist = orderRepo.findAll();
		if(olist.isEmpty())
		{
			throw new NoRecordAvailableException("No Order are Placed");
		}
		res.setMessage("All the Order Have been Fetched");
		res.setStatusCode(HttpStatus.OK.value());
		res.setData(olist);
		return res;
	}
	
	// get all orders of a customer
	
	public ResponseStructure<List<Order>> getAllOrderOfCustomer(Customer customer_id)
	{
		ResponseStructure<List<Order>> res = new ResponseStructure<>();
		List<Order> olist = orderRepo.findOrderByC(customer_id);
		if(olist.isEmpty())
		{
			throw new NoRecordAvailableException("No Order are Placed By this customer");
		}
		res.setMessage("All the Order Have been Fetched");
		res.setStatusCode(HttpStatus.OK.value());
		res.setData(olist);
		return res;
	}
	
	// get by id
	
	public ResponseStructure<Order> getById(Integer id)
	{
		ResponseStructure<Order> res = new ResponseStructure<>();
		Optional<Order> opt = orderRepo.findById(id);
		if(opt.isEmpty())
		{
			throw new NoRecordAvailableException("No Order are Placed by this id");
		}
		res.setMessage("Order Have been Fetched");
		res.setStatusCode(HttpStatus.OK.value());
		res.setData(opt.get());
		return res;
	}
	
	// update order Status
	
	public ResponseStructure<Order> updateOrderByStatus(Integer id, Map<String , Object> data)
	{
		ResponseStructure<Order> res = new ResponseStructure<Order>();
		Optional<Order> opt =orderRepo.findById(id);
		
		if(opt.isPresent())
		{
			Order order = opt.get();
			for(Map.Entry<String, Object> entry : data.entrySet())
			{
				String key = entry.getKey();
				Object value = entry.getValue();
				
				switch(key)
				{
				case "status" : order.setStatus(Status.valueOf(value.toString()));
				break;
				}
			}
			
			res.setMessage("order status is updated");
			res.setStatusCode(HttpStatus.OK.value());
			res.setData(orderRepo.save(order));
			return res;
		}else {
			throw new IdNotFoundException("No Order By this id");
		}
		
	}
	
	// cancel order
	
	public ResponseStructure<Order> cancelOrder(Integer id)
	{
		ResponseStructure<Order> res = new ResponseStructure<>();
		Optional<Order> opt = orderRepo.findById(id);
		if(opt.isPresent())
		{
			Order order = opt.get();
			if(order.getStatus() != Status.PreparingFood && order.getStatus() != Status.OrderPlaced) {
				order.setStatus(Status.cancelled);
				res.setMessage("Order cancelled in the id"+id);
				res.setStatusCode(HttpStatus.OK.value());
				res.setData(orderRepo.save(order));
				return res;
			}else {
				throw new IllegalArgumentException("Your order is started to Prepare No way to cancel");
			}
			}
		else {
			throw new IdNotFoundException("No Order is there with this id "+id);
		}
	}
	
	// get all orders placed in a restuarent
	
	public ResponseStructure<List<Order>> getAllOrdersReasturant(Integer Restuarent_Id)
	{
		ResponseStructure<List<Order>> res = new ResponseStructure<>();
		List<Order> orders = orderRepo.findAllOrdersByRestaurant(Restuarent_Id);
		if(orders.isEmpty())
		{
			throw new NoRecordAvailableException("No Orders in this restuanrent");
		}
		res.setMessage("Order of this reaturent");
		res.setStatusCode(HttpStatus.OK.value());
		res.setData(orders);
		return res;
	}
	
	public ResponseStructure<List<Order>> getOrderByDate(LocalDate date)
	{
		ResponseStructure<List<Order>> res = new ResponseStructure<>();
		List<Order> orders = orderRepo.findOrderByDate(date);
		if(orders.isEmpty())
		{
			throw new NoRecordAvailableException("No Orders in this date");
		}
		res.setMessage("Order of this date fetched");
		res.setStatusCode(HttpStatus.OK.value());
		res.setData(orders);
		return res;
	}
	
	public ResponseStructure<List<Order>> getOrderByStatus(Status status)
	{
		ResponseStructure<List<Order>> res = new ResponseStructure<>();
		List<Order> orders = orderRepo.findOrderByStatus(status);
		if(orders.isEmpty())
		{
			throw new NoRecordAvailableException("No Orders in this Status");
		}
		res.setMessage("Orders  at this status fetched");
		res.setStatusCode(HttpStatus.OK.value());
		res.setData(orders);
		return res;
	}
}
