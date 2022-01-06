package com.rguktn.drives.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.rguktn.drives.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	
	@Autowired
	private CustomUserDetailsService userDetailsService;

//	@Bean
//	  public UserDetailsService uds() {
//	    var uds = new InMemoryUserDetailsManager();
//	 
//	    var u = User.withUsername("john")
//	                .password("12345")
//	                .authorities("read")
//	                .build();
//	 
//	    uds.createUser(u);
//	 
//	    return uds;
//	  }
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	 
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	 
	  @Override
	protected void configure(HttpSecurity http) throws Exception {
//		http.cors().and().authorizeRequests().antMatchers("/api/auth/**").permitAll();
		  http.authorizeRequests().antMatchers("/swagger-ui/**").permitAll()
		  .antMatchers("/api/auth/user").permitAll().and().cors().and().csrf().disable();
	}
	  
	  @Override
	  public void configure(WebSecurity web) throws Exception {
	      web.ignoring().antMatchers("/swagger-ui/**").antMatchers("/v3/api-docs");
	  }
	  
	    @Bean
	    CorsConfigurationSource corsConfigurationSource() {
	        CorsConfiguration configuration = new CorsConfiguration();
	        configuration.setAllowedOrigins(Arrays.asList("*"));
	        configuration.setAllowedMethods(Arrays.asList("*"));
	        configuration.setAllowedHeaders(Arrays.asList("*"));
	        configuration.setAllowCredentials(true);
	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        source.registerCorsConfiguration("/**", configuration);
	        return source;
	    }

	@Bean
	  public AuthenticationManager authenticationManagerBean() 
	    throws Exception {
	      return super.authenticationManagerBean();
	  }
}
