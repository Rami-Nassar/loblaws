package com.loblaws.processingservices.repo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loblaws.processingservices.model.product.ProductModel;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, UUID> {
	Optional<ProductModel> findByUpcNumber(Integer upcNumber);
}
