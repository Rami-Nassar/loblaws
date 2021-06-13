package com.loblaws.processingservices.model.orderxproduct;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.loblaws.processingservices.model.order.OrderModel;
import com.loblaws.processingservices.model.order.OrderModel.OrderModelBuilder;

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
@Table(name = "orderxproduct")
public class OrderXProductModel {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @org.hibernate.annotations.Type(type = "uuid-char")
	@Column(name = "orderXProductId")
    private UUID orderXProductId;
	
	@org.hibernate.annotations.Type(type = "uuid-char")
	@Column(name = "orderId")
    private UUID orderId;
	
	@org.hibernate.annotations.Type(type = "uuid-char")
	@Column(name = "productId")
    private UUID productId;
	
	@Column(name = "quantity")
	private Integer quantity;
}
