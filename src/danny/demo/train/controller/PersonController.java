package danny.demo.train.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import danny.demo.train.dto.PersonDto;
import danny.demo.train.service.PersonService;

@Path("/person")
@Consumes(MediaType.TEXT_PLAIN)
public class PersonController {

	@EJB
	private PersonService personService;
	
	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllPersons() {
		try {
			Response response = new Response();
			response.records = personService.getAllPersons();
			return response;
		} catch (Exception e) {
			return null;
		}
	}
	
	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String addPerson(PersonDto personDto) {
		try {
			personService.addPerson(personDto);
		} catch (Exception e) {
			return "error";
		}
		return "success";
	}
	
	@POST
	@Path("/edit")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String editPerson(PersonDto personDto) {
		try {
			personService.editPerson(personDto);
		} catch (Exception e) {
			return "error";
		}
		return "success";
	}
	
	@DELETE
	@Path("/delete")
	@Produces(MediaType.TEXT_PLAIN)
	public String removePerson(@QueryParam("id") int id) {
		try {
			personService.deletePerson(id);
		} catch (Exception e) {
			return "error";
		}
		return "success";
	}
	
	@SuppressWarnings("unused")
	private class Response {
		public List<PersonDto> records;
	}
}