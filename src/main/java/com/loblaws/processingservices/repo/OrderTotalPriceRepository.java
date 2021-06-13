package com.loblaws.processingservices.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.loblaws.processingservices.model.order.TotalOrderPriceHelper;

public interface OrderTotalPriceRepository extends JpaRepository<TotalOrderPriceHelper, UUID>{	
	TotalOrderPriceHelper getOrderTotal(@Param("orderId") String orderId);	
}

