package com.mijnqiendatabase.qiendatabase.api;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.mijnqiendatabase.qiendatabase.domain.Klant;
import com.mijnqiendatabase.qiendatabase.domain.Kosten;
import com.mijnqiendatabase.qiendatabase.domain.Trainee;
import com.mijnqiendatabase.qiendatabase.domain.Uur;
import com.mijnqiendatabase.qiendatabase.service.KostenService;
import com.mijnqiendatabase.qiendatabase.service.TraineeService;
import com.mijnqiendatabase.qiendatabase.service.UserService;
import com.mijnqiendatabase.qiendatabase.service.UurService;;


@Path("trainee")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Component
public class TraineeApi {

	// Jordi: kosten deel toegevoegd
	@Autowired
	private KostenService kostenService;
	@Autowired
	private TraineeService traineeService;
	@Autowired
	private UurService uurService;
	@Autowired
	private UserService userService;

	@POST // Create
	public Response apiCreate(Trainee trainee) {
		if (trainee.getId() != 0) {
			return Response.status(Response.Status.CONFLICT).build();
		}
		return Response.ok(traineeService.save(trainee)).build();
	}

	@GET // Retrieve/Read
	@Path("current")
	public Response apiGetByLogin() {
		
		
  		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		
		// find user by currentPrincipalName
		
		Trainee traineex = (Trainee)this.userService.getUserByUsername(currentPrincipalName);
		System.out.println(traineex.getUsername());
		System.out.println(traineex.getUren());
		return Response.ok(traineex).build();
	}
	
	@GET // Retrieve/Read
	@Path("{id}")
	public Response apiGetById(@PathParam("id") long id) {
		System.out.println("check in trainee/id");
		Optional<Trainee> trainee = traineeService.findById(id);
		if (trainee.isPresent() == false) {
			return Response.status(Response.Status.NOT_FOUND).build();
		} else {
			return Response.ok(trainee.get()).build();
		}
	}
	
	@GET // Retrieve/Read
  	public Response apiGetAll() {
         	return Response.ok(traineeService.findAll()).build();
  	}
 
	@PUT // Update
  	@Path("{id}")
  	public Response apiUpdate(@PathParam("id") long id, Trainee trainee) {
  		
  			// BAD REQUEST
  			System.out.println("in trainee Uren " + trainee.getId());
         	if (trainee == null || trainee.getId() != id) {
               	System.out.println("bad request?");
         		return Response.status(Response.Status.BAD_REQUEST).build();
         	}
         	Optional<Trainee> optionalOldTrainee = traineeService.findById(id);
         	
         	// NOT FOUND
         	if (!optionalOldTrainee.isPresent()) {
         		System.out.println("not found?");
               	return Response.status(Response.Status.NOT_FOUND).build();
         	}
			Trainee target = optionalOldTrainee.get();
         	// 

         	for(Uur uur : trainee.getUren()) {
         		uur.setTrainee(target);
         		uurService.save(uur);
         		target.addUur(uur);
         	}

         	target.setUren(trainee.getUren());
         	System.out.println(target.getUren());
         	
         	// Jordi: basically copy paste, maar met kosten ipv uren
         	Set<Kosten> nieuwekosten = new HashSet();
         	for(Kosten k : trainee.getKosten()) {
         		System.out.println("trainee.getKosten():" + trainee.getKosten());
         		nieuwekosten.add(kostenService.save(k));
         		System.out.println("k.getFactuurDatum()" + k.getFactuurDatum());
         	}
         	trainee.setKosten(nieuwekosten);
         	target.setKosten(trainee.getKosten());
         	System.out.println(target.getKosten());

         	return Response.ok(traineeService.save(target)).build();
  	}
  	

  	
  	@DELETE // Delete
  	@Path("{id}")
  	public Response apiDeleteById(@PathParam("id") long id) {
         	if (traineeService.findById(id).isPresent() == false) {
               	return Response.status(Response.Status.NOT_FOUND).build();
         	} else {
               	traineeService.deleteById(id);
               	return Response.status(Response.Status.OK).build();
         	}
  	}

}