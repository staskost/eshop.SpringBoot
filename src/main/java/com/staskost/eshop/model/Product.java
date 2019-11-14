package com.staskost.eshop.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "product")
public class Product extends BaseEntity {

	@Column(name = "name")
	private String name;

	@Column(name = "price")
	private double price;

	@Column(name = "category")
	private String category;

	@Column(name = "description")
	private String description;

	@ManyToMany(mappedBy = "cartProducts")
	@JsonIgnore
	List<Cart> carts;

	@Column(name = "count")
	private int productCount;

	@Column(name = "is_availabe")
	private int isAvailabe;

	@Column(name = "photo_link")
	private String photLink;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Cart> getCarts() {
		return carts;
	}

	public void setCarts(List<Cart> carts) {
		this.carts = carts;
	}

	public int getProductCount() {
		return productCount;
	}

	public void setProductCount(int productCount) {
		this.productCount = productCount;
	}

	public int getIsAvailabe() {
		return isAvailabe;
	}

	public void setIsAvailabe(int isAvailabe) {
		this.isAvailabe = isAvailabe;
	}

	public String getPhotLink() {
		return photLink;
	}

	public void setPhotLink(String photLink) {
		this.photLink = photLink;
	}

}
