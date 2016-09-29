package no.uio.inf5750.assignment2.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import no.uio.inf5750.assignment2.service.StudentSystem;
import no.uio.inf5750.assignment2.dao.CourseDAO;
import no.uio.inf5750.assignment2.model.Course;
import no.uio.inf5750.assignment2.model.Student;
import no.uio.inf5750.assignment2.dao.StudentDAO;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("DefaultStudentSystem")
@Transactional
public class DefaultStudentSystem implements StudentSystem {

	@Autowired
	CourseDAO courseDao;

	@Autowired
	StudentDAO studentDao;
	

	static Logger logger = Logger.getLogger(DefaultStudentSystem.class);
	
	@Override
	public int addCourse(String courseCode, String name) {
		return (Integer) courseDao.saveCourse(new Course(courseCode, name));
	}

	@Override
	public void updateCourse(int courseId, String courseCode, String name) {
		Course course = courseDao.getCourse(courseId);
		course.setCourseCode(courseCode);
		course.setName(name);
		courseDao.saveCourse(course);
	}

	@Override
	public Course getCourse(int courseId) {
		return (Course) courseDao.getCourse(courseId);
	}

	@Override
	public Course getCourseByCourseCode(String courseCode) {
		return (Course) courseDao.getCourseByCourseCode(courseCode);
	}

	@Override
	public Course getCourseByName(String name) {
		return (Course) courseDao.getCourseByName(name);
	}

	@Override
	public Collection<Course> getAllCourses() {
		return courseDao.getAllCourses();
	}

	@Override
	public void delCourse(int courseId) {
		courseDao.delCourse((Course) courseDao.getCourse(courseId));
	}

	@Override
	public void addAttendantToCourse(int courseId, int studentId) {
		Course co = courseDao.getCourse(courseId);
		Student st = studentDao.getStudent(studentId);
		Set<Course> courses = st.getCourses();
		Set<Student> students = co.getAttendants();
		courses.add(co);
		students.add(st);
		co.setAttendants(students);
		st.setCourses(courses);
		courseDao.saveCourse(co);
		studentDao.saveStudent(st);
	}

	@Override
	public void removeAttendantFromCourse(int courseId, int studentId) {
		Course co = courseDao.getCourse(courseId);
		Student st = studentDao.getStudent(studentId);
		Set<Course> courses = st.getCourses();
		Set<Student> students = co.getAttendants();
		courses.remove(co);
		students.remove(st);
		co.setAttendants(students);
		st.setCourses(courses);
		courseDao.saveCourse(co);
		studentDao.saveStudent(st);
	}

	@Override
	public int addStudent(String name) {
		return (Integer) studentDao.saveStudent(new Student(name));
	}

	@Override
	public void updateStudent(int studentId, String name) {
		Student student = studentDao.getStudent(studentId);
		student.setName(name);
		studentDao.saveStudent(student);
	}

	@Override
	public Student getStudent(int studentId) {
		return (Student) studentDao.getStudent(studentId);
	}

	@Override
	public Student getStudentByName(String name) {
		return (Student) studentDao.getStudentByName(name);
	}

	@Override
	public Collection<Student> getAllStudents() {
		return studentDao.getAllStudents();
	}

	@Override
	public void delStudent(int studentId) {
		Student s = studentDao.getStudent(studentId);
		for (Course course : courseDao.getAllCourses()) {
			course.getAttendants().remove(s);
		}
		
		studentDao.delStudent(s);
	}
	
	@Override
	public void setStudentLocation(int studentId, String latitude, String longitude) {
		Student student = getStudent(studentId);
		student.setLatitude(latitude);
		student.setLongitude(longitude);
	}
}