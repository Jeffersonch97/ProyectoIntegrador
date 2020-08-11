package com.project.Veterinary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.Veterinary.entities.Mascota;
import com.project.Veterinary.repository.MascotaRepo;

@Controller
@RequestMapping("/mascotas")
public class MascotaController {

	
		@Autowired
		 private MascotaRepo repo;
		 
		 
		 
		 
		 // @RequestMapping("")
		 // public String index() {
			// return "index";
		 // }
		 
		 // @RequestMapping("/about")
		 // public String about() {
			// return "about";
		 // }
		 
		 @GetMapping("/add_pet")
		 public String showSignUpForm(Mascota mascota) {
		     return "add_pet";
		 }

		 @GetMapping("/list_pet")
		 public String showUsers(Model model) {
			 model.addAttribute("pet", repo.findAll());
		     return "list_pet";
		 }
		 
		 @GetMapping("/list_petA")
		 public String adminUsers(Model model) {
			 model.addAttribute("pet", repo.findAll());
		     return "list_pet";
		 }

		 @PreAuthorize("hasAuthority('admin')")
		 @RequestMapping("/private")
		 public String showPrivate(Model model) {
			 model.addAttribute("pet", repo.findAll());
		     return "list_pet";
		 }
		 
		 @PreAuthorize("hasAuthority('admin')")
		 @PostMapping("/add")
		 public String addUser(Mascota mascota, BindingResult result, Model model) {
		     if (result.hasErrors()) {
		        return "add_pet";
		     }
		     
		     repo.save(mascota);   
		     return "redirect:list_pet";
		 }

		 @PreAuthorize("hasAuthority('admin')")
		 @GetMapping("/edit/{id}")
		 public String showUpdateForm(@PathVariable("id") Long id, Model model) {
		     Mascota mascota = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid pet Id:" + id));
		     model.addAttribute("pet", mascota);
		     return "update_pet";
		 }
		 
		 @PreAuthorize("hasAuthority('admin')")
		 @PostMapping("/update/{id}")
		 public String updateUser(@PathVariable("id") Long id, Mascota mascota, BindingResult result, Model model) {
		     if (result.hasErrors()) {
		          mascota.setId(id);
		          return "update_pet";
		     }
		     
		     
		     repo.save(mascota);
		     return "redirect:/mascotas/list_pet";
		 }

		 @PreAuthorize("hasAuthority('admin')")
		 @GetMapping("/delete/{id}")
		 public String deletePet(@PathVariable("id") Long id, Model model) {
		     Mascota mascota = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		    
		     repo.delete(mascota);	     
		     model.addAttribute("pet", repo.findAll());
		     return "list_pet";
		 }
}
