package com.ibm.training.bootcamp.rest.sample01.restcontroller;

import java.sql.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;

import com.ibm.training.bootcamp.rest.sample01.domain.User;
import com.ibm.training.bootcamp.rest.sample01.service.UserService;
import com.ibm.training.bootcamp.rest.sample01.service.UserServiceImpl;

@Path("/users")
public class UsersController {

	private UserService userService;

	public UsersController() {
		this.userService = new UserServiceImpl();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getUsers(
			@QueryParam("firstName") String firstName,
			@QueryParam("middleName") String middleName,
			@QueryParam("lastName") String lastName,
			@QueryParam("bDay") String bDay,
			@QueryParam("position") String position){

		try {
			List<User> users;
			
			if (StringUtils.isAllBlank(firstName, middleName, lastName, bDay, position)) {
				users = userService.findAll();
			} else {
				users = userService.findByName(firstName, lastName, position);
			}
						
			return users;
			
		} catch (Exception e) {
			throw new WebApplicationException(e);
		}

	}


	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addUser(User user) {

		try {
			userService.add(user);
			String result = "User saved : " + user.getFirstName() + " " + user.getMiddleName() + " " + 
			user.getLastName() + " " + user.getbDay() + " " + user.getPosition();
			return Response.status(201).entity(result).build();
		} catch (Exception e) {
			
			throw new WebApplicationException(e);
		}

	}
	
	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	@Path("/try")
	public Response tryPost(String str) {

		try {
			
			return Response.status(201).entity(str).build();
		} catch (Exception e) {
			
			throw new WebApplicationException(e);
		}

	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateUser(User user) {

		try {
			userService.upsert(user);
			String result = "User updated : " + user.getFirstName() + " " + user.getMiddleName() + " " +
			user.getLastName() + " " + user.getbDay() + " " + user.getPosition();
			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			throw new WebApplicationException(e);
		}

	}

	@DELETE
	@Path("{id}")
	public Response deleteUser(@PathParam("id") String id) {

		try {
			Long longId = Long.parseLong(id);
			userService.delete(longId);
			String result = "User deleted";
			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			throw new WebApplicationException(e);
		}
	}
}
