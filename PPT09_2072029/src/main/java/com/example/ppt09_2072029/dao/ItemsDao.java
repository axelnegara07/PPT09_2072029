package com.example.ppt09_2072029.dao;

import com.example.ppt09_2072029.model.Items;
import com.example.ppt09_2072029.util.HiberUtility;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class ItemsDao implements DaoInterface<Items>{

    @Override
    public List<Items> getData() {
        List<Items> ilist;

        SessionFactory sessionFactory = HiberUtility.getSessionFactory();
        Session session = sessionFactory.openSession();

        CriteriaBuilder bob = session.getCriteriaBuilder();
        CriteriaQuery query = bob.createQuery(Items.class);
        query.from(Items.class);

        ilist = session.createQuery(query).getResultList();

        session.close();
        return ilist;
    }

    @Override
    public void addData(Items data) {
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
    public void deleteData(Items data) {
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
    public void updateData(Items data) {
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