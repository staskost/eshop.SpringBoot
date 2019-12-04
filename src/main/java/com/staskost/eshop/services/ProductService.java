package com.staskost.eshop.services;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import com.staskost.eshop.model.Product;

public interface ProductService {

	void saveProduct(Product product);

	public void updateProduct(int id, Product product);

	Product getById(int id);

	List<Product> getAllProducts();

	Product findByName(String name);

	List<Product> findByNameLike(String name);

	List<Product> findByNameStartsWith(String name);

	List<Product> findByCategoryAndPriceLessThan(String category, double price, PageRequest pageRequest);
	
	List<Product> findByCategoryAndPriceGreaterThan(String category, Double price, PageRequest pageRequest);

	List<Product> findByCategoryAndPriceBetween(String category, double minPrice, double maxPrice, PageRequest pageRequest);
	
	List<Product> findByCategory(String category, PageRequest pageRequest);
	
	int findCategoryProductsCount(String category);
	
	int findCategoryProductsCountWithPriceLessThan(String category, double price);
	
	int findCategoryProductsCountWithPriceGreaterThan(String category, double price);

	void setProductPrice(double price, int id);

	void removeItemfromProductCount(int items, int id);
	
	void addItemToProductCount(int items,int id);
	
	void removeProduct(int id);
	
	void addProduct(Product product);

}
