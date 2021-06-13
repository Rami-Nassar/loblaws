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
import com.loblaws.processingservices.model.product.ProductModel;
import com.loblaws.processingservices.service.impl.ProductServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/api/product")	
@Api(value = "Processing Service")
@Slf4j
public class ProductController {
	
	@Autowired
	private Gson gson;
	
	@Autowired
	private ProductServiceImpl service;
	
	@ApiOperation(value = "Add Product", response = ResponseModel.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = Constants.STORE_SUCCESS_MSG) })
	//@PreAuthorize("hasRole('create:product')")
	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/vnd.api+json")
	public ResponseEntity<ResponseModel> AddProduct(@RequestBody ProductModel productModel) {
		ResponseModel msgstart = ResponseModel.builder()
				.Message("Start Add Product")
				.MessageTypeID(2).build();
		
		log.info(gson.toJson(msgstart));		

		ResponseModel response = service.addProduct(productModel);
		HttpStatus status = response != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;

		ResponseModel msgend = ResponseModel.builder()
				.Message("End Add Product")
				.MessageTypeID(2)
				.build();
		
		log.info(gson.toJson(msgend));

		ResponseEntity<ResponseModel> output = new ResponseEntity<ResponseModel>(response, status);
		return output;
	}
	
	//Get all Products
	@RequestMapping(value="/getAllProducts",method = RequestMethod.GET, produces = "application/vnd.api+json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = Constants.STORE_SUCCESS_MSG)})
	//@PreAuthorize("hasRole('view:products')")
	public ResponseEntity<List<ProductModel>> getAllProducts(){
		ResponseModel msgstart= ResponseModel.builder()
				.Message("Start Get All Products")
				.MessageTypeID(2)
				.build();
		log.info(gson.toJson(msgstart));
		
		List<ProductModel> response = service.getAllProducts();
		HttpStatus status = response != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		
		ResponseModel msgend= ResponseModel.builder()
				.Message("End Get All Products")
				.MessageTypeID(2)
				.build();
		log.info(gson.toJson(msgend));		
		
		ResponseEntity<List<ProductModel>> output = new ResponseEntity<List<ProductModel>>(response, status);		
		return output;		
	}
	
	@RequestMapping(value="/getProductById/{productId}",method = RequestMethod.GET,  produces = "application/vnd.api+json")
	public ResponseEntity<ProductModel> getProductById(@PathVariable UUID productId){
		
		ProductModel model = ProductModel.builder().productId(productId).build();	
		
		ResponseModel msgstart= ResponseModel.builder()
				.Message("Start Get Product by Id")
				.MessageTypeID(2)
				.Message(model.toString())
				.build();
		log.info(gson.toJson(msgstart));
		
		ProductModel response = service.getProductById(model);
		HttpStatus status = response != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		
		ResponseModel msgend= ResponseModel.builder()
				.Message("End Get Connector by Id")
				.MessageTypeID(2)
				.build();
		log.info(gson.toJson(msgend));		
		
		ResponseEntity<ProductModel> output = new ResponseEntity<ProductModel>(response, status);		
		return output;		
    }
	
	@RequestMapping(value="/getProductByUPC/{UPCNumber}",method = RequestMethod.GET,  produces = "application/vnd.api+json")
	public ResponseEntity<ProductModel> getProductByUPC(@PathVariable Integer UPCNumber){
		
		ProductModel model = ProductModel.builder().upcNumber(UPCNumber).build();	
		
		ResponseModel msgstart= ResponseModel.builder()
				.Message("Start Get Product by UPC")
				.MessageTypeID(2)
				.Message(model.toString())
				.build();
		log.info(gson.toJson(msgstart));
		
		ProductModel response = service.getProductByUPC(model);
		HttpStatus status = response != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		
		ResponseModel msgend= ResponseModel.builder()
				.Message("End Get Connector by UPC")
				.MessageTypeID(2)
				.build();
		log.info(gson.toJson(msgend));		
		
		ResponseEntity<ProductModel> output = new ResponseEntity<ProductModel>(response, status);		
		return output;		
    }
	
	@ApiOperation(value = "Update Product", response = ResponseModel.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = Constants.UPDATE_JOB_SUCCESS_MSG)})	
	//@PreAuthorize("hasRole('update:product')")
	@RequestMapping(value="/updateProduct",method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/vnd.api+json")
	public ResponseEntity<ResponseModel> UpdateProduct(@RequestBody ProductModel productModel){
		ResponseModel msgstart= ResponseModel.builder()
				.Message("Start Update Product")
				.MessageTypeID(2)
				.build();
		log.info(gson.toJson(msgstart));		
			
		ResponseModel response = service.updateProduct(productModel);
		HttpStatus status = response != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		
		ResponseModel msgend= ResponseModel.builder()
				.Message("End Update Prouct")
				.MessageTypeID(2)
				.build();
		log.info(gson.toJson(msgend));		
		
		ResponseEntity<ResponseModel> output = new ResponseEntity<ResponseModel>(response, status);		
		return output;	
	}
}
