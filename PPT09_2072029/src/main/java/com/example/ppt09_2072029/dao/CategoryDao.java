package com.example.ppt09_2072029.dao;

import com.example.ppt09_2072029.model.Category;
import com.example.ppt09_2072029.util.HiberUtility;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class CategoryDao implements DaoInterface<Category>{

    @Override
    public List<Category> getData() {
        List<Category> clist;

        SessionFactory sessionFactory = HiberUtility.getSessionFactory();
        Session session = sessionFactory.openSession();

        CriteriaBuilder bob = session.getCriteriaBuilder();
        CriteriaQuery query = bob.createQuery(Category.class);
        query.from(Category.class);

        clist = session.createQuery(query).getResultList();

        session.close();
        return clist;
    }

    @Override
    public void addData(Category data) {
        SessionFactory sessionFactory = HiberUtility.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(data);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
        session.close();
    }

    @Override
    public void deleteData(Category data) {
        SessionFactory sessionFactory = HiberUtility.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.delete(data);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
        session.close();
    }

    @Override
    public void updateData(Category data) {
        SessionFactory sessionFactory = HiberUtility.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.update(data);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
        session.close();
    }
}
