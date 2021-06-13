package com.loblaws.processingservices.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.loblaws.processingservices.model.order.OrderSummaryHelper;

public interface OrderSummaryQueryRepository extends JpaRepository<OrderSummaryHelper, UUID>{
	List<OrderSummaryHelper> getOrderSummary(@Param("orderId") String orderId);
}
