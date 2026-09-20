package com.jsp.foodorder.food_order_app.dto;

public class OrderItemRequest {
	private Integer menuItemId;
	private Integer quantity;
	public Integer getMenuItemId() {
		return menuItemId;
	}
	public void setMenuItemId(Integer menuItemId) {
		this.menuItemId = menuItemId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantiity(Integer quantiity) {
		this.quantity = quantiity;
	}
	

}
