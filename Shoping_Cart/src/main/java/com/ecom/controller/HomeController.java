package com.ecom.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import com.ecom.service.CartService;
import com.ecom.service.CategoryService;
import com.ecom.service.ProductService;
import com.ecom.service.UserService;
import com.ecom.util.CommanUtil;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController{
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CommanUtil commanUtil;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private CartService cartService;
	
	@ModelAttribute
	public void getUserDetails(Principal p, Model m) {
		
		if(p!=null) {
			String email = p.getName();
			UserDtls userDtls = userService.getUserByEmail(email);
			m.addAttribute("user",userDtls);
			Integer countCart = cartService.getCountCart(userDtls.getId());
			m.addAttribute("countCart", countCart);
		
	
		}
		List<Category> allActiveCategory = categoryService.getAllActiveCategory();
		m.addAttribute("categorys", allActiveCategory);
		
	}
	
	@GetMapping("/")
	public String Index() {
		return "Index";
		
	}
	
	@GetMapping("/signin")
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
	
	//Forgot password Code
	
	@GetMapping("/forgot-password")
	public String showForgotPassword() {
		return "forgot_password.html";
	}
	
	@PostMapping("/forgot-password")
	public String processForgotPassword(@RequestParam String email,HttpSession session,HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
		
		UserDtls userByEmail = userService.getUserByEmail(email);
		
		if(ObjectUtils.isEmpty(userByEmail)) 
		{
			session.setAttribute("errorMsg", "Invalid email");
		}else {
			
			String resetToken = UUID.randomUUID().toString();
			userService.updateUserResetToken(email,resetToken);
			
			//Generate URL
			
			String url =CommanUtil.generateUrl(request)+"/reset-password?token="+resetToken;
			
			Boolean sendMail = commanUtil.sendMail(url, email);
			
			if(sendMail) {
				session.setAttribute("errorMsg", "please ckeck your email..Password Reset link send");

			}else {
				session.setAttribute("msg", "Something wrong on server email not send");

			}
		}
		return "redirect:/forgot-password";
	}
	
	@GetMapping("/reset-password")
	public String showResetPassword(@RequestParam String token, HttpSession session, Model m) {
		
		UserDtls userByToken = userService.getUserByToken(token);
		
		if(userByToken==null) {
			m.addAttribute("msg", "Your link is invailed or expaired");
			return "message";
		}
		m.addAttribute("token",token);
		return "reset_password";
	}
	
	@PostMapping("/reset-password")
	public String resetPassword(@RequestParam String token,@RequestParam String password,HttpSession session, Model m) {
		
		UserDtls userByToken = userService.getUserByToken(token);
		
		if(userByToken==null) {
			m.addAttribute("errorMsg", "Your link is invailed or expaired");
			return "message";
		}else {
			userByToken.setPassword(passwordEncoder.encode(password));
			userByToken.setResetToken(null);
			userService.updateUser(userByToken);
			session.setAttribute("succMsg", "Password change successfully");
			m.addAttribute("msg", "Password change successfully");
			return "message";
		}
		
		
		
	}
}





















