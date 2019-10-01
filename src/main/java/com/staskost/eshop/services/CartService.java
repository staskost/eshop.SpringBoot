package com.staskost.eshop.services;

import com.staskost.eshop.model.Cart;
import com.staskost.eshop.model.Product;
import com.staskost.eshop.model.User;

public interface CartService {
	
	Cart createCart(User user);
	
	Cart getUsersCart(User user);
	
	void addProduct(Cart cart, Product product);
	
	void removeProduct(Cart cart, Product product);
	
	void deleteCart(Cart cart);
	
	double getTotal(Cart cart);
	
	void checkout(User user, Cart cart);
	

}
