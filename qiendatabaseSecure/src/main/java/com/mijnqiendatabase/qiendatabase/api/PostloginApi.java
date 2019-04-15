package com.mijnqiendatabase.qiendatabase.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

import com.mijnqiendatabase.qiendatabase.domain.Admin;
import com.mijnqiendatabase.qiendatabase.domain.Klant;
import com.mijnqiendatabase.qiendatabase.domain.Trainee;
import com.mijnqiendatabase.qiendatabase.domain.User;
import com.mijnqiendatabase.qiendatabase.service.UserService;

@Controller
public class PostloginApi {
	private final UserService userService;
	@Autowired
	public PostloginApi (UserService userService) {
	    this.userService = userService;
	}
	
	@RequestMapping(value = "/postlogin", method=RequestMethod.GET)
	public RedirectView postlogin() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		
		// find user by currentPrincipalName
		
		User user = this.userService.getUserByUsername(currentPrincipalName);
		
		
		if(user instanceof Trainee ) {
			// redirect to the ... 
			return new RedirectView ("dashboardTrainee");
		}
		else if(user instanceof Admin){
			return new RedirectView ("dashboardAdmin");
		}else if(user instanceof Klant) {
			return new RedirectView ("dashboardKlant");
		}else {
			return new RedirectView ("errorerror");
		}
	}
}
