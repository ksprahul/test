/**
 *
 */
package com.spankr.tutorial.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * @author Lee_Vettleson
 *
 */
public class TestSampleDAO {

    private SampleDAO dao = null;

    /**
     * Gets called before every test method
     *
     * @throws SQLException
     */
    @Before
    public void setup() throws SQLException {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        dao = context.getBean(SampleDAO.class);
        dao.createTable();
    }

    /**
     * Gets called after every test method
     */
    @After
    public void tearDown() {
        dao.dropTable();
    }

    @Test
    public void addAndRetrievePeople() {
        SamplePerson person = new SamplePerson();
        person.setFirstName("Bob");
        person.setLastName("Haskins");
        person.setAge(29);
        assertTrue("Unable to insert Bob", dao.insertPerson(person));

        person = new SamplePerson();
        person.setFirstName("Mary");
        person.setLastName("Thompson");
        person.setAge(45);
        assertTrue("Unable to insert Mary", dao.insertPerson(person));

        List<SamplePerson> people = dao.getAllPeople();
        assertEquals("Wrong number of people", 2, people.size());

        SamplePerson bob = people.get(0);
        SamplePerson mary = people.get(1);

        assertEquals("Not Bob", "Bob", bob.getFirstName());
        assertEquals("Not Mary", "Mary", mary.getFirstName());

        assertEquals("Person mismatch", bob, dao.getPersonById(bob.getId()));
        assertEquals("Person mismatch", mary, dao.getPersonById(mary.getId()));
    }

    @Test
    public void getMissingPersons() {
        List<SamplePerson> people = dao.getAllPeople();
        assertEquals("Wrong number of people", 0, people.size());
    }
}
