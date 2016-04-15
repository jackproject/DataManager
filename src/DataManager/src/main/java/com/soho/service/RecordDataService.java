package com.soho.service;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soho.model.PickItem;
import com.soho.model.RecordData;

@Service
public class RecordDataService {
	
	@Autowired
    private SessionFactory sessionFactory;

	public List<RecordData> findAll(){
        String hsql = "from t_data";
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hsql);
        
        return query.list();
    }


	public List<RecordData> findAllByPick(List<PickItem> listPickItem){
        String hsql = "from t_data";
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hsql);
        
        hsql += " where ";
        
        for (int i = 0; i < listPickItem.size(); i++) {
        	PickItem pickItem = listPickItem.get(i);

        	hsql += "(";        	
        	hsql += "item_id=";
        	hsql += pickItem.getItem_id();

        	hsql += " and ";
        	hsql += "content";
        	switch (pickItem.getChoice()) {
				case 0:
		        	hsql += ">";
					break;
				case 1:
		        	hsql += "=";
					break;
				case 2:
		        	hsql += "<";
					break;
				default:
					break;
			}
        	hsql += pickItem.getPick_value();

        	hsql += ")";
        	
        	if (i < listPickItem.size()-1) {
            	hsql += " or ";
        	}
        }
        System.out.println(hsql);
        
        return query.list();
    }
	
    public void update(Object entity){
        Session session = sessionFactory.getCurrentSession();

        session.saveOrUpdate(entity);

        session.flush();
    }

    public void deleteAllByItemId(Integer itemId){
        String hsql = "delete from t_data as t where t.item_id=?";
        
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
    
    public RecordData insert(Object entity){
        Session session = sessionFactory.getCurrentSession();

        session.save(entity);
        
        RecordData objNew = (RecordData) entity;
        
        session.flush();
        
        return objNew;
    }

    public Integer findNewRecordId() {

        String hsql = "select max(t.data_id) from t_data as t";
        
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hsql);
        
        Integer maxId = (Integer) query.uniqueResult();
        
        if (maxId == null) {
        	maxId = 0;
        }
        
        Integer newId = maxId + 1;
        
        return newId;
    }


}
