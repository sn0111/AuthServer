package com.rguktn.drives.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rguktn.drives.data.Role;
import com.rguktn.drives.data.User;
import com.rguktn.drives.repository.RoleRepository;
import com.rguktn.drives.repository.UserRepository;

@RestController
@RequestMapping("/api/admin")
public class TestController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@PostMapping("/user")
	public User addUser(@RequestBody User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}
	
	@PostMapping("/role")
	public Role addRole(@RequestBody Role role) {
		return roleRepo.save(role);
	}
}
