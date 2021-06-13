package com.loblaws.processingservices.service;

import java.util.List;

import com.loblaws.processingservices.model.common.ResponseModel;
import com.loblaws.processingservices.model.payment.PaymentModel;
import com.loblaws.processingservices.model.product.ProductModel;

public interface IPaymentService {
	public abstract ResponseModel addPaymentToOrder(PaymentModel model);
	public abstract List<PaymentModel> getPaymentsByOrderId(PaymentModel model);
}
