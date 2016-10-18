package no.uio.inf5750.assignment1_thomakpa.dao;



import java.util.List;

import no.uio.inf5750.assignment1_thomakpa.model.Message;

import org.apache.log4j.Logger;

import org.hibernate.HibernateException;

import org.hibernate.SessionFactory;

import org.hibernate.Transaction;

import org.hibernate.Session;

import org.springframework.transaction.annotation.Transactional;

 

 

@Transactional

public class HibernateMessageDao

   implements MessageDao

{

 

    static Logger logger = Logger.getLogger(HibernateMessageDao.class);

    private SessionFactory sessionFactory;

 

    public void setSessionFactory( SessionFactory sessionFactory )

    {

        this.sessionFactory = sessionFactory;

    }

 

    public int save( Message message )

    {

        return (Integer) sessionFactory.getCurrentSession().save( message );

    }


   public Message get( int id )

    {

        return (Message) sessionFactory.getCurrentSession().get( Message.class, id );

    }

 

    public Message getLast()

    {

        Session session = sessionFactory.openSession();

        Transaction tx = null;

        Message message = null;

        try{

           tx = session.beginTransaction();

           @SuppressWarnings("unchecked")

           // This is an HQL query, not an SQL query (HQL is based on SQL, but is not 100% the same)

           List<Message> messages = session.createQuery("FROM Message ORDER by id DESC").list();

           if (!messages.isEmpty()) {

                   message = messages.iterator().next();

           }

           tx.commit();

        }catch (HibernateException e) {

           if (tx!=null) tx.rollback();

           logger.error("DB query failed", e);

        }finally {

           session.close();

        }

        return message;

    }

}