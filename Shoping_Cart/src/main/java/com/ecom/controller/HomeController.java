package com.ecom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
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
	public String Products() {
		return "Product";
		
	}

	@GetMapping("/product")
	public String Product() {
		return "view_Product";
		
	}
}
