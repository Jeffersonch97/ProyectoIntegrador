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
import org.springframework.web.multipart.MultipartFile;

import com.project.Veterinary.entities.Producto;
import com.project.Veterinary.repository.ProductoRepo;
import com.project.Veterinary.service.PictureService;

@Controller
@RequestMapping("/productos")
public class ProductoController {
	@Autowired
	 private ProductoRepo repo;
	 
	 @Autowired
	    PictureService picService;
	 
	 
	 @RequestMapping("")
	 public String index() {
		return "index";
	 }
	 
	 @RequestMapping("/about")
	 public String about() {
		return "about";
	 }
	 
	 @GetMapping("/add_recipe")
	 public String showSignUpForm(Producto producto) {
	     return "add_recipe";
	 }

	 @GetMapping("/list")
	 public String showRecipes(Model model) {
		 model.addAttribute("recipes", repo.findAll());
	     return "list_recipes";
	 }
	 
	 @RequestMapping("/login")
	 public String showLogin() {
	     return "login";
	 }
	 
	 @GetMapping("/sc")
	 public String showRecipes() {
	     return "soundcloud.html";
	 }	 
	 
	 @PreAuthorize("hasAuthority('admin')")
	 @RequestMapping("/private")
	 public String showPrivate(Model model) {
		 model.addAttribute("recipes", repo.findAll());
	     return "list_recipes";
	 }
	 
	 @PreAuthorize("hasAuthority('admin')")
	 @PostMapping("/add")
	 public String addRecipe(Producto producto, BindingResult result, Model model, @RequestParam("file") MultipartFile file) {
	     if (result.hasErrors()) {
	        return "add_recipe";
	     }
	     UUID idPic = UUID.randomUUID();
	     picService.uploadPicture(file, idPic);
	     producto.setFoto(idPic);
	     repo.save(producto);   
	     return "redirect:list";
	 }

	 @PreAuthorize("hasAuthority('admin')")
	 @GetMapping("/edit/{id}")
	 public String showUpdateForm(@PathVariable("id") Long id, Model model) {
	     Producto producto = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid recipe Id:" + id));
	     model.addAttribute("recipe", producto);
	     return "update_recipe";
	 }
	 
	 @PreAuthorize("hasAuthority('admin')")
	 @PostMapping("/update/{id}")
	 public String updateRecipe(@PathVariable("id") Long id, Producto producto, BindingResult result, Model model, @RequestParam("file") MultipartFile file) {
	     if (result.hasErrors()) {
	          producto.setId(id);
	          return "update_recipe";
	     }
	     
	     if (!file.isEmpty()) {
	    	 picService.deletePicture(producto.getFoto());
		     UUID idPic = UUID.randomUUID();
		     picService.uploadPicture(file, idPic);
		     producto.setFoto(idPic);
	     }
	     repo.save(producto);
	     return "redirect:/productos/list";
	 }

	 @PreAuthorize("hasAuthority('admin')")
	 @GetMapping("/delete/{id}")
	 public String deleteRecipe(@PathVariable("id") Long id, Model model) {
	     Producto producto = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid recipe Id:" + id));
	     picService.deletePicture(producto.getFoto());
	     repo.delete(producto);	     
	     model.addAttribute("recipes", repo.findAll());
	     return "list_recipes";
	 }
}
