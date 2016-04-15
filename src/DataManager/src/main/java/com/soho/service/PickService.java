package com.soho.service;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soho.model.Pick;


@Service
public class PickService {

	@Autowired
    private SessionFactory sessionFactory;
	
    public List<Pick> findAll(){
        String hsql = "from t_pick as t";
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hsql);
        
        return query.list();
    }

    public void update(Object entity){
        Session session = sessionFactory.getCurrentSession();
        
        session.update(entity);

        session.flush();
    }

    public void delete(Object entity){
        Session session = sessionFactory.getCurrentSession();
        
        session.delete(entity);

        session.flush();
    }

    public Pick insert(Object entity){
        Session session = sessionFactory.getCurrentSession();

        session.save(entity);
        
        // 设置 id 的策略为 strategy=GenerationType.IDENTITY 时
        // 当插入了数据之后，新的 id 即会出现在 entity 中
        Pick pick = (Pick) entity;
        
        session.flush();
        
        return pick;
    }
}
