package com.loblaws.processingservices.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loblaws.processingservices.model.orderxproduct.OrderXProductModel;

public interface OrderXProductRepository extends JpaRepository<OrderXProductModel, UUID> {

}
