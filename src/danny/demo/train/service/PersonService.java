package danny.demo.train.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import danny.demo.train.dao.PersonDao;
import danny.demo.train.dto.PersonDto;
import danny.demo.train.entity.Person;

@Stateless
public class PersonService {

	@Inject
	private PersonDao personDao;

	public List<PersonDto> getAllPersons() {
		List<Person> allPersonsList = personDao.getPersons();
		List<PersonDto> result = new ArrayList<>();
		for (Person p : allPersonsList) {
			PersonDto dto = generateDtoFromPerson(p);
			result.add(dto);
		}
		return result;
	}

	public void addPerson(PersonDto personDto) {
		Person p = generatePersonFromDto(personDto);
		personDao.addPerson(p);
	}
	
	public void editPerson(PersonDto personDto) {
		Person p = generatePersonFromDto(personDto);
		personDao.updatePerson(p);
	}
	
	public void deletePerson(int id) {
		personDao.deletePerson(id);
	}
	
	private Person generatePersonFromDto(PersonDto personDto) {
		Person p = new Person();
		p.setId(personDto.getId());
		p.setName(personDto.getName());
		p.setBirthDate(new Date(personDto.getBirthDate()));
		return p;
	}
	
	private PersonDto generateDtoFromPerson(Person p) {
		PersonDto dto = new PersonDto();
		dto.setId(p.getId());
		dto.setName(p.getName());
		if (p.getBirthDate() != null) {
			dto.setBirthDate(p.getBirthDate().getTime());
		}
		return dto;
	}
}
