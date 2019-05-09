package com.mijnqiendatabase.qiendatabase.api;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
	public User getUserByUsername(@PathParam("username") @NotBlank String username) {
		User user = userService.getUserByUsername(username);
		return user;
	}
	
	@PUT
	@Path("{id}/password")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseEntity changePassword( @RequestBody @Valid User user) {
		System.out.println("check in password");
		User target = userService.getUserByUsername(user.getUsername());
		System.out.println(target.getPassword());
		System.out.println(target.getPassword());
		System.out.println(">"+user.getPassword()+"<");
		
		return ResponseEntity.ok(userService.updateUser(target, user.getPassword()));
	}
	
	
	
	
	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseEntity addUser( @RequestBody @Valid User user) {
		System.out.println("check in add");
		//user check of al bestaat
		
		User target = null;
		if("trainee".equals(user.getRole())) {
			System.out.println("trainee");
			target = new Trainee();
			target.setAchternaam(user.getAchternaam());
			target.setPassword(user.getPassword());
			target.setUsername(user.getUsername());
			target.setVoornaam(user.getVoornaam());
			target.setEmailadres(user.getEmailadres());
			target.setRole(user.getRole());
//			if(target instanceof Trainee) {
//				((Trainee) target).setKlant(((Trainee) user).getKlant());
//			}
			
			
		}
		else {
			if("admin".equals(user.getRole())) {
				target = new Admin();
				target.setAchternaam(user.getAchternaam());
				target.setPassword(user.getPassword());
				target.setUsername(user.getUsername());
				target.setVoornaam(user.getVoornaam());
				target.setEmailadres(user.getEmailadres());
				target.setRole(user.getRole());
			}
			else {
				if("klant".equals(user.getRole())) {
					System.out.println("klant");
					target = new Klant();
					target.setAchternaam(user.getAchternaam());
					target.setPassword(user.getPassword());
					target.setUsername(user.getUsername());
					target.setEmailadres(user.getEmailadres());
					target.setVoornaam(user.getVoornaam());
					target.setRole(user.getRole());
//					if(target instanceof Klant) {
//						((Klant) target).setTrainee(((Klant) user).getTrainee());
//						((Klant) target).setBedrijf(((Klant) user).getBedrijf());
//					}
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