package com.project.Veterinary.controller;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.project.Veterinary.entities.Usuario;
import com.project.Veterinary.entities.Usuario;
import com.project.Veterinary.repository.UsuarioRepo;
import com.project.Veterinary.repository.UsuarioRepo;
import com.project.Veterinary.service.PictureService;
@Controller
@RequestMapping("/users")
public class UserController {
	@Autowired
	 private UsuarioRepo repo;
	 
	 
	 
	 
	 // @RequestMapping("")
	 // public String index() {
		// return "index";
	 // }
	 
	 // @RequestMapping("/about")
	 // public String about() {
		// return "about";
	 // }
	 
	 @GetMapping("/add_user")
	 public String showSignUpForm(Usuario usuario) {
	     return "add_user";
	 }

	 @GetMapping("/list_user")
	 public String showUsers(Model model) {
		 model.addAttribute("users", repo.findAll());
	     return "list_users";
	 }
	 
	 @GetMapping("/list_userA")
	 public String adminUsers(Model model) {
		 model.addAttribute("users", repo.findAll());
	     return "list_users";
	 }

	 @PreAuthorize("hasAuthority('admin')")
	 @RequestMapping("/private")
	 public String showPrivate(Model model) {
		 model.addAttribute("users", repo.findAll());
	     return "list_users";
	 }
	 
	 @PreAuthorize("hasAuthority('admin')")
	 @PostMapping("/add")
	 public String addUser(Usuario usuario, BindingResult result, Model model) {
	     if (result.hasErrors()) {
	        return "add_user";
	     }
	     
	     repo.save(usuario);   
	     return "redirect:list_user";
	 }

	 @PreAuthorize("hasAuthority('admin')")
	 @GetMapping("/edit/{id}")
	 public String showUpdateForm(@PathVariable("id") Long id, Model model) {
	     Usuario usuario = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
	     model.addAttribute("user", usuario);
	     return "update_user";
	 }
	 
	 @PreAuthorize("hasAuthority('admin')")
	 @PostMapping("/update/{id}")
	 public String updateUser(@PathVariable("id") Long id, Usuario usuario, BindingResult result, Model model) {
	     if (result.hasErrors()) {
	          usuario.setId(id);
	          return "update_user";
	     }
	     
	     
	     repo.save(usuario);
	     return "redirect:/users/list_user";
	 }

	 @PreAuthorize("hasAuthority('admin')")
	 @GetMapping("/delete/{id}")
	 public String deleteUser(@PathVariable("id") Long id, Model model) {
	     Usuario usuario = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
	    
	     repo.delete(usuario);	     
	     model.addAttribute("users", repo.findAll());
	     return "list_users";
	 }
}
