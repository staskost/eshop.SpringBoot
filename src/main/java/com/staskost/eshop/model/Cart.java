package com.staskost.eshop.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "cart")
public class Cart extends BaseEntity {

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_user_id", referencedColumnName = "id")
	@JsonIgnore
	private User user;

	@ManyToMany
	@JoinTable(name = "cart_product", joinColumns = @JoinColumn(name = "fk_cart_id"), inverseJoinColumns = @JoinColumn(name = "fk_product_id"))
	@JsonIgnore
	List<Product> cartProducts;
	
	public void addProcuct(Product product) {
		this.cartProducts.add(product);
	}

	public void removeProduct(Product product) {
		this.cartProducts.remove(product);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Product> getCartProducts() {
		return cartProducts;
	}

	public void setCartProducts(List<Product> cartProducts) {
		this.cartProducts = cartProducts;
	}
	
	
}
