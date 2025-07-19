package com.ecom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.ecom.model.UserDtls;

public interface UserRepository extends JpaRepository<UserDtls, Integer> {

	public UserDtls findByEmail(String email);

	public List<UserDtls> findByRole(String role);
	
	public UserDtls findByResetToken(String token);
	
}
