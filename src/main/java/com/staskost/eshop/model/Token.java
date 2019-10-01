package com.staskost.eshop.model;

import java.io.Serializable;

public class Token implements Serializable {

	private static final long serialVersionUID = 1L;

	private String alphanumeric;

	public Token() {
	}

	public Token(String alphanumeric) {
		super();
		this.alphanumeric = alphanumeric;
	}

	public String getAlphanumeric() {
		return alphanumeric;
	}

	public void setAlphanumeric(String alphanumeric) {
		this.alphanumeric = alphanumeric;
	}

}
