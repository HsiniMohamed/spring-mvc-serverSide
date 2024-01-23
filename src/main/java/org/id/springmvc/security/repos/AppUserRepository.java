package org.id.springmvc.security.repos;

import org.id.springmvc.security.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, String>{
	 AppUser findByUsername(String username);

}
