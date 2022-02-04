package com.example.second.dao;



import com.example.second.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,String> {
	

	User findByMobileNumber(String mobile);
	User findByUserName(String userName);
	User findByEmail(String email);
}
