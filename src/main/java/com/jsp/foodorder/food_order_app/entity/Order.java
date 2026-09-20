package com.jsp.foodorder.food_order_app.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CurrentTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer orderId;
	@CurrentTimestamp
	private LocalDateTime orderDateTime;
	@Enumerated(EnumType.STRING)
	private Status status;
	private Double totalAmount;
	
	@JsonIgnore
	@JoinColumn(name="customer_id")
	@ManyToOne
	private Customer c;
	
	@OneToOne(mappedBy = "order",cascade = CascadeType.ALL)
	private Payment pay;
	
	@OneToMany(mappedBy ="orders",cascade = CascadeType.ALL)
	private List<OrderItem> orderitemlist;
}
