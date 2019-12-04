package com.staskost.eshop.repos;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.staskost.eshop.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

	Product findByName(String name);

	List<Product> findByCategoryAndPriceLessThan(String category, Double price, Pageable pageable);
	
	List<Product> findByCategoryAndPriceGreaterThan(String category, Double price, Pageable pageable);

	List<Product> findByNameContainingIgnoreCase(String name);

	List<Product> findByNameStartsWithIgnoreCase(String name);

	List<Product> findByCategory(String category, Pageable pageable);

	int countByCategory(String category);
	
	int countByCategoryAndPriceLessThan(String category, Double price);
	
	int countByCategoryAndPriceGreaterThan(String category, Double price);

	List<Product> findByCategoryAndPriceBetween(String category, double minPrice, double maxPrice,
			 Pageable pageable);

}
