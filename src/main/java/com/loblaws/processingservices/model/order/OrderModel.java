package com.loblaws.processingservices.model.order;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.loblaws.processingservices.model.product.ProductModel;
import com.loblaws.processingservices.model.product.ProductModel.ProductModelBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class OrderModel {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @org.hibernate.annotations.Type(type = "uuid-char")
	@Column(name = "orderId")
    private UUID orderId;
	
	@org.hibernate.annotations.Type(type = "uuid-char")
	@Column(name = "cashierId")
	private UUID cashierId;
	
	@Column(name = "completed")
	private Boolean completed;
	
}
