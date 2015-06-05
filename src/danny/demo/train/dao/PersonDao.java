package danny.demo.train.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import danny.demo.train.entity.Person;

@Stateless
@Transactional(value = TxType.REQUIRED)
public class PersonDao {

	@PersistenceContext(unitName = "person-unit")
	private EntityManager entityManager;
	
	@Transactional(value = TxType.MANDATORY)
	public void addPerson(Person person) throws Exception {
		entityManager.persist(person);
	}
	
	@Transactional(value = TxType.REQUIRED)
	public void updatePerson(Person person) throws Exception {
		entityManager.merge(person);
	}
	
	@Transactional(value = TxType.MANDATORY)
    public void deletePerson(int id) throws Exception {
		Person p = entityManager.find(Person.class, id);
		entityManager.remove(p);
    }

	@Transactional(value = TxType.REQUIRED)
    @SuppressWarnings("unchecked")
	public List<Person> getPersons() throws Exception {
        Query query = entityManager.createQuery("SELECT p from Person as p");
        return query.getResultList();
    }
    
	@Transactional(value = TxType.REQUIRED)
    public Person getPerson(int id) {
    	return entityManager.find(Person.class, id);
    }
}
