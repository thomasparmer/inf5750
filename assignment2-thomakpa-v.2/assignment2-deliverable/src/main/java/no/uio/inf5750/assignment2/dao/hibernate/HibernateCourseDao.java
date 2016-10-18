package no.uio.inf5750.assignment2.dao.hibernate;

import java.util.Collection;
import java.util.List;
import no.uio.inf5750.assignment2.dao.CourseDAO;
import no.uio.inf5750.assignment2.model.Course;
import no.uio.inf5750.assignment2.model.Student;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class HibernateCourseDao implements CourseDAO {

	static Logger logger = Logger.getLogger(HibernateCourseDao.class);

	@Autowired
	private SessionFactory sessionFactory;


	@Override
	public int saveCourse(Course course) {
		return (Integer) sessionFactory.getCurrentSession().save(course);
	}

	@Override
	public Course getCourse(int id) {
		return (Course) sessionFactory.getCurrentSession().get(Course.class, id);
	}

	@Override
	public Course getCourseByCourseCode(String courseCode) {
		//return (Course) sessionFactory.getCurrentSession().get(Course.class, courseCode);
		return (Course) sessionFactory.getCurrentSession().createCriteria(Course.class).add(Restrictions.eq("courseCode", courseCode)).uniqueResult();
		
	}

	/*
	@Override
	public Course getCourseByCourseCode(String courseCode) {
		//create a query to find a course by his CourseCode
		Query query= sessionFactory.getCurrentSession().
	            createQuery("from Course where courseCode=:courseCode");
	    query.setParameter("courseCode", courseCode);
	    //collect result 
	    Course course = (Course) query.uniqueResult();
	    return course;
	}*/
	
	@Override
	public Course getCourseByName(String name) {
		//return (Course) sessionFactory.getCurrentSession().get(Course.class, name);
		return (Course) sessionFactory.getCurrentSession().createCriteria(Course.class).add(Restrictions.eq("name", name)).uniqueResult();
	}

	@Override
	public Collection<Course> getAllCourses() {
		return sessionFactory.getCurrentSession().createCriteria(Course.class).list();
	}

	@Override
	public void delCourse(Course course) {
		sessionFactory.getCurrentSession().delete(course);
	}

}