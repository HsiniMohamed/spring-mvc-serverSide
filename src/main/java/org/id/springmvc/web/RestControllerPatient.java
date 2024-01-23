package org.id.springmvc.web;


import java.util.List;

import org.id.springmvc.dao.IPatientRepository;
import org.id.springmvc.entities.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

@RestController
public class RestControllerPatient {

	@Autowired
	private IPatientRepository patientRepository;
	
	@GetMapping(path ="/listPatients")
	public List<Patient> lisPatients( ) {
		return patientRepository.findAll();
	}
	@GetMapping(path ="/patients/{id}")

	public Patient getPatient(@PathVariable Long id ) {
		return patientRepository.findById(id).get();
	}
	
}
