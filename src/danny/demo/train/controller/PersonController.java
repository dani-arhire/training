package danny.demo.train.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import danny.demo.train.dto.PersonDto;

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
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String addPerson(PersonDto p) {
		return "success";
	}
	
	@DELETE
	@Path("/delete")
	@Produces(MediaType.TEXT_PLAIN)
	public String removePerson(@FormParam("id") int id) {
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