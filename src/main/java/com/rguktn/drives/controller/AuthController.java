package com.rguktn.drives.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
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
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

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
	
	@GetMapping("/sms")
	public String sms() {
        Twilio.init("AC0a7f466490f27f87fd2b4f29eba09253", "8a6df5398177f4b345a4d760cad97ae0"); 
        Message message = Message.creator( 
                new com.twilio.type.PhoneNumber("whatsapp:+919725575554"), 
                new com.twilio.type.PhoneNumber("whatsapp:+14155238886"), 
                "Hi Aneri Good Afternoon")      
            .create(); 
 
        System.out.println(message.getSid());
        return "sms";
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
//	@PreAuthorize("hasRole('ADMIN')")
	public List<Role> getRoles(){
		return roleRepo.findAll();
	}
	
	@PostMapping("/role")
	@PreAuthorize("hasRole('ADMIN')")
	public Role addRole(@RequestBody Role role) {
		return roleRepo.save(role);
	}
	
	@GetMapping("/get")
	public Map<?, ?> getDetails() {
		Map<?, ?> userAuthentication = new HashMap<>();
	    try {
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        if (!(authentication instanceof OAuth2Authentication)) {
	            return userAuthentication;
	        }
	        OAuth2Authentication oauth2Authentication = (OAuth2Authentication) authentication;
	        Authentication userauthentication = oauth2Authentication.getUserAuthentication();
	        System.out.println(oauth2Authentication.getUserAuthentication().getName());
	        if (userauthentication == null) {
	            return userAuthentication;
	        }
	        Map<?, ?> details = (HashMap<?, ?>) userauthentication.getDetails();    //this effect in the new RW OAUTH2 userAuthentication
	        Object principal = details.containsKey("principal") ? details.get("principal") : userAuthentication; //this should be effect in the common OAUTH2 userAuthentication
	        if (!(principal instanceof Map)) {
	            return userAuthentication;
	        }
	        userAuthentication = (Map<?, ?>) principal;
	    } catch (Exception e) {
//	        logger.error("Got exception while trying to obtain user info from security context.", e);
	    	System.out.println(e.getMessage());
	    }
	    return userAuthentication;
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
