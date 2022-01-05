package com.rguktn.drives.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rguktn.drives.data.Role;
import com.rguktn.drives.data.User;
import com.rguktn.drives.repository.PermissionRepository;
import com.rguktn.drives.repository.RoleRepository;
import com.rguktn.drives.repository.UserRepository;

@RestController
@RequestMapping("/api/auth")
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AuthController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private PermissionRepository permRepo;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@GetMapping
	public List<User> auth() {
		return userRepository.findAll();
	}
	
	@PostMapping("/user")
	@PreAuthorize("hasRole('ADMIN')")
	public User addUser(@RequestBody User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}
	
	@PutMapping("/user")
	@PreAuthorize("hasRole('ADMIN')")
	public User updateUser(@RequestBody User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}
	
	@GetMapping("/roles")
	@PreAuthorize("hasRole('ADMIN')")
	public List<Role> getRoles(){
		return roleRepo.findAll();
	}
	
	@PostMapping("/role")
	@PreAuthorize("hasRole('ADMIN')")
	public Role addRole(@RequestBody Role role) {
		return roleRepo.save(role);
	}
	
//	@GetMapping("/Permissions")
//	public List<Permission> getPermissions(){
//		return permRepo.findAll();
//	}
//	
//	@PostMapping("/permission")
//	public Permission addPerm(@RequestBody Permission permission) {
//		return permRepo.save(permission);
//	}
}
