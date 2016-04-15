package com.soho.service;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soho.model.OtherName;
import com.soho.model.PickItem;

@Service
public class PickItemService {
	
	@Autowired
    private SessionFactory sessionFactory;
	
    public List<PickItem> findAll(){
        String hsql = "from t_pick_item as t";
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hsql);
        
        return query.list();
    }

	public List<PickItem> findByPickId(Integer pickId) {
		
        String hsql = "from t_pick_item as t where t.pick_id=?";
		
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hsql);
        
        query.setInteger(0, pickId);

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

    public void deleteAllByPickId(Integer pickId){
        String hsql = "delete from t_pick_item as t where t.pick_id=?";
        
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hsql);

        query.setInteger(0, pickId);
        
        query.executeUpdate();
    }
    
    public PickItem insert(Object entity){
        Session session = sessionFactory.getCurrentSession();

        session.save(entity);
        
        // 设置 id 的策略为 strategy=GenerationType.IDENTITY 时
        // 当插入了数据之后，新的 id 即会出现在 entity 中
        PickItem pickItem = (PickItem) entity;
        
        session.flush();
        
        return pickItem;
    }

}
