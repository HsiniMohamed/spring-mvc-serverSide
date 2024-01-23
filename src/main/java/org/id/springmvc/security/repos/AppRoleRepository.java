package org.id.springmvc.security.repos;

import org.id.springmvc.security.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole, String> {

}
