package kr.co.kncom.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.kncom.domain.User;

public interface UserDao extends JpaRepository<User, Long> {
	
}
