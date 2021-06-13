package com.loblaws.processingservices.service;

import java.util.List;

import com.loblaws.processingservices.model.cashier.CashierModel;
import com.loblaws.processingservices.model.common.ResponseModel;

public interface ICashierService {
	public abstract ResponseModel addCashier(CashierModel model);
	public abstract List<CashierModel> getAllCashiers();
	public abstract CashierModel getCashierById(CashierModel cashierModel);
}
