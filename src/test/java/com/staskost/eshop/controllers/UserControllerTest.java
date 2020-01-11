package com.staskost.eshop.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.staskost.eshop.model.User;
import com.staskost.eshop.services.UserService;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

public class UserControllerTest {

	@Mock
	UserService userService;

	UserController controller;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		controller = new UserController(userService);
	}

//	@Test
//	public void getAuthenticatedUser() {
//		User user = userService.getAuthenticatedUser();
//		
//		assertEquals("user", user);
//	}

	@Test
	public void getAllUsers() {
		User user = new User();
		List<User> users = new ArrayList<>();
		users.add(user);

		when(userService.getAll()).thenReturn(users);
		List<User> users2 = controller.getAllUsers();
		assertEquals(users2.size(), 1);
		verify(userService, times(1)).getAll();
	}

//	@Test
//	public void testSayHello() {
//		String hello = controller.sayHello();
//		String Hi = "hello";
//		assert.hello(this.hello.equals("hello"));
//	}

}
