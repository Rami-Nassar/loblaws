package com.loblaws.processingservices.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.loblaws.processingservices.model.cashier.CashierModel;
import com.loblaws.processingservices.model.common.ResponseModel;
import com.loblaws.processingservices.model.product.ProductModel;
import com.loblaws.processingservices.repo.CashierRepository;
import com.loblaws.processingservices.service.ICashierService;

@Service
public class CashierServiceImpl implements ICashierService {
	
	@Autowired
	CashierRepository repo;

	@Override
	public ResponseModel addCashier(CashierModel model) {
		ResponseModel response = null;
		
		//Validate
		Optional<CashierModel> cashierModel = List.of(model).stream()							
				.findAny();
		
		if(cashierModel.isEmpty()) {
			response=ResponseModel.builder().Message("Validation Failed").MessageTypeID(0).StatusCode(HttpStatus.UNPROCESSABLE_ENTITY).build();
			
		}else {					
			
			repo.save(model);
			
			response=ResponseModel.builder().Message("Cashier Added Successfully").MessageTypeID(1).StatusCode(HttpStatus.OK).build();
		}
		
		if(response == null){response=ResponseModel.builder().Message("Not Found").MessageTypeID(1).StatusCode(HttpStatus.NOT_FOUND).build();}
		return response;
	}

	@Override
	public List<CashierModel> getAllCashiers() {
		return repo.findAll();
	}

	@Override
	public CashierModel getCashierById(CashierModel cashierModel) {
		Optional<CashierModel> responses =repo.findById(cashierModel.getCashierId());
		return responses.get();
	}

}
