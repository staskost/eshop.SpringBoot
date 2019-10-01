package com.staskost.eshop.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.staskost.eshop.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	User findByEmail(String email);

	User findByEmailAndPassword(String email, String password);

}
