package com.staskost.eshop.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.staskost.eshop.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

	Product findByName(String name);

	List<Product> findByPrice(Double price);

	List<Product> findByPriceBetween(double priceMin, double priceMax);

	List<Product> findByNameContainingIgnoreCase(String name);

	List<Product> findByNameStartsWithIgnoreCase(String name);

	List<Product> findByCategory(String category);

	int countByCategory(String category);

}
