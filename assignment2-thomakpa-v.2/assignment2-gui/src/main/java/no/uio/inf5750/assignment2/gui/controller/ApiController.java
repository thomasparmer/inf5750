package no.uio.inf5750.assignment2.gui.controller;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import no.uio.inf5750.assignment2.model.Course;
import no.uio.inf5750.assignment2.model.Student;
import no.uio.inf5750.assignment2.service.StudentSystem;

@Controller
@RequestMapping("/api")
public class ApiController {
	
	
	static Logger logger = Logger.getLogger(ApiController.class);

	@Autowired
	private StudentSystem studentSystem;

	
	@RequestMapping(value="/student/{user}", method = RequestMethod.GET)
	@ResponseBody
	public Student getStudentByUsername(@PathVariable String user,
	       HttpServletRequest request,
	       HttpServletResponse response) {
	     
	    Student student = studentSystem.getStudentByName(user);
	    return student;
	}
	
	/*
	@RequestMapping(value="/student", method = RequestMethod.GET)
	public String getStudents(ModelMap model) {
		populateModel(model);
	     
		return "api/student";
	}*/
	
	@RequestMapping(value = "/student", method = RequestMethod.GET)
	@ResponseBody
	public Collection<Student> student(HttpServletRequest request, HttpServletResponse response ,ModelMap model) {
		return studentSystem.getAllStudents();
	}

	
	@RequestMapping(value = "/course", method = RequestMethod.GET)
	public Collection<Course> getCourses(ModelMap model) {
		return studentSystem.getAllCourses();
		
	}
	
	private ModelMap populateModel(HttpServletRequest request, HttpServletResponse response ,ModelMap model) {
		Collection<Student> students = studentSystem.getAllStudents();
		model.addAttribute("students", students);
		Collection<Course> courses = studentSystem.getAllCourses();
		model.addAttribute("courses", courses);
	
		return model;
	}
	
	@RequestMapping(value = "/student/{student_id}/location", method = RequestMethod.GET)
	@ResponseBody
	public Collection<Student> setLocation(@PathVariable int student_id,HttpServletRequest request, HttpServletResponse response ,ModelMap model) {

		String latitude = request.getParameter("latitude");
		String longitude = request.getParameter("longitude");
		studentSystem.setStudentLocation(student_id, latitude, longitude);

	return studentSystem.getAllStudents();
}

}