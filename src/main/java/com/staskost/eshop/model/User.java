package com.staskost.eshop.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "email")
	private String email;

	@Column(name = "password")
	private String password;

	@Column(name = "is_loyal")
	private int isLoyal;

	@Column(name = "points")
	private int points;

	@JoinColumn(name = "fk_role_id", referencedColumnName = "id")
	@ManyToOne
	private Role role;

	@Column(name = "secret")
	private String secret;

	@Column(name = "username")
	private String userName;

	@OneToOne(mappedBy = "user")
	private Cart cart;

	public User() {
		super();
	}

	public User(String firstName, String lastName, String email, String password, int isLoyal, int points, Role role,
			String username) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.isLoyal = isLoyal;
		this.points = points;
		this.role = role;
		this.userName = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String retrievePassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Cart retrieveCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public int getIsLoyal() {
		return isLoyal;
	}

	public void setIsLoyal(int isLoyal) {
		this.isLoyal = isLoyal;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String retrieveSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getUserName() {
		return userName;
	}

	public void setUsername(String userName) {
		this.userName = userName;
	}

}
