package com.loblaws.processingservices.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.loblaws.processingservices.model.common.ResponseModel;
import com.loblaws.processingservices.model.product.ProductModel;
import com.loblaws.processingservices.repo.ProductRepository;
import com.loblaws.processingservices.service.IProductService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductServiceImpl implements IProductService {
	
	@Autowired
	ProductRepository repo;
	
	@Override
	public ResponseModel addProduct(ProductModel model) {
		ResponseModel response = null;
		
		//Validate
		Optional<ProductModel> productModel = List.of(model).stream()
				.filter(cm ->model.getPrice() != null && model.getPrice() >= 0.0 )		// Check that product has a non-negative price	
				.filter(cm ->model.getUpcNumber() != null)								// Check that product has a UPC Number			
				.findAny();
		
		if(productModel.isEmpty()) {
			response=ResponseModel.builder().Message("Validation Failed").MessageTypeID(0).StatusCode(HttpStatus.UNPROCESSABLE_ENTITY).build();
			
		}else {					
			
			repo.save(model);
			
			response=ResponseModel.builder().Message("Product Added Successfully").MessageTypeID(1).StatusCode(HttpStatus.OK).build();
		}
		
		if(response == null){response=ResponseModel.builder().Message("Not Found").MessageTypeID(1).StatusCode(HttpStatus.NOT_FOUND).build();}
		return response;
	}

	@Override
	public List<ProductModel> getAllProducts() {		
		return repo.findAll();
	}

	@Override
	public ProductModel getProductById(ProductModel productModel) {
		Optional<ProductModel> responses =repo.findById(productModel.getProductId());
		return responses.get();
	}

	@Override
	public ProductModel getProductByUPC(ProductModel productModel) {
		Optional<ProductModel> responses =repo.findByUpcNumber(productModel.getUpcNumber());
		return responses.get();
	}

	@Override
	public ResponseModel updateProduct(ProductModel model) {
		ResponseModel response = null;
		
		//Validate
		Optional<ProductModel> productModel = List.of(model).stream()	
				.filter(cm ->model.getProductId() != null && repo.findById(model.getProductId()).isPresent() )	// Check valid id provided exists in DB
				.findAny();
		
		if(productModel.isEmpty()) {
			response=ResponseModel.builder().Message("Validation Failed").MessageTypeID(0).StatusCode(HttpStatus.UNPROCESSABLE_ENTITY).build();
		}else {		
			repo.save(model);
			response=ResponseModel.builder().Message("Product Updated Successfully").MessageTypeID(1).StatusCode(HttpStatus.OK).build();
		}
		
		if(response == null){response=ResponseModel.builder().Message("Not Found").MessageTypeID(1).StatusCode(HttpStatus.NOT_FOUND).build();}
		return response;	
	}

}
