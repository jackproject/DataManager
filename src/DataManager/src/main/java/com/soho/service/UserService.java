package com.soho.service;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soho.model.Item;
import com.soho.model.User;

@Service
public class UserService {

	@Autowired
    private SessionFactory sessionFactory;

    public List<User> findAll(){
    	// 按排序字段排序
        String hsql = "from t_user as t";
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hsql);
        
        return query.list();
    }

    public User findUser(String username, String password){
        String hsql = "from t_user as t where t.username=? and t.password=? ";
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hsql);

        query.setString(0, username);
        query.setString(1, password);
        
        return (User) query.uniqueResult();
    }

}
