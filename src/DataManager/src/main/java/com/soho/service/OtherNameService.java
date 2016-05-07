package com.soho.service;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soho.model.Item;
import com.soho.model.OtherName;

@Service
public class OtherNameService {
	
	@Autowired
    private SessionFactory sessionFactory;
    
	public List<OtherName> findAll(){
        String hsql = "from t_othername";
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hsql);
        
        return query.list();
    }
    
	public List<OtherName> findByItemId(Integer itemId){
        String hsql = "from t_othername as t where t.item_id=?";
        
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hsql);
        
        query.setInteger(0, itemId);

        return query.list();
    }

    // 返回第一个匹配的别名值
	public OtherName findByItemName(String itemName){
        String hsql = "from t_othername as t where t.name=?";
        
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hsql);

        query.setString(0, itemName);
        
        System.out.println("itemName:" + itemName);
        
        
        List<OtherName> list = query.list();
        
        OtherName oth = null;
        
        if (list.size() > 0) {
        	oth = list.get(0);
        }
        
        return oth;
    }
    
	public OtherName findById(Integer id){
        Session session = sessionFactory.getCurrentSession();

        OtherName otherName = (OtherName) session.get(OtherName.class, id);
        
        return otherName;
    }

    
    public void update(Object entity){
        Session session = sessionFactory.getCurrentSession();
        
        session.update(entity);

        session.flush();
    }


    public void updateAllByItemId(Integer itemId, List<OtherName> list){
    	
    	// 1. 清空所有数据
    	deleteAllByItemId(itemId);

    	if (list.size() <= 0) {
    		return ;
    	}
    	
        // 2. 插入所有新的数据
        for (OtherName item : list) {
        	insert(item);
        }
    }
    

    public void deleteAllByItemId(Integer itemId){
        String hsql = "delete from t_othername as t where t.item_id=?";
        
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hsql);

        query.setInteger(0, itemId);
        
        query.executeUpdate();
    }
    
    public void delete(Object entity){
        Session session = sessionFactory.getCurrentSession();
        
        session.delete(entity);
        
        session.flush();
    }
    public OtherName insert(Object entity){
        Session session = sessionFactory.getCurrentSession();

        session.save(entity);
        
        // 设置 id 的策略为 strategy=GenerationType.IDENTITY 时
        // 当插入了数据之后，新的 id 即会出现在 entity 中
        OtherName objNew = (OtherName) entity;
        
        session.flush();
        
        return objNew;
    }

}
