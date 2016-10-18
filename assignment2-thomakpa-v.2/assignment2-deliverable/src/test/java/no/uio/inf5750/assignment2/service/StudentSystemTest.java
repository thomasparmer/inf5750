package no.uio.inf5750.assignment2.service;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collection;
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
	public void updateStudentTest() {
		int id = studentSystem.addStudent("thomas");
		Student student = studentDao.getStudent(id);
		studentSystem.updateStudent(id, "per");
		assertNotEquals(student.getName(), "thomas");
		assertEquals(student.getName(), "per");
	}

	
	
	@Test
	public void getCourseByNameTest() {
		int id = studentSystem.addCourse("inf5750", "opensourcedevelopment");
		Course course = studentSystem.getCourseByName("opensourcedevelopment");
		assertEquals("opensourcedevelopment", course.getName());
	}

	
	@Test
	public void getStudentByName() {
		int id = studentSystem.addStudent("thomas");
		Student student = studentSystem.getStudentByName("thomas");
		assertNotNull(student);
		assertEquals(id, student.getId());
	}
	
	@Test
	public void studentSystemTest() {

		int id = studentSystem.addCourse("inf5750", "opensourcedevelopment");
		Course course = studentSystem.getCourse(id);
		assertNotNull(course);
		assertEquals(id, course.getId());
	}

	@Test
	public void studentTest() {
		int id = studentSystem.addStudent("thomas");
		Student student = studentSystem.getStudent(id);
		assertNotNull(student);
		assertEquals(id, student.getId());

	}

	@Test
	public void SetStudentLocationTest() {

		int id = studentSystem.addStudent("Thomas");
		studentSystem.setStudentLocation(id, "0", "1");
		Student student = studentSystem.getStudent(id);

		assertNotNull(student);
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
	public void GetStudentTest() {
		String name = "Thomas";
		int id = studentSystem.addStudent("Thomas");
		assertNotNull(id);
	}

	@Test
	public void deleteCourseTest() {
		int id = studentSystem.addCourse("INF5750", "open");
		studentSystem.delCourse(id);
		Course courseTest = studentSystem.getCourse(id);
		assertNull(courseTest);
	}

	@Test
    public void deleteStudentTest() {
    	int id = studentSystem.addStudent("Thomas");
        studentSystem.delStudent(id);
        Student student = studentSystem.getStudent(id);
        assertNull(student);
    }
	
	@Test
	public void getAllStudents() {
		int id = studentSystem.addStudent("Thomas");
		int id2 = studentSystem.addStudent("Kaspar");
		Collection<Student> students = studentDao.getAllStudents();
		assertNotNull(students);
	}
	
	@Test
	public void getAllCourses() {
		int id = studentSystem.addCourse("INF5750", "open");
		int id2 = studentSystem.addCourse("INF1000", "Object");
		Collection<Course> courses = courseDao.getAllCourses();
		assertNotNull(courses);
	}

	@Test
	public void addRemoveAttendantToCourseTest(){
		int courseId = studentSystem.addCourse("INF5750", "open");
		int studentId = studentSystem.addStudent("Thomas");
		studentSystem.addAttendantToCourse(courseId, studentId);
		Course course = studentSystem.getCourse(courseId);
		Student student = studentSystem.getStudent(studentId);
		assertTrue(student.getCourses().contains(course));
		studentSystem.removeAttendantFromCourse(courseId, studentId);
		assertFalse(student.getCourses().contains(course));
	}
}


