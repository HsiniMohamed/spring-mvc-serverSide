package org.id.springmvc.security.service;

import java.util.UUID;

import org.id.springmvc.security.entities.AppRole;
import org.id.springmvc.security.entities.AppUser;
import org.id.springmvc.security.repos.AppRoleRepository;
import org.id.springmvc.security.repos.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class AccountServiceImpl implements AccountService{
	
	private AppRoleRepository appRoleRepository;
	private AppUserRepository appUserRepository;
	private PasswordEncoder passwordEncoder;
	@Override
	public AppUser addNewUser(String username, String password, String email, String confirmPassword) {
		
		AppUser user= appUserRepository.findByUsername(username);
		if(user != null) throw new RuntimeException("this user already exist");
		if(!password.equals(confirmPassword)) throw new RuntimeException("password not match");
		 user = AppUser.builder()
				.userId(UUID.randomUUID().toString())
				.username(username)
				.password(passwordEncoder.encode(password))
				.email(email)
				.build();
	AppUser savedAppUser=appUserRepository.save(user);
				
		return savedAppUser;
	}

	@Override
	public AppRole addNewRole(String role) {
		AppRole appRole =appRoleRepository.findById(role).orElse(null);
		if(appRole != null) throw new RuntimeException("this role already exist");
		appRole = AppRole.builder()
				.role(role)
				.build();
		
		return appRoleRepository.save(appRole);
	}

	@Override
	public void addRoleToUser(String username, String role) {
		
		AppUser appUser =appUserRepository.findByUsername(username);
		AppRole appRole =appRoleRepository.findById(role).get();
		appUser.getRoles().add(appRole);
		
	}

	@Override
	public void removeRoleFromUser(String username, String role) {
		AppUser appUser =appUserRepository.findByUsername(username);
		AppRole appRole =appRoleRepository.findById(role).get();
		appUser.getRoles().remove(appRole);
	}

	@Override
	public AppUser loadUserByUsername(String username) {
		// TODO Auto-generated method stub
		return appUserRepository.findByUsername(username);
	}

}
