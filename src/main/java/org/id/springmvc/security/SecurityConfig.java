package org.id.springmvc.security;

import javax.sql.DataSource;

import org.id.springmvc.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import lombok.AllArgsConstructor;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class SecurityConfig  {
	
	private PasswordEncoder passwordEncoder;
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	
	
	//@Bean
	public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource) {
		
		return new JdbcUserDetailsManager(dataSource);
	}
	
	//@Bean
	public InMemoryUserDetailsManager inMemoryUserDetailsManager() {	
		return new InMemoryUserDetailsManager(
				User.withUsername("user1").password(passwordEncoder.encode("1234")).roles("USER").build(),
				User.withUsername("user2").password(passwordEncoder.encode("1234")).roles("USER").build(),
				User.withUsername("admin").password(passwordEncoder.encode("1234")).roles("USER","ADMIN").build()
				);
	}
	
	@Bean
	public SecurityFilterChain securityFilterchain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.formLogin().loginPage("/login").defaultSuccessUrl("/").permitAll();
		//httpSecurity.rememberMe();
		httpSecurity.authorizeHttpRequests().requestMatchers("/webjars/**").permitAll();
		//httpSecurity.authorizeHttpRequests().requestMatchers("/user/**").hasAnyRole("USER");
		//httpSecurity.authorizeHttpRequests().requestMatchers("/admin/**").hasAnyRole("ADMIN");
		httpSecurity.authorizeHttpRequests().anyRequest().authenticated();
		httpSecurity.exceptionHandling().accessDeniedPage("/notAuthorized");
		httpSecurity.userDetailsService(userDetailsServiceImpl);
		return httpSecurity.build();
		
	}
}
