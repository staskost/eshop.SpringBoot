package com.staskost.eshop.services;

import com.staskost.eshop.model.Cart;
import com.staskost.eshop.model.Product;
import com.staskost.eshop.model.User;

public interface CartService {
	
	Cart getById(int cartId);
	
	Cart createCart(int userId);
	
	Cart getUsersCart(int userId);
	
	void addProductToCart(int cartId, int productId);
	
	void removeProductFromCart(int cartId, int productId);
	
	void deleteCart(Cart cart);
	
	double getTotal(Cart cart);
	
//	void checkout(int userId, int cartId);

//	Cart returnCartOrException(int cartId);
	

}
