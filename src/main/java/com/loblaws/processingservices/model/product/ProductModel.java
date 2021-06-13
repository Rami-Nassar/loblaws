package com.loblaws.processingservices.model.product;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Table(name = "product")
public class ProductModel {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @org.hibernate.annotations.Type(type = "uuid-char")
	@Column(name = "productId")
    private UUID productId;
	
	@Column(name = "productName")
	private String productName;
	
	@Column(name = "UPCNumber")
	private Integer upcNumber;
	
	@Column(name = "inventoryCount")
	private Integer inventoryCount;
	
	@Column(name = "price")
	private Double price;
}
