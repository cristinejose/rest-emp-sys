package com.ibm.training.bootcamp.rest.sample01.restcontroller;

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

import com.ibm.training.bootcamp.rest.sample01.domain.Employee;
import com.ibm.training.bootcamp.rest.sample01.service.EmployeeService;
import com.ibm.training.bootcamp.rest.sample01.service.EmployeeServiceImpl;

@Path("/employees")
public class EmployeeController {

	private EmployeeService employeeService;

	public EmployeeController() {
		this.employeeService = new EmployeeServiceImpl();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Employee> getEmployees(
			@QueryParam("firstName") String firstName,
			@QueryParam("middleName") String middleName,
			@QueryParam("lastName") String lastName,
			@QueryParam("bDay") String bDay,
			@QueryParam("position") String position){

		try {
			List<Employee> employees;
			
			if (StringUtils.isAllBlank(firstName, middleName, lastName, bDay, position)) {
				employees = employeeService.findAll();
			} else {
				employees = employeeService.findByName(firstName, lastName, position);
			}
						
			return employees;
			
		} catch (Exception e) {
			throw new WebApplicationException(e);
		}

	}


	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addEmployee(Employee employee) {

		try {
			employeeService.add(employee);
			String result = "Employee saved : " + employee.getFirstName() + " " + employee.getMiddleName() + " " + 
					employee.getLastName() + " " + employee.getbDay() + " " + employee.getPosition();
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
	public Response updateEmployee(Employee employee) {

		try {
			employeeService.upsert(employee);
			String result = "Employee updated : " + employee.getFirstName() + " " + employee.getMiddleName() + " " +
					employee.getLastName() + " " + employee.getbDay() + " " + employee.getPosition();
			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			throw new WebApplicationException(e);
		}

	}

	@DELETE
	@Path("{id}")
	public Response deleteEmployee(@PathParam("id") String id) {

		try {
			Long longId = Long.parseLong(id);
			employeeService.delete(longId);
			String result = "Employee deleted";
			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			throw new WebApplicationException(e);
		}
	}
}
