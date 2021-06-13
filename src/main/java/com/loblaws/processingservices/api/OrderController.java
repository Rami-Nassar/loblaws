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
import com.loblaws.processingservices.model.order.OrderModel;
import com.loblaws.processingservices.model.order.OrderSummaryHelper;
import com.loblaws.processingservices.model.order.TotalOrderPriceHelper;
import com.loblaws.processingservices.model.product.AddProductModel;
import com.loblaws.processingservices.service.impl.OrderServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/api/order")	
@Api(value = "Processing Service")
@Slf4j
public class OrderController {

	@Autowired
	private Gson gson;
	
	@Autowired
	private OrderServiceImpl service;
	
	@ApiOperation(value = "Add Order", response = ResponseModel.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = Constants.STORE_SUCCESS_MSG) })
	//@PreAuthorize("hasRole('create:order')")
	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/vnd.api+json")
	public ResponseEntity<ResponseModel> AddOrder(@RequestBody OrderModel orderModel) {
		ResponseModel msgstart = ResponseModel.builder()
				.Message("Start Add Order")
				.MessageTypeID(2).build();
		
		log.info(gson.toJson(msgstart));		

		ResponseModel response = service.addOrder(orderModel);
		HttpStatus status = response != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;

		ResponseModel msgend = ResponseModel.builder()
				.Message("End Add Order")
				.MessageTypeID(2)
				.build();
		
		log.info(gson.toJson(msgend));

		ResponseEntity<ResponseModel> output = new ResponseEntity<ResponseModel>(response, status);
		return output;
	}
	
	//Get all Orders
	@RequestMapping(value="/getAllOrders",method = RequestMethod.GET, produces = "application/vnd.api+json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = Constants.STORE_SUCCESS_MSG)})
	//@PreAuthorize("hasRole('view:orders')")
	public ResponseEntity<List<OrderModel>> getAllORders(){
		ResponseModel msgstart= ResponseModel.builder()
				.Message("Start Get All Orders")
				.MessageTypeID(2)
				.build();
		log.info(gson.toJson(msgstart));
		
		List<OrderModel> response = service.getAllOrders();
		HttpStatus status = response != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		
		ResponseModel msgend= ResponseModel.builder()
				.Message("End Get All Orders")
				.MessageTypeID(2)
				.build();
		log.info(gson.toJson(msgend));		
		
		ResponseEntity<List<OrderModel>> output = new ResponseEntity<List<OrderModel>>(response, status);		
		return output;		
	}
	
	@RequestMapping(value="/getOrderById/{orderId}",method = RequestMethod.GET,  produces = "application/vnd.api+json")
	public ResponseEntity<OrderModel> getOrderById(@PathVariable UUID orderId){
		
		OrderModel model = OrderModel.builder().orderId(orderId).build();	
		
		ResponseModel msgstart= ResponseModel.builder()
				.Message("Start Get Order by Id")
				.MessageTypeID(2)
				.Message(model.toString())
				.build();
		log.info(gson.toJson(msgstart));
		
		OrderModel response = service.getOrderById(model);
		HttpStatus status = response != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		
		ResponseModel msgend= ResponseModel.builder()
				.Message("End Get Order by Id")
				.MessageTypeID(2)
				.build();
		log.info(gson.toJson(msgend));		
		
		ResponseEntity<OrderModel> output = new ResponseEntity<OrderModel>(response, status);		
		return output;		
    }	

	@RequestMapping(value = "/addProductToOrder", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/vnd.api+json")
	public ResponseEntity<ResponseModel> AddProductToOrder(@RequestBody AddProductModel addProductModel) {
		ResponseModel msgstart = ResponseModel.builder()
				.Message("Start Add Product To Order")
				.MessageTypeID(2).build();
		
		log.info(gson.toJson(msgstart));		

		ResponseModel response = service.addProductToOrder(addProductModel);
		HttpStatus status = response != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;

		ResponseModel msgend = ResponseModel.builder()
				.Message("End Add Product To Order")
				.MessageTypeID(2)
				.build();
		
		log.info(gson.toJson(msgend));

		ResponseEntity<ResponseModel> output = new ResponseEntity<ResponseModel>(response, status);
		return output;
	}
	
	
	@RequestMapping(value="/getOrderProductSummary",method = RequestMethod.GET, produces = "application/vnd.api+json")	
	public ResponseEntity<List<OrderSummaryHelper>> getOrderProductSummary(@RequestBody OrderModel orderModel){
		ResponseModel msgstart= ResponseModel.builder()
				.Message("Start Get Order Product Summary")
				.MessageTypeID(2)
				.build();
		log.info(gson.toJson(msgstart));
		
		List<OrderSummaryHelper> response = service.getOrderProductSummary(orderModel);
		HttpStatus status = response != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		
		ResponseModel msgend= ResponseModel.builder()
				.Message("End Get Order Product Summary")
				.MessageTypeID(2)
				.build();
		log.info(gson.toJson(msgend));		
		
		ResponseEntity<List<OrderSummaryHelper>> output = new ResponseEntity<List<OrderSummaryHelper>>(response, status);		
		return output;		
	}
	
	@RequestMapping(value="/getTotalOrderPrice",method = RequestMethod.GET, produces = "application/vnd.api+json")	
	public ResponseEntity<TotalOrderPriceHelper> getTotalOrderPrice(@RequestBody OrderModel orderModel){
		ResponseModel msgstart= ResponseModel.builder()
				.Message("Start Get Total Order Price")
				.MessageTypeID(2)
				.build();
		log.info(gson.toJson(msgstart));
		
		TotalOrderPriceHelper response = service.getOrderTotal(orderModel);
		HttpStatus status = response != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		
		ResponseModel msgend= ResponseModel.builder()
				.Message("End Get Total Order Price")
				.MessageTypeID(2)
				.build();
		log.info(gson.toJson(msgend));		
		
		ResponseEntity<TotalOrderPriceHelper> output = new ResponseEntity<TotalOrderPriceHelper>(response, status);		
		return output;		
	}
	
	
}
