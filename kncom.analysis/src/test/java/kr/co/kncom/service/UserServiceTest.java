package kr.co.kncom.service;

import org.junit.Test;

public class UserServiceTest {

	@Test
	public void test() {
		
		UserService userService = new UserService();
		userService.saveUserDao();
		
	}

}
