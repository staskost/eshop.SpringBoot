package com.staskost.eshop.services;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.staskost.eshop.model.Product;
import com.staskost.eshop.model.User;
import com.staskost.eshop.repos.UserRepository;

import io.jsonwebtoken.Jwts;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
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

	public void createUser(User user) {
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

	private Object getClaimFromToken(String token) {
		Object user = Jwts.parser().setSigningKey("123#&*zcvAWEE999").parseClaimsJws(token).getBody().get("user");
		return user;
	}

	public User getUserFromToken(String token) {
		Object obj = getClaimFromToken(token);
		ObjectMapper mapper = new ObjectMapper();
		User user = mapper.convertValue(obj, User.class);
		if(user == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found");
		}
		return user;
	}


}
