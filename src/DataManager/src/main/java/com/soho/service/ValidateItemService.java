package com.soho.service;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soho.model.ValidateItem;


@Service
public class ValidateItemService {
	
	@Autowired
    private SessionFactory sessionFactory;
    
	public List<ValidateItem> findAll(){
        String hsql = "from t_validate";
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hsql);
        
        return query.list();
    }

	public List<ValidateItem> findByItemId(Integer itemId){
        String hsql = "from t_validate as t where t.item_id=?";
        
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hsql);
        
        query.setInteger(0, itemId);

        return query.list();
    }

    
	public ValidateItem findById(Integer id){
        Session session = sessionFactory.getCurrentSession();

        ValidateItem validateItem = (ValidateItem) session.get(ValidateItem.class, id);
        
        return validateItem;
    }


    public void updateAllByItemId(List<ValidateItem> list){
    	
    	if (list.size() <= 0) {
    		return ;
    	}
    	
    	Integer itemId = list.get(0).getItem_id();
    	
    	// 1. 清空所有数据
    	deleteAllByItemId(itemId);
        
        // 2. 插入所有新的数据
        for (ValidateItem item : list) {
        	insert(item);
        }
    }
    

    public void deleteAllByItemId(Integer itemId){
        String hsql = "delete from t_validate as t where t.item_id=?";
        
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hsql);

        query.setInteger(0, itemId);
        
        query.executeUpdate();
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

    public ValidateItem insert(Object entity){
        Session session = sessionFactory.getCurrentSession();

        session.save(entity);
        
        // 设置 id 的策略为 strategy=GenerationType.IDENTITY 时
        // 当插入了数据之后，新的 id 即会出现在 entity 中
        ValidateItem objNew = (ValidateItem) entity;
        
        session.flush();
        
        return objNew;
    }

}
