package com.loblaws.processingservices.service;

import java.util.List;

import com.loblaws.processingservices.model.common.ResponseModel;
import com.loblaws.processingservices.model.product.ProductModel;

public interface IProductService {
	public abstract ResponseModel addProduct(ProductModel model);
	public abstract List<ProductModel> getAllProducts();
	public abstract ProductModel getProductById(ProductModel productModel);
	public abstract ProductModel getProductByUPC(ProductModel productModel);
	public abstract ResponseModel updateProduct(ProductModel model);
}
