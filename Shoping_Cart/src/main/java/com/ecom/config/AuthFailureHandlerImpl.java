package com.ecom.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.ecom.model.UserDtls;
import com.ecom.repository.UserRepository;
import com.ecom.service.UserService;
import com.ecom.util.AppConstant;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
@Component
public class AuthFailureHandlerImpl extends SimpleUrlAuthenticationFailureHandler {
	
	@Autowired
	private UserRepository userRepository; 
	
	@Autowired
	private UserService userService;
	

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
		 String email = request.getParameter("username");
		
		UserDtls userDtls = userRepository.findByEmail(email);
		
		if(userDtls.getIsEnable()) {
			
			if(userDtls.getAccountNonLocked()) {
				
				if(userDtls.getFailedAttempt() < AppConstant.ATTEMPT_TIME) {
					userService.increaseFailedAttempt(userDtls);

				}else {
					userService.userAccountLock(userDtls);
					exception = new LockedException("your account is Locked !! failed attempt 3");

					
				}
				
			}else {
				if(userService.unlockAccountTimeExpired(userDtls)) {
					
					exception = new LockedException("your account is unLocked !! please try to login");
				}else {
				exception = new LockedException("your account is Locked !! please try after sometimes");

			}
	}
			
		}else {
			exception = new LockedException("your account is inactivate");
		}
		
		super.setDefaultFailureUrl("/signin?error");
		super.onAuthenticationFailure(request, response, exception);
	}

}
