package com.loblaws.processingservices.model.product;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddProductModel {
	private Integer upcNumber;
	private Integer quantity;
	private UUID orderId;
}
