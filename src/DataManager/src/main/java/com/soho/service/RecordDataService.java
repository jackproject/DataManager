package com.soho.service;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soho.model.Item;
import com.soho.model.PickItem;
import com.soho.model.RecordData;

@Service
public class RecordDataService {
	
	@Autowired
    private SessionFactory sessionFactory;
	
    @Resource
    private ItemService itemService;

	
	public List<RecordData> findAll(){
        String hsql = "from t_data";
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hsql);
        
        return query.list();
    }


	public List<RecordData> findAllByPick(List<PickItem> listPickItem) {
		
		
		StringBuilder sb = new StringBuilder();
		
        sb.append("from t_data where ");
        
        for (int i = 0; i < listPickItem.size(); i++) {
        	PickItem pickItem = listPickItem.get(i);

        	Integer itemId = pickItem.getItem_id();
        	
        	sb.append("(");        	
        	sb.append("item_id=");
        	sb.append(itemId);

        	sb.append(" and ");

        	Integer choice = pickItem.getChoice();
        	
        	
        	Item item = itemService.findItemByItemId(itemId);
        	
        	switch (item.getType()) {
				case 1:
		        	sb.append("CAST(content AS DECIMAL)");
					break;
				case 3:
		        	sb.append("CAST(content AS DateTime)");
					break;
				default:
					
					// 【注】对于字符串等类型不需要考虑 >, < 的情况
					choice = -1;
					
		        	sb.append("content");
					break;
        	}
        	
        	
        	switch (choice) {
				case 0:
		        	sb.append(">");
					break;
				case 1:
		        	sb.append("=");
					break;
				case 2:
		        	sb.append("<");
					break;
				default:
		        	sb.append("=");
					break;
			}

        	String strValue = pickItem.getPick_value();
        	
        	switch (item.getType()) {
				case 0:
		        	sb.append("'%");
		        	sb.append(strValue);		        	
		        	sb.append("%'");
					break;
				case 1:
		        	sb.append("CAST('");
		        	sb.append(strValue);		        	
		        	sb.append("' AS DECIMAL)");
					break;
				case 3:
		        	sb.append("CAST('");
		        	sb.append(strValue);
		        	sb.append("' AS DateTime)");
					break;
				default:
		        	sb.append(strValue);
					break;
        	}

        	sb.append(")");
        	
        	if (i < listPickItem.size()-1) {
            	sb.append(" or ");
        	}
        }
        System.out.println(sb);
//
//        String hsql = "from t_data";

        String hsql = new String(sb);
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hsql);
        
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
