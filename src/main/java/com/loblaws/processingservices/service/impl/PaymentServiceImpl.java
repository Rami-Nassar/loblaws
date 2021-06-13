package com.loblaws.processingservices.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.loblaws.processingservices.model.common.ResponseModel;
import com.loblaws.processingservices.model.order.OrderModel;
import com.loblaws.processingservices.model.order.TotalOrderPriceHelper;
import com.loblaws.processingservices.model.payment.PaymentModel;
import com.loblaws.processingservices.repo.PaymentRepository;
import com.loblaws.processingservices.service.IPaymentService;

import lombok.Builder;

@Service
public class PaymentServiceImpl implements IPaymentService{
	
	@Autowired
	PaymentRepository repo;
	
	@Autowired
	OrderServiceImpl orderService;

	@Override
	public ResponseModel addPaymentToOrder(PaymentModel model) {
		ResponseModel response = null;
		
		//Validate
		Optional<PaymentModel> productModel = List.of(model).stream()							
				.findAny();
		
		if(productModel.isEmpty()) {
			response=ResponseModel.builder().Message("Validation Failed").MessageTypeID(0).StatusCode(HttpStatus.UNPROCESSABLE_ENTITY).build();
			
		}else {						
			OrderModel oModel = OrderModel.builder().orderId(model.getOrderId()).build();
			TotalOrderPriceHelper totalOrderPrice = orderService.getOrderTotal(oModel);
			
			Double totalAmount = totalOrderPrice.getTotal();
			Double amountPaid = model.getAmountPaid(); 
			
			List<PaymentModel> paymentList = repo.findByOrderId(model.getOrderId());
			Double previousPaid = 0.0;
			for(PaymentModel p : paymentList) {
				previousPaid += p.getAmountPaid();
			}
			
			Double totalPaid = previousPaid + amountPaid;
			
			if(totalPaid < totalAmount) { //Still owe money => NOT PIF
				PaymentModel pModel = PaymentModel.builder()
						.paymentType(model.getPaymentType())
						.amountPaid(amountPaid)
						.orderId(model.getOrderId())
						.completed(false)
						.build();
				
				repo.save(pModel);
			}else { //PIF 
				PaymentModel pModel = PaymentModel.builder()
						.paymentType(model.getPaymentType())
						.amountPaid(amountPaid)
						.orderId(model.getOrderId())
						.completed(true)
						.build();
				
				repo.save(pModel);
			}
			response=ResponseModel.builder().Message("Payment Added Successfully").MessageTypeID(1).StatusCode(HttpStatus.OK).build();
		}
		
		if(response == null){response=ResponseModel.builder().Message("Not Found").MessageTypeID(1).StatusCode(HttpStatus.NOT_FOUND).build();}
		return response;
	}

	@Override
	public List<PaymentModel> getPaymentsByOrderId(PaymentModel model) {
		return repo.findByOrderId(model.getOrderId());
	}

}
