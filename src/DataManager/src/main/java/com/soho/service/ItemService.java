package com.soho.service;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soho.model.Item;

@Service
public class ItemService {

	@Autowired
    private SessionFactory sessionFactory;

    public List<Item> findAll(){
        String hsql = "from t_item";
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hsql);
        
        return query.list();
    }

    public void update(Object entity){
        Session session = sessionFactory.getCurrentSession();
        
        Transaction transaction = session.beginTransaction();
        transaction.begin();
        
        session.update(entity);
        
        transaction.commit();
    }

    public void delete(Object entity){
        Session session = sessionFactory.getCurrentSession();
        
        session.delete(entity);

        session.flush();
    }

    public Item insert(Object entity){
        Session session = sessionFactory.getCurrentSession();



//      Transaction transaction = session.beginTransaction();
//        Transaction transaction = session.getTransaction();
//        transaction.begin();
        

        System.out.println("start save ..");
        
        Item item =  (Item) session.save(entity);
        
        System.out.println("flush..");
        
        session.flush();

//        transaction.commit();
        
        
        return item;
    }
}
