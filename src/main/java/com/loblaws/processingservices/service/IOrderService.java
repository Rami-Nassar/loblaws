package com.loblaws.processingservices.service;

import java.util.List;

import com.loblaws.processingservices.model.common.ResponseModel;
import com.loblaws.processingservices.model.order.OrderModel;
import com.loblaws.processingservices.model.order.OrderSummaryHelper;
import com.loblaws.processingservices.model.order.TotalOrderPriceHelper;
import com.loblaws.processingservices.model.product.AddProductModel;

public interface IOrderService {
	public abstract ResponseModel addOrder(OrderModel model);
	public abstract List<OrderModel> getAllOrders();
	public abstract OrderModel getOrderById(OrderModel orderModel);
	public abstract ResponseModel addProductToOrder(AddProductModel addProductModel);
	public abstract List<OrderSummaryHelper> getOrderProductSummary(OrderModel model);
	public abstract TotalOrderPriceHelper getOrderTotal(OrderModel model);
}
