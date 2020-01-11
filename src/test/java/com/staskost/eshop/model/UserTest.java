package com.staskost.eshop.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class UserTest {
	
	User user;

	@Before
	public void setUp() throws Exception {
		user = new User();
	}

	@Test
	public void getId() {
		int id = 1;
		
		user.setId(id);
		
		assertEquals(id, user.getId());
	}

}
