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
import com.loblaws.processingservices.model.cashier.CashierModel;
import com.loblaws.processingservices.model.common.ResponseModel;
import com.loblaws.processingservices.service.impl.CashierServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/api/cashier")	
@Api(value = "Processing Service")
@Slf4j
public class CashierController {
	
	@Autowired
	private Gson gson;
	
	@Autowired
	private CashierServiceImpl service;

	@ApiOperation(value = "Add Cashier", response = ResponseModel.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = Constants.STORE_SUCCESS_MSG) })
	//@PreAuthorize("hasRole('create:cashier')")
	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/vnd.api+json")
	public ResponseEntity<ResponseModel> AddCashier(@RequestBody CashierModel cashierModel) {
		ResponseModel msgstart = ResponseModel.builder()
				.Message("Start Add Cashier")
				.MessageTypeID(2).build();
		
		log.info(gson.toJson(msgstart));		

		ResponseModel response = service.addCashier(cashierModel);
		HttpStatus status = response != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;

		ResponseModel msgend = ResponseModel.builder()
				.Message("End Add Cashier")
				.MessageTypeID(2)
				.build();
		
		log.info(gson.toJson(msgend));

		ResponseEntity<ResponseModel> output = new ResponseEntity<ResponseModel>(response, status);
		return output;
	}
	
	//Get all Cashiers
	@RequestMapping(value="/getAllCashiers",method = RequestMethod.GET, produces = "application/vnd.api+json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = Constants.STORE_SUCCESS_MSG)})
	//@PreAuthorize("hasRole('view:cashier')")
	public ResponseEntity<List<CashierModel>> getAllCashiers(){
		ResponseModel msgstart= ResponseModel.builder()
				.Message("Start Get All Cashiers")
				.MessageTypeID(2)
				.build();
		log.info(gson.toJson(msgstart));
		
		List<CashierModel> response = service.getAllCashiers();
		HttpStatus status = response != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		
		ResponseModel msgend= ResponseModel.builder()
				.Message("End Get All Cashiers")
				.MessageTypeID(2)
				.build();
		log.info(gson.toJson(msgend));		
		
		ResponseEntity<List<CashierModel>> output = new ResponseEntity<List<CashierModel>>(response, status);		
		return output;		
	}
	
	@RequestMapping(value="/getCashierById/{cashierId}",method = RequestMethod.GET,  produces = "application/vnd.api+json")
	public ResponseEntity<CashierModel> getCashierById(@PathVariable UUID cashierId){
		
		CashierModel model = CashierModel.builder().cashierId(cashierId).build();	
		
		ResponseModel msgstart= ResponseModel.builder()
				.Message("Start Get Cashier by Id")
				.MessageTypeID(2)
				.Message(model.toString())
				.build();
		log.info(gson.toJson(msgstart));
		
		CashierModel response = service.getCashierById(model);
		HttpStatus status = response != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		
		ResponseModel msgend= ResponseModel.builder()
				.Message("End Get Cashier by Id")
				.MessageTypeID(2)
				.build();
		log.info(gson.toJson(msgend));		
		
		ResponseEntity<CashierModel> output = new ResponseEntity<CashierModel>(response, status);		
		return output;		
    }
}
