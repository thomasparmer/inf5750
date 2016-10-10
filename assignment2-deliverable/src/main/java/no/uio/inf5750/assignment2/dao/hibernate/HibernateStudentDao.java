package no.uio.inf5750.assignment2.dao.hibernate;

import java.util.Collection;
import java.util.List;
import no.uio.inf5750.assignment2.dao.StudentDAO;
import no.uio.inf5750.assignment2.model.Course;
import no.uio.inf5750.assignment2.model.Student;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class HibernateStudentDao implements StudentDAO {

	static Logger logger = Logger.getLogger(HibernateStudentDao.class);
	
	@Autowired
	private SessionFactory sessionFactory;


	@Override
	public int saveStudent(Student student) {
		return (Integer) sessionFactory.getCurrentSession().save(student);
	}

	@Override
	public Student getStudent(int id) {
		return (Student) sessionFactory.getCurrentSession().get(Student.class, id);
	}

	@Override
	public Student getStudentByName(String name) {
		return (Student) sessionFactory.getCurrentSession().createCriteria(Student.class).add(Restrictions.eq("name", name)).uniqueResult();
		//return (Course) sessionFactory.getCurrentSession().createCriteria(Course.class).add(Restrictions.eq("courseCode", courseCode)).uniqueResult();		
	}

	@Override
	public Collection<Student> getAllStudents() {
		return sessionFactory.getCurrentSession().createCriteria(Student.class).list();
	}

	@Override
	public void delStudent(Student student) {
		sessionFactory.getCurrentSession().delete(student);
	}
}