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
import com.project.Veterinary.entities.Promocion;
import com.project.Veterinary.repository.PromocionRepo;
import com.project.Veterinary.service.PictureService;
@Controller
@RequestMapping("/promociones")
public class PromocionController {
	@Autowired
	 private PromocionRepo repo;
	 
	 @Autowired
	    PictureService picService;
	 
	 
	 // @RequestMapping("")
	 // public String index() {
		// return "index";
	 // }
	 
	 // @RequestMapping("/about")
	 // public String about() {
		// return "about";
	 // }
	 
	 @GetMapping("/add_promotion")
	 public String showSignUpForm(Promocion promocion) {
	     return "add_promotion";
	 }

	 @GetMapping("/list_promotion")
	 public String showPromotions(Model model) {
		 model.addAttribute("promotions", repo.findAll());
	     return "list_promotions";
	 }
	 

	 @PreAuthorize("hasAuthority('admin')")
	 @RequestMapping("/private")
	 public String showPrivate(Model model) {
		 model.addAttribute("promotions", repo.findAll());
	     return "list_promotions";
	 }
	 
	 @PreAuthorize("hasAuthority('admin')")
	 @PostMapping("/add")
	 public String addPromotion(Promocion promocion, BindingResult result, Model model, @RequestParam("file") MultipartFile file) {
	     if (result.hasErrors()) {
	        return "add_promotion";
	     }
	     UUID idPic = UUID.randomUUID();
	     picService.uploadPicture(file, idPic);
	     promocion.setFoto(idPic);
	     repo.save(promocion);   
	     return "redirect:list_promotion";
	 }

	 @PreAuthorize("hasAuthority('admin')")
	 @GetMapping("/editp/{id}")
	 public String showUpdateForm(@PathVariable("id") Long id, Model model) {
	     Promocion promocion = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid promotion Id:" + id));
	     model.addAttribute("promotion", promocion);
	     return "update_promotion";
	 }
	 
	 @PreAuthorize("hasAuthority('admin')")
	 @PostMapping("/updatep/{id}")
	 public String updatePromotion(@PathVariable("id") Long id, Promocion promocion, BindingResult result, Model model, @RequestParam("file") MultipartFile file) {
	     if (result.hasErrors()) {
	          promocion.setId(id);
	          return "update_promotion";
	     }
	     
	     if (!file.isEmpty()) {
	    	 picService.deletePicture(promocion.getFoto());
		     UUID idPic = UUID.randomUUID();
		     picService.uploadPicture(file, idPic);
		     promocion.setFoto(idPic);
	     }
	     repo.save(promocion);
	     return "redirect:/promociones/list_promotion";
	 }

	 @PreAuthorize("hasAuthority('admin')")
	 @GetMapping("/delete/{id}")
	 public String deletePromotion(@PathVariable("id") Long id, Model model) {
	     Promocion promocion = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid promotion Id:" + id));
	     picService.deletePicture(promocion.getFoto());
	     repo.delete(promocion);	     
	     model.addAttribute("promotions", repo.findAll());
	     return "list_promotions";
	 }
}
