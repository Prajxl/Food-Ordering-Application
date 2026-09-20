package com.jsp.foodorder.food_order_app.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer itemId;
	private String itemName;
	private Double price;
	private boolean availability;
	
	
	@JsonIgnore
	@JoinColumn(name="rest_id")
	@ManyToOne
	private Restaurent rest;
	
	@JsonIgnore
	@OneToMany(mappedBy = "menu",cascade = CascadeType.ALL)
	private List<OrderItem> orderItemList;
	
}
