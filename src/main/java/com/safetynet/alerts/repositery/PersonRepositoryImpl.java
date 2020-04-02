package com.safetynet.alerts.repositery;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.aspectj.weaver.ast.And;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.safetynet.alerts.model.Person;

@Transactional
public class PersonRepositoryImpl implements PersonRepository {

    @PersistenceContext()
    private EntityManager em;

    public PersonRepositoryImpl(EntityManager pEm) {
        em = pEm;
    }

    @Override
    public List<Person> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Person savePerson(Person pPerson) {
        if (pPerson.getId() == null) {
            em.persist(pPerson);
        } else {
            em.merge(pPerson);
        }
        return pPerson;
    }

    @Override
    public <S extends Person> List<S> saveAll(Iterable<S> entities) {
        List<S> result = new ArrayList<S>();
        if (entities == null) {
            return result;
        }
        for (S entity : entities) {
            result.add(save(entity));
        }
        return result;
    }

    @Override
    public Person updateByLastNameAndFirstName(String pLastName,
            String pFirstName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteByLastNameAndFirstName(String pLastName,
            String pFirstName) {
        // TODO Auto-generated method stub

    }

    @Override
    public List<Person> findAll(Sort sort) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Person> findAllById(Iterable<Long> ids) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void flush() {
        // TODO Auto-generated method stub

    }

    @Override
    public <S extends Person> S saveAndFlush(S entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<Person> entities) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteAllInBatch() {
        // TODO Auto-generated method stub

    }

    @Override
    public Person getOne(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <S extends Person> List<S> findAll(Example<S> example) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <S extends Person> List<S> findAll(Example<S> example, Sort sort) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Page<Person> findAll(Pageable pageable) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <S extends Person> S save(S pPerson) {
        if (pPerson.getId() == null) {
            em.persist(pPerson);
        } else {
            em.merge(pPerson);
        }
        return pPerson;
    }

    @Override
    public Optional<Person> findById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean existsById(Long id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public long count() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void deleteById(Long id) {
        // TODO Auto-generated method stub

    }

    @Override
    public void delete(Person entity) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteAll(Iterable<? extends Person> entities) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteAll() {
        // TODO Auto-generated method stub

    }

    @Override
    public <S extends Person> Optional<S> findOne(Example<S> example) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <S extends Person> Page<S> findAll(Example<S> example,
            Pageable pageable) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <S extends Person> long count(Example<S> example) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public <S extends Person> boolean exists(Example<S> example) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Person findByLastNameAndFirstName(String pLastName,
            String pFirstName) {
        TypedQuery<Person> q = em.createQuery(
                "SELECT p FROM Person p WHERE p.lastName = ?1 And p.firstName = ?2",
                Person.class);
        q.setParameter(1, pLastName);
        q.setParameter(2, pFirstName);
System.out.println(pLastName);
System.out.println(pFirstName);
        return q.getSingleResult();
    }

}
