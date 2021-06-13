package com.loblaws.processingservices.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.loblaws.processingservices.model.common.ResponseModel;
import com.loblaws.processingservices.model.order.OrderModel;
import com.loblaws.processingservices.model.order.OrderSummaryHelper;
import com.loblaws.processingservices.model.order.TotalOrderPriceHelper;
import com.loblaws.processingservices.model.orderxproduct.OrderXProductModel;
import com.loblaws.processingservices.model.product.AddProductModel;
import com.loblaws.processingservices.model.product.ProductModel;
import com.loblaws.processingservices.repo.CashierRepository;
import com.loblaws.processingservices.repo.OrderRepository;
import com.loblaws.processingservices.repo.OrderSummaryQueryRepository;
import com.loblaws.processingservices.repo.OrderTotalPriceRepository;
import com.loblaws.processingservices.repo.OrderXProductRepository;
import com.loblaws.processingservices.repo.ProductRepository;
import com.loblaws.processingservices.service.IOrderService;

@Service
public class OrderServiceImpl implements IOrderService {
	
	@Autowired
	OrderRepository repo;
	
	@Autowired
	CashierRepository cashierRepo;
	
	@Autowired
	OrderXProductRepository orderXProductRepo;
	
	@Autowired
	ProductRepository productRepo;
	
	@Autowired
	OrderSummaryQueryRepository orderSummaryRepo;
	
	@Autowired
	OrderTotalPriceRepository orderTotalPriceRepo;
	
	@Autowired
	ProductServiceImpl productService;
	
	@Override
	public ResponseModel addOrder(OrderModel model) {
		ResponseModel response = null;
		
		//Validate
		Optional<OrderModel> orderModel = List.of(model).stream()
				.filter(cm ->model.getCashierId() != null && cashierRepo.findById(model.getCashierId()).isPresent())			// Check that order has a valid cashierId that exists
				.findAny();
		
		if(orderModel.isEmpty()) {
			response=ResponseModel.builder().Message("Validation Failed").MessageTypeID(0).StatusCode(HttpStatus.UNPROCESSABLE_ENTITY).build();
			
		}else {					
			
			OrderModel oModel = OrderModel.builder()
					.cashierId(model.getCashierId())
					.completed(false)
					.build();
			
			repo.save(oModel);
			
			response=ResponseModel.builder().Message("Order Added Successfully").MessageTypeID(1).StatusCode(HttpStatus.OK).build();
		}
		
		if(response == null){response=ResponseModel.builder().Message("Not Found").MessageTypeID(1).StatusCode(HttpStatus.NOT_FOUND).build();}
		return response;
	}

	@Override
	public List<OrderModel> getAllOrders() {
		return repo.findAll();
	}

	@Override
	public OrderModel getOrderById(OrderModel orderModel) {
		Optional<OrderModel> responses =repo.findById(orderModel.getOrderId());
		return responses.get();
	}

	@Override
	public ResponseModel addProductToOrder(AddProductModel addProductModel) {
		ResponseModel response = null;
		
		//Validate
		Optional<AddProductModel> addProductM = List.of(addProductModel).stream()
				.filter(cm -> addProductModel.getUpcNumber() != null && productRepo.findByUpcNumber(addProductModel.getUpcNumber()).isPresent())		// Check that valid UPC for product is provided
				.filter(cm -> addProductModel.getQuantity() != null && addProductModel.getQuantity() > 0)												// Check that quantity is provided
				.filter(cm -> addProductModel.getOrderId() != null && repo.findById(addProductModel.getOrderId()).isPresent() )							// Check that valid OrderId is provided
				.findAny();
		
		if(addProductM.isEmpty()) {
			response=ResponseModel.builder().Message("Validation Failed").MessageTypeID(0).StatusCode(HttpStatus.UNPROCESSABLE_ENTITY).build();
			
		}else {					
			
			ProductModel pModel = productRepo.findByUpcNumber(addProductModel.getUpcNumber()).get();			
			int inventory = pModel.getInventoryCount();
			int quantity = addProductModel.getQuantity();
			
			if(quantity <= inventory) {	//Quantity can't exceed inventory
			
				OrderXProductModel oXPModel = OrderXProductModel.builder()
						.orderId(addProductModel.getOrderId())
						.quantity(quantity)
						.productId(pModel.getProductId())
						.build();			
				
				
				orderXProductRepo.save(oXPModel);
				
				//Need to update Inventory Count				
				pModel.setInventoryCount(inventory - quantity);
				productService.updateProduct(pModel);
				
				
				response=ResponseModel.builder().Message("Product Added to Order Successfully").MessageTypeID(1).StatusCode(HttpStatus.OK).build();
			}else {
				response=ResponseModel.builder().Message("Quantity Exceed Inventory").MessageTypeID(0).StatusCode(HttpStatus.UNPROCESSABLE_ENTITY).build();
			}
			
			
		}
		
		if(response == null){response=ResponseModel.builder().Message("Not Found").MessageTypeID(1).StatusCode(HttpStatus.NOT_FOUND).build();}
		return response;
	}

	@Override
	public List<OrderSummaryHelper> getOrderProductSummary(OrderModel model) {
		return orderSummaryRepo.getOrderSummary(model.getOrderId().toString());
	}

	@Override
	public TotalOrderPriceHelper getOrderTotal(OrderModel model) {
		TotalOrderPriceHelper totalModel = orderTotalPriceRepo.getOrderTotal(model.getOrderId().toString());	
		
		double TotalAfterTaxRounded = Math.round(totalModel.getTotal()*100)/100;

		
		TotalOrderPriceHelper totalPriceAfterTax = new TotalOrderPriceHelper();
		totalPriceAfterTax.setOrderId(model.getOrderId());
		totalPriceAfterTax.setTotal(TotalAfterTaxRounded);
		
		return totalPriceAfterTax;
	}

}
