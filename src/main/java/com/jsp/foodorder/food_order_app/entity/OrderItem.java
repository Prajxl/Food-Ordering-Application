package com.jsp.foodorder.food_order_app.entity;



import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer orderItemId;
	private int quantity;
	private Double subTotal;
	
	@JoinColumn(name="menu_item_id")
	@ManyToOne
	private MenuItem menu;
	
	@JsonIgnore
	@JoinColumn(name="order_id")
	@ManyToOne
	private Order orders;
}
