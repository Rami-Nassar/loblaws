package com.loblaws.processingservices.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loblaws.processingservices.model.order.OrderModel;

public interface OrderRepository extends JpaRepository<OrderModel, UUID> {

}
