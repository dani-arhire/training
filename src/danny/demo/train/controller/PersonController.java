package danny.demo.train.controller;

import java.util.List;

import javax.inject.Inject;
import javax.websocket.server.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import danny.demo.train.dto.PersonDto;
import danny.demo.train.service.PersonService;

@Path("/person")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PersonController {

	@Inject
	private PersonService personService;
	
	@GET
	public Response getAllPersons() {
		List<PersonDto> allPersons = personService.getAllPersons();
		return Response.status(Status.OK).entity(allPersons).build();
	}
	
	@POST
	public String addPerson(PersonDto personDto) {
		personService.addPerson(personDto);
		return null;
	}
	
	@PUT
	public String editPerson(PersonDto personDto) {
		personService.editPerson(personDto);
		return null;
	}
	
	@DELETE
	public String removePerson(@PathParam("id") int id) {
		personService.deletePerson(id);
		return null;
	}
}