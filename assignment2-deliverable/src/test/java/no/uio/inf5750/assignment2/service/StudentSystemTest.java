package no.uio.inf5750.assignment2.service;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

import no.uio.inf5750.assignment2.dao.CourseDAO;
import no.uio.inf5750.assignment2.dao.StudentDAO;
import no.uio.inf5750.assignment2.model.Course;
import no.uio.inf5750.assignment2.model.Student;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/META-INF/assignment2/beans.xml" })
@Transactional
public class StudentSystemTest {
	static Logger logger = Logger.getLogger(StudentSystemTest.class);
	@Autowired
	StudentSystem studentSystem;

	@Autowired
	private StudentDAO studentDao;

	@Autowired
	private CourseDAO courseDao;


	@Test
	public void addCourseANDgetCourseTest() {
		int id = studentSystem.addCourse("inf5750", "opensourcedevelopment");
		Course course = studentSystem.getCourse(id);
		assertNotNull(course);
		assertEquals(id, course.getId());
	}
	
	
	@Test
	public void getCourseByCourseCodeTest() {	
		int id = studentSystem.addCourse("inf5750", "opensourcedevelopment");
		Course course = studentSystem.getCourseByCourseCode("inf5750");
		assertEquals(id, course.getId());
	}
	
	
	@Test
	public void updateCourseTest() {
		int id = studentSystem.addCourse("inf5750", "opensourcedevelopment");
		Course course = courseDao.getCourse(id);
		studentSystem.updateCourse(id, "inf5063", "hetrogenious");
		assertEquals(course, studentSystem.getCourseByCourseCode("inf5063"));
	}

	
	@Test
	public void getCourseByNameTest() {
		Course course = studentSystem.getCourseByName("opensourcedevelopment");
		assertEquals("opensourcedevelopment", course.getName());
	}

	
	@Test
	public void studentSystemTest() {

		int id = studentSystem.addCourse("inf5750", "opensourcedevelopment");
		Course course = studentSystem.getCourse(id);
		assertNotNull(course);
		assertEquals(id, course.getId());
		course = studentSystem.getCourseByCourseCode("inf5750");
		assertEquals(id, course.getCourseCode());
		course = studentSystem.getCourseByName("opensourcedevelopment");
		assertEquals("opensourcedevelopment", course.getName());
	}

	
	@Test
	public void studentTest() {
		int id = studentSystem.addStudent("thomas");
		Student student = studentSystem.getStudent(id);
		assertNotNull(student);
		assertEquals(id, student.getId());
		student = studentSystem.getStudentByName("thomas");
		assertEquals("thomas", student.getName());
	}
	
	@Test
	public void SetStudentLocationTest() {

	int id = studentSystem.addStudent("Thomas");
	studentSystem.setStudentLocation(id, "0", "1");
	Student student = studentSystem.getStudent(id);

	assertNotNull( student );
	assertEquals("0", student.getLatitude());
	assertEquals("1", student.getLongitude());

	}


	@Test
	public void addStudentTest() {
		int id = studentSystem.addStudent("Thomas");
		Student student = studentSystem.getStudent(id);
		assertNotNull(student);
		assertEquals(id, student.getId());
	}
	
	@Test
	public void GetStudentTest()
	{
	String name = "Thomas";
	int id = studentSystem.addStudent("Thomas");
	assertNotNull(id);
	}

}


