package com.staskost.eshop.services;

import java.util.List;

import com.staskost.eshop.model.User;

public interface UserService {

	void createUser(User user);

	User getById(int id);
	
	User findByEmail(String email);
	
	User findByEmailAndPassword(String email, String password);

	List<User> getAll();
	
	public void givePointsToLoyal(double total, User user);
	
	double getTotalAfterDiscount(double total, User user);
	
	void withdraw(double total);
	
	User getUserFromToken(String token);
	
}
