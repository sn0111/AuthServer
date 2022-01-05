package com.rguktn.drives.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rguktn.drives.data.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	public User findByUsername(String username);
}
