package com.staskost.eshop.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity {

	@Column(name = "name")
	private String name;

	@OneToMany
	@JoinColumn(name = "fk_role_id", referencedColumnName = "id")
	@JsonIgnore
	private List<User> users;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
