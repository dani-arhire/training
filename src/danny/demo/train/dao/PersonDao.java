package danny.demo.train.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import danny.demo.train.entity.Person;

public class PersonDao {

	@PersistenceContext(unitName = "person-unit")
	private EntityManager entityManager;
	
	public void addPerson(Person person) {
		entityManager.persist(person);
	}
	
	public Person updatePerson(Person person) {
		return entityManager.merge(person);
	}
	
    public void deletePerson(int id) {
		Person p = entityManager.find(Person.class, id);
		entityManager.remove(p);
    }

    @SuppressWarnings("unchecked")
	public List<Person> getPersons() {
        Query query = entityManager.createQuery("SELECT p from Person as p");
        return query.getResultList();
    }
    
    public Person getPerson(int id) {
    	return entityManager.find(Person.class, id);
    }
}
