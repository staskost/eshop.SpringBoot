package com.staskost.eshop.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.staskost.eshop.model.Cart;
import com.staskost.eshop.model.User;

public interface CartRepository extends JpaRepository<Cart, Integer>{

	Cart findByUser(User user);
}
