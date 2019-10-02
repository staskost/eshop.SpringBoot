package com.staskost.eshop.services;

import java.util.List;

import com.staskost.eshop.model.Product;

public interface ProductService {

	void createProduct(Product product);

	public void updateProduct(int id, Product product);

	public void deleteProduct(int id);

	Product getById(int id);

	List<Product> getAllProducts();

	Product findByName(String name);

	List<Product> findByNameLike(String name);

	List<Product> findByNameStartsWith(String name);

	List<Product> findByPrice(double price);

	List<Product> findByPriceBetween(double minPrice, double maxPrice);

	void setProductPrice(double price, int id);

	void removeItemfromProductCount(int items, int id);
	
	void addItemfromProductCount(int items,int id);
	
	void removeProduct(int id);
	
	void addProduct(Product product);

}
