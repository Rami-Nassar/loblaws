package com.loblaws.processingservices.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loblaws.processingservices.model.payment.PaymentModel;

public interface PaymentRepository extends JpaRepository<PaymentModel, UUID> {
	List<PaymentModel> findByOrderId(UUID orderId);
}
