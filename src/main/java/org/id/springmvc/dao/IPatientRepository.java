package org.id.springmvc.dao;

import org.id.springmvc.entities.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPatientRepository extends JpaRepository<Patient, Long> {

	public Page<Patient> findByNomContains(String mc,Pageable pageable);
}
