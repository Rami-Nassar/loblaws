package com.loblaws.processingservices.model.cashier;

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
@Table(name = "cashier")
public class CashierModel {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @org.hibernate.annotations.Type(type = "uuid-char")
	@Column(name = "cashierId")
    private UUID cashierId;
}
