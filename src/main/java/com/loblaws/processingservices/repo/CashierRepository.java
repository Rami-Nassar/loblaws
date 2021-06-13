package com.loblaws.processingservices.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loblaws.processingservices.model.cashier.CashierModel;

public interface CashierRepository extends JpaRepository<CashierModel, UUID> {

}
