package org.id.springmvc.web;


import java.util.List;

import org.id.springmvc.dao.IPatientRepository;
import org.id.springmvc.entities.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

@Controller
public class ControllerPatient {

	@Autowired
	private IPatientRepository patientRepository;
	@GetMapping(path="/")
	public String index() {
		
		return "redirect:/user/patients";
	}
	
	@GetMapping(path="/user/patients")
	public String listPatients(Model model,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size,
			@RequestParam(name = "motCle", defaultValue = "") String motCle) {
		
		Page<Patient> pagePatients = patientRepository.findByNomContains(motCle,PageRequest.of(page, size));
		model.addAttribute("patients",pagePatients.getContent());
		model.addAttribute("pages",new int[pagePatients.getTotalPages()]);
		model.addAttribute("currentPage",page);
		model.addAttribute("size",size);
		model.addAttribute("motCle",motCle);

		
		return "patients";
	}
	
	@GetMapping(path = "/admin/deletePatient")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String delete(Long id,String motCle,int page, int size) {
		patientRepository.deleteById(id);
		return "redirect:/user/patients?page="+page+"&motCle="+motCle+"&size="+size;
	}
	
	@GetMapping(path = "/admin/formPatient")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String formPatient(Model model) {
		model.addAttribute("patient",new Patient());
		model.addAttribute("mode","new");
		return "formPatient";
	}
	
	@PostMapping(path ="/admin/savePatient")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String savePatient(@Valid  Patient patient,BindingResult bindingResult,Model model) {
		if(bindingResult.hasErrors()) return "formPatient";
		patientRepository.save(patient);
		model.addAttribute("patient",patient);
		return "confirmation";
	}
	@GetMapping(path ="/admin/editPatient")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String editPatient(Model model, Long id ) {
		Patient p=patientRepository.findById(id).get();
		model.addAttribute("patient",p);
		model.addAttribute("mode","edit");

		return "formPatient";
	}
	
	
}
