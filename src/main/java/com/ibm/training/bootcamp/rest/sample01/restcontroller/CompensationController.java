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

import com.ibm.training.bootcamp.rest.sample01.domain.Compensation;
import com.ibm.training.bootcamp.rest.sample01.service.CompensationService;
import com.ibm.training.bootcamp.rest.sample01.service.CompensationServiceImpl;

@Path("/compensations")
public class CompensationController {

	private CompensationService compensationService;

	public CompensationController() {
		this.compensationService = new CompensationServiceImpl();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Compensation> getCompensations(@QueryParam("month") String month, @QueryParam("salary") String salary,
			@QueryParam("bonus") String bonus, @QueryParam("commission") String commission) {

		try {
			List<Compensation> compensations;

			if (StringUtils.isAllBlank(month, salary, bonus, commission)) {
				compensations = compensationService.findAll();
			} else {
				compensations = compensationService.findByMonth(month);
			}
			return compensations;

		} catch (Exception e) {
			throw new WebApplicationException(e);
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addCompensation(Compensation compensation) {

		try {
			compensationService.add(compensation);
			String result = "Compensation saved : " + compensation.getId_comp() + " " + compensation.getMonth() + " "
					+ compensation.getSalary() + " " + compensation.getBonus() + " " + compensation.getCommission();
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
	public Response updateEmployee(Compensation compensation) {

		try {
			compensationService.upsert(compensation);
			String result = "Compensation updated : " + compensation.getId_comp() + " " + compensation.getMonth() + " "
					+ compensation.getSalary() + " " + compensation.getBonus() + " " + compensation.getCommission();
			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			throw new WebApplicationException(e);
		}

	}

	@DELETE
	@Path("{id_comp}")
	public Response deleteCompensation(@PathParam("id_comp") String id_comp) {

		try {
			Long longId_comp = Long.parseLong(id_comp);
			compensationService.delete(longId_comp);
			String result = "Compensation deleted";
			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			throw new WebApplicationException(e);
		}
	}

}
