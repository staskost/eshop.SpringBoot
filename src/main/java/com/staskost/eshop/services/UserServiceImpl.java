package com.staskost.eshop.services;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.staskost.eshop.model.Cart;
import com.staskost.eshop.model.Product;
import com.staskost.eshop.model.User;
import com.staskost.eshop.repos.UserRepository;

import io.jsonwebtoken.Jwts;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;

	private CartService cartService;

	private ProductService productService;

	public UserServiceImpl(UserRepository userRepository, CartService cartService, ProductService productService) {
		this.userRepository = userRepository;
		this.cartService = cartService;
		this.productService = productService;
	}

	private User returnUserOrException(int id) {
		Optional<User> opt = userRepository.findById(id);
		if (opt.isPresent()) {
			User user = opt.get();
			return user;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found");
		}
	}

	public void save(User user) {
		userRepository.save(user);
	}

	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public User findByEmailAndPassword(String email, String password) {
		return userRepository.findByEmailAndPassword(email, password);
	}

	public User getById(int id) {
		User user = returnUserOrException(id);
		return user;
	}

	public List<User> getAll() {
		List<User> users = userRepository.findAll();
		return users;
	}

	public void givePointsToLoyal(double total, User user) {
		if (user.getIsLoyal() == 1) {
			int roundedTotal = (int) Math.round(total);
			if (roundedTotal > 700) {
				user.setPoints(user.getPoints() + 70);
			} else {
				user.setPoints(user.getPoints() + (roundedTotal / 10));
			}
		} else {
			user.setPoints(user.getPoints());
		}
	}

	private double calculatePercentage(double obtained, double total) {
		return obtained * 100 / total;
	}

	public double getTotalAfterDiscount(double total, User user) {
		double price = 0;
		double pc = 0;
		if (user.getIsLoyal() == 1) {
			int points = user.getPoints();
			if ((points > 0) & (points < 700)) {
				pc = calculatePercentage(points / 10, total);
				price = total - pc;
				user.setPoints(0);
			} else {
				pc = calculatePercentage(70, total);
				price = total - pc;
				user.setPoints(0);
			}
		} else {
			price = total;
		}
		return price;
	}

	public void withdraw(double total) {
		// card transaction here
		System.out.println("Your transaction was successfull");
	}

//	public User getUserFromToken(String token) {
//		int userId = (int) Jwts.parser().setSigningKey("123#&*zcvAWEE999").parseClaimsJws(token).getBody().get("id");
//		User user = getById(userId);
//		return user;
//	}

	public User getAuthenticatedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String email = userDetails.getUsername();
		User user = findByEmail(email);
		return user;
	}

	public void checkout(int userId, int cartId) {
		User user = getById(userId);
		Cart cart = cartService.returnCartOrException(cartId);
		double total = cartService.getTotal(cart);
		double totalAfterDiscount = getTotalAfterDiscount(total, user);
		withdraw(totalAfterDiscount);
		givePointsToLoyal(totalAfterDiscount, user);
		cartService.deleteCart(cart);
		withdraw(totalAfterDiscount);
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
