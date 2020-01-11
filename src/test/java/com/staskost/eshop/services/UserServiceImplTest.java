//package com.staskost.eshop.services;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import com.staskost.eshop.model.User;
//import com.staskost.eshop.repos.UserRepository;
//
//public class UserServiceImplTest {
//
//	UserServiceImpl userService;
//
//	@Mock
//	UserRepository userRepository;
//
//	@Before
//	public void setUp() throws Exception {
//		MockitoAnnotations.initMocks(this);
//		userService = new UserServiceImpl(userRepository);
//	}
//
//	@Test
//	public void getAll() throws Exception {
//		User user = new User();
//		List<User> users = new ArrayList<>();
//		users.add(user);
//		
//		when(userRepository.findAll()).thenReturn(users);
//		List<User> users2 = userService.getAll();
//		
//		assertEquals(users2.size(), 1);
//		verify(userRepository, times(1)).findAll();
//	}
//
//}
