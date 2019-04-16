package com.mijnqiendatabase.qiendatabase.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login");
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
		registry.addViewController("/users").setViewName("users");
		registry.addViewController("/register").setViewName("register");
		registry.addViewController("/forgotPassword").setViewName("forgotPassword");
		
		//admin
		registry.addViewController("/dashboardAdmin").setViewName("dashboardAdmin");
		registry.addViewController("/AdminUrenoverzicht").setViewName("AdminUrenoverzicht");
		registry.addViewController("/AdminBeheer").setViewName("AdminBeheer");
		
		//trainee
		registry.addViewController("/dashboardTrainee").setViewName("dashboardTrainee");
		registry.addViewController("/TraineeDeclaraties").setViewName("TraineeDeclaraties");
		registry.addViewController("/TraineeUrenArchief").setViewName("TraineeUrenArchief");
		
		//klant
		registry.addViewController("/dashboardKlant").setViewName("dashboardKlant");
		registry.addViewController("/KlantUrenArchief").setViewName("KlantUrenArchief");
	

		registry.addViewController("/forgotPassword").setViewName("forgotPassword");
	}
	
}
