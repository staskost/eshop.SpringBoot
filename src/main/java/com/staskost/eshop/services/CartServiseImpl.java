package com.staskost.eshop.services;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.staskost.eshop.model.Cart;
import com.staskost.eshop.model.Product;
import com.staskost.eshop.model.User;
import com.staskost.eshop.repos.CartRepository;

@Service
public class CartServiseImpl implements CartService {

	private CartRepository cartRepository;

	private UserService userService;

	private ProductService productService;

	public CartServiseImpl(CartRepository cartRepository, UserService userService, ProductService productService) {
		this.cartRepository = cartRepository;
		this.userService = userService;
		this.productService = productService;
	}

	private Cart returnCartOrNull(int id) {
		Optional<Cart> opt = cartRepository.findById(id);
		if (opt.isPresent()) {
			Cart cart = opt.get();
			return cart;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart Not Found");
		}
	}

	public Cart createCart(int userId) {
		User user = userService.getById(userId);
		Cart cart = new Cart();
		cart.setUser(user);
		cartRepository.save(cart);
		return cart;
	}

	public Cart getUsersCart(int userId) {
		User user = userService.getById(userId);
		Cart cart = cartRepository.findByUser(user);
		return cart;
	}

	public void addProductToCart(int cartId, int productId) {
		Product product = productService.getById(productId);
		Cart cart = returnCartOrNull(cartId);
		cart.addProcuct(product);
		cartRepository.save(cart);
	}

	public void removeProductFromCart(int cartId, int productId) {
		Product product = productService.getById(productId);
		Cart cart = returnCartOrNull(cartId);
		cart.addProcuct(product);
		cart.removeProduct(product);
		cartRepository.save(cart);
	}

	public void deleteCart(Cart cart) {
		if (cart != null) {
			cartRepository.delete(cart);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart Not Found");
		}
	}

	public double getTotal(Cart cart) {
		List<Product> products = cart.getCartProducts();
		double sum = 0.0;
		for (Product p : products) {
			sum += p.getPrice();
		}
		return sum;
	}

	public void checkout(int userId, int cartId) {
		User user = userService.getById(userId);
		Cart cart = returnCartOrNull(cartId);
		double total = getTotal(cart);
		double totalAfterDiscount = userService.getTotalAfterDiscount(total, user);
		userService.withdraw(totalAfterDiscount);
		userService.givePointsToLoyal(totalAfterDiscount, user);
		deleteCart(cart);
		userService.withdraw(totalAfterDiscount);
		List<Product> products = cart.getCartProducts();
		int count = 0;
		for (Product p : products) {
			count = p.getProductCount();
			p.setProductCount(count - 1);
			if (count >= 0) {
				p.setIsAvailabe(0);
			}
			productService.saveProduct(p);
		}
	}

}
