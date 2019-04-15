package com.mijnqiendatabase.qiendatabase.api;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.view.RedirectView;

import com.mijnqiendatabase.qiendatabase.domain.Admin;
import com.mijnqiendatabase.qiendatabase.domain.Klant;
import com.mijnqiendatabase.qiendatabase.domain.Trainee;
import com.mijnqiendatabase.qiendatabase.domain.User;
import com.mijnqiendatabase.qiendatabase.service.UserService;

@CrossOrigin("*")
@Path("/user")
@Controller
//@Component
public class UserApi {
	private final UserService userService;
	@Autowired
	public UserApi(UserService userService) {
	    this.userService = userService;
	}
	
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseEntity getAllUsers() {
		return ResponseEntity.ok(userService.getAllUsers());
	}
	
	@GET
	@Path("/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseEntity getUserByUsername(@PathParam("username") @NotBlank String username) {
		return ResponseEntity.ok(userService.isUserExists(username));
	}
	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseEntity addUser( @RequestBody @Valid User user) {
		System.out.println("check in add");
		User target = null;
		if("trainee".equals(user.getRole())) {
			target = new Trainee();
			target.setAchternaam(user.getAchternaam());
			target.setPassword(user.getPassword());
			target.setUsername(user.getUsername());
			target.setVoornaam(user.getVoornaam());
			target.setRole(user.getRole());
			
			
		}
		else {
			if("admin".equals(user.getRole())) {
				target = new Admin();
				target.setAchternaam(user.getAchternaam());
				target.setPassword(user.getPassword());
				target.setUsername(user.getUsername());
				target.setVoornaam(user.getVoornaam());
				target.setRole(user.getRole());
			}
			else {
				if("klant".equals(user.getRole())) {
					target = new Klant();
					target.setAchternaam(user.getAchternaam());
					target.setPassword(user.getPassword());
					target.setUsername(user.getUsername());
					target.setVoornaam(user.getVoornaam());
					target.setRole(user.getRole());
				}
			}
		}
		
		return ResponseEntity.ok(userService.addUser(target));
	}
	@DELETE
	@Path("/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseEntity deleteUser(@PathParam("username") @NotBlank String username) {
		return ResponseEntity.ok(userService.deleteUser(username));
	}
}