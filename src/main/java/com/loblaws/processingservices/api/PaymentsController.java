package com.loblaws.processingservices.api;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.loblaws.processingservices.common.Constants;
import com.loblaws.processingservices.model.common.ResponseModel;
import com.loblaws.processingservices.model.payment.PaymentModel;
import com.loblaws.processingservices.model.product.ProductModel;
import com.loblaws.processingservices.service.impl.PaymentServiceImpl;
import com.loblaws.processingservices.service.impl.ProductServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/api/payments")	
@Api(value = "Processing Service")
@Slf4j
public class PaymentsController {
	
	@Autowired
	private Gson gson;
	
	@Autowired
	private PaymentServiceImpl service;
	
	@RequestMapping(value = "/addPaymentToOrder", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/vnd.api+json")
	public ResponseEntity<ResponseModel> AddPaymentToOrder(@RequestBody PaymentModel paymentModel) {
		ResponseModel msgstart = ResponseModel.builder()
				.Message("Start Add Payment To Order")
				.MessageTypeID(2).build();
		
		log.info(gson.toJson(msgstart));		

		ResponseModel response = service.addPaymentToOrder(paymentModel);
		HttpStatus status = response != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;

		ResponseModel msgend = ResponseModel.builder()
				.Message("End Add Payment To ORder")
				.MessageTypeID(2)
				.build();
		
		log.info(gson.toJson(msgend));

		ResponseEntity<ResponseModel> output = new ResponseEntity<ResponseModel>(response, status);
		return output;
	}
	
	@RequestMapping(value="/getPaymentByOrderId/{orderId}",method = RequestMethod.GET,  produces = "application/vnd.api+json")
	public ResponseEntity<List<PaymentModel>> getPaymentsByOrderId(@PathVariable UUID orderId){
		
		PaymentModel model = PaymentModel.builder().orderId(orderId).build();	
		
		ResponseModel msgstart= ResponseModel.builder()
				.Message("Start Get Product by Id")
				.MessageTypeID(2)
				.Message(model.toString())
				.build();
		log.info(gson.toJson(msgstart));
		
		List<PaymentModel> response = service.getPaymentsByOrderId(model);
		HttpStatus status = response != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		
		ResponseModel msgend= ResponseModel.builder()
				.Message("End Get Connector by Id")
				.MessageTypeID(2)
				.build();
		log.info(gson.toJson(msgend));		
		
		ResponseEntity<List<PaymentModel>> output = new ResponseEntity<List<PaymentModel>>(response, status);		
		return output;		
    }
}
