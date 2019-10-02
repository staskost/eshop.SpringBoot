package com.staskost.eshop.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.staskost.eshop.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{
	
	Product findByName(String name);
	
	List<Product> findByPrice(Double price);
	
	List<Product> findByPriceBetween(double priceMin, double priceMax);
	
	List<Product> findByNameContainingIgnoreCase(String name);
	
	List<Product> findByNameStartsWithIgnoreCase(String name);
	
	int countByCategory(String category);

}
