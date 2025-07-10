package com.ecom.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ecom.model.Product;
import com.ecom.model.UserDtls;
import com.ecom.model.Category;
import com.ecom.service.CategoryService;
import com.ecom.service.ProductService;
import com.ecom.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController{
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	public String Index() {
		return "Index";
		
	}
	
	@GetMapping("/login")
	public String Login() {
		return "Login";
		
	}
	
	@GetMapping("/register")
	public String Register() {
		return "Register";
		
	}
	
	@GetMapping("/products")
	public  String Products(Model m, @RequestParam(value = "category" ,defaultValue = "" ) String category) {
	List<Category> categories = categoryService.getAllActiveCategory();
	List<Product> products = productService.getAllActiveProducts(category);
     m.addAttribute("categories", categories);
     m.addAttribute("products",products);
	 m.addAttribute("paramValue", category);	
     
     return "Product";
		
	}

	@GetMapping("/product/{id}")
	public String Product(@PathVariable int id , Model m) {
		Product productById = productService.getProductById(id);
		m.addAttribute("product", productById);
		return "view_Product";
		
	}
	
	@PostMapping("/saveUser")
	public String saveUser(@ModelAttribute UserDtls user, @RequestParam("img") MultipartFile file, HttpSession session) throws IOException {
		
		String imageName = file.isEmpty() ? "default.jpg" : file.getOriginalFilename() ;
		user.setProfileImage(imageName);
		UserDtls saveUser = userService.saveUser(user);
		
		if(!ObjectUtils.isEmpty(saveUser)) {
			
			if(!file.isEmpty()) {
				
              File saveFile = new ClassPathResource("static/img").getFile();
			
				Path path = Paths.get(saveFile.getAbsolutePath()+File.separator+"profile_img"
				+File.separator+file.getOriginalFilename());
				
				System.out.println(path);
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			}
			session.setAttribute("succMsg", "register sucessfully");	
		}else {
			 session.setAttribute("errorMsg", "somethimg wrong on server");

		}
		
		return "redirect:/register";
	}
}





















