package kr.co.kncom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

import kr.co.kncom.dao.UserDao;
import kr.co.kncom.domain.User;

@ComponentScan
public class UserService {
	
	@Autowired
	private UserDao userDao;
	
	public void saveUserDao() {
		
		User user = new User();
		user.setCol1("col1");
		
		userDao.save(user);
	}
	
}
