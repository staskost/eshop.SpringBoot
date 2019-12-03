package com.staskost.eshop.services;

import java.util.List;

import com.staskost.eshop.model.User;

public interface UserService {

	void save(User user);

	User getById(int id);

	User findByEmail(String email);

	User findByEmailAndPassword(String email, String password);

	List<User> getAll();

	void checkout(int userId, int cartId);

//	User getUserFromToken(String token);

	User getAuthenticatedUser();

}
