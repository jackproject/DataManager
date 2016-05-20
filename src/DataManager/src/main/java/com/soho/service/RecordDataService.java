package com.soho.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	public List<RecordData> findAll() {
		String hsql = "from t_data as t order by t.data_id";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hsql);

		return query.list();
	}

	public List<RecordData> findAllByPickTest(List<PickItem> listPickItem) {

		List<RecordData> listRecordData = new ArrayList();

		List<RecordData> list = findAll();

		return listRecordData;
	}

	public List findAllByPick(List listRecordData, List<PickItem> listPickItem) {		

		List list = new ArrayList();		

		Integer prevDataId = -1;
		
		for (int i = 0; i < listRecordData.size(); i++) {
			
			Map map = (Map) listRecordData.get(i);

			if (isMapOK(map, listPickItem)) {
					list.add(map);
			}
		}
		
		return list;
	}

	
	public List findAllByPick(List<PickItem> listPickItem) {		

		List list = new ArrayList();		

		List<RecordData> listRecordData = findAll();

		Comparator<RecordData> comparator = new Comparator<RecordData>() {

			@Override
			public int compare(RecordData o1, RecordData o2) {
				return o1.getData_id() - o2.getData_id();
			}
		};

		// 按照 data_id 来排序，确保相同data_id 都被放在一起
		Collections.sort(listRecordData, comparator);

		Integer prevDataId = -1;
		Map map = null;
		for (int i = 0; i < listRecordData.size(); i++) {
			
			RecordData recordData = listRecordData.get(i);

			if (prevDataId != recordData.getData_id()) {
				
				if (prevDataId != -1 && isMapOK(map, listPickItem)) {
					list.add(map);
				}
				
				prevDataId = recordData.getData_id();
				
				String strDataId = "" + recordData.getData_id();
				
				map = new HashMap();
				map.put("data_id", strDataId);
			}
			
			
			String strItemId = "" + recordData.getItem_id();
			
			map.put(strItemId, recordData.getContent_item());
			
		}

		if (prevDataId != -1 && isMapOK(map, listPickItem)) {
			list.add(map);
		}
		
		
		return list;
	}

	// 判断当前 Map 数据是否满足所有的筛选条件
	private Boolean isMapOK(Map map, List<PickItem> listPickItem) {
		
		for (int i = 0; i < listPickItem.size(); i++) {

			PickItem pickItem = listPickItem.get(i);
			
			if (!isPickItemOK(map, pickItem)) {
				return false;
			}
		}
		
		return true;
	}

	// 判断当前 Map 数据是否满足特定筛选条件
	private Boolean isPickItemOK(Map map, PickItem pickItem) {
		Integer itemId = pickItem.getItem_id();

		String strItemId = "" + itemId;
		String strContent = (String) map.get(strItemId);
		
		
		if (strContent == null) {
			// 没有此 itemId 的数据，直接返回 false，过滤掉
			
			return false;
		}
		
		Integer choice = pickItem.getChoice();
		
		
		if (choice == null) {
			// 没有选择比较条件，那么默认是满足此验证的
			return true;
		}
		
		String strValue = pickItem.getPick_value();

		Item item = itemService.findItemByItemId(itemId);

		Boolean ret = false;
		
		switch (item.getType()) {
		case 1: {

			float v1 = 0;
			float v2 = 0;
			try {
				v1 = Float.parseFloat(strContent);
				v2 = Float.parseFloat(strValue);
				
			} catch (NumberFormatException e) {
				// 转换格式出错的数据，直接跳过
				return false;
			}

			switch (choice) {
			case 0:
				ret = (v1 > v2);

				break;
			case 1:
				ret = (v1 == v2);
				break;

			case 2:
				ret = (v1 < v2);
				break;
			}
		}
			break;

		case 3: {
//			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			long v1 = 0;
			long v2 = 0;
			try {
				v2 = df.parse(strValue).getTime();
				v1 = df.parse(strContent).getTime();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				return false;
			}

			switch (choice) {
			case 0:
				ret = (v1 > v2);

				break;
			case 1:
				ret = (v1 == v2);
				break;

			case 2:
				ret = (v1 < v2);
				break;
			}
		}
			break;
			
		default:
			{

				if (strValue == null) {
					ret = true;
					break;
				}
				
				String separator = ",";
				
				strValue.replace("，", separator);
				
				String[] arr = strValue.split(separator);
				

				ret = false;
				for (int i = 0; i < arr.length; i++) {
					if (strContent.equals(arr[i])) {
						ret = true;
						break;
					}					
				}
				
			}
			break;
		}
		
		return ret;
	}

	// 通过组合hsql的方式来实现，最终因为无法筛选相同 dataId 的属性，而放弃
	public List<RecordData> findAllByPickHSql(List<PickItem> listPickItem) {

		StringBuilder sb = new StringBuilder();

		sb.append("from t_data where ");

		Comparator<PickItem> comparator = new Comparator<PickItem>() {

			@Override
			public int compare(PickItem o1, PickItem o2) {
				return o1.getItem_id() - o2.getItem_id();
			}
		};

		// 按照 item_id 来排序，确保相同item_id 都被放在一起
		Collections.sort(listPickItem, comparator);

		Integer prevItemId = -1;

		for (int i = 0; i < listPickItem.size(); i++) {
			PickItem pickItem = listPickItem.get(i);

			Integer itemId = pickItem.getItem_id();

			if (prevItemId != itemId) {
				
				if (i != 0) {
					sb.append(")");
					sb.append(")");
					
					sb.append(" and ");
				}

				sb.append("(");
				sb.append("item_id<>");
				sb.append(itemId);

				sb.append(" or ");

				sb.append("(");

			} else {
				sb.append(" and ");
			}

			appendPickItemHSql(sb, pickItem);

			prevItemId = itemId;
		}
		
		if (prevItemId != -1) {
			sb.append(")");
			sb.append(")");
		}
		
		System.out.println(sb);
		//
		// String hsql = "from t_data";

		String hsql = new String(sb);
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hsql);

		return query.list();
	}

	// 追加当前 pick item 内容的验证条件
	private void appendPickItemHSql(StringBuilder sb, PickItem pickItem) {
		Integer itemId = pickItem.getItem_id();

		Integer choice = pickItem.getChoice();

		Item item = itemService.findItemByItemId(itemId);

		switch (item.getType()) {
		case 1:
			sb.append("CAST(content AS float)");
			break;
		case 3:
			sb.append("CAST(content AS date)");
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
			sb.append("' AS float)");
			break;
		case 3:
			sb.append("CAST('");
			sb.append(strValue);
			sb.append("' AS date)");
			break;
		default:
			sb.append(strValue);
			break;
		}
	}

	public void update(Object entity) {
		Session session = sessionFactory.getCurrentSession();

		session.saveOrUpdate(entity);

		session.flush();
	}

	public void deleteAllByItemId(Integer itemId) {
		String hsql = "delete from t_data as t where t.item_id=?";

		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hsql);

		query.setInteger(0, itemId);

		query.executeUpdate();
	}


	public void deleteAll() {
		String hsql = "delete from t_data";

		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hsql);

		query.executeUpdate();
	}
	
	public void delete(Object entity) {
		Session session = sessionFactory.getCurrentSession();

		session.delete(entity);

		session.flush();
	}

	public RecordData insert(Object entity) {
		Session session = sessionFactory.getCurrentSession();

		session.save(entity);

		RecordData objNew = (RecordData) entity;

		session.flush();

		return objNew;
	}

	public void insertBatch(List listRecord) {
		Session session = sessionFactory.getCurrentSession();

		for (int i = 0; i < listRecord.size(); i++) {
			
//			insert(listRecord.get(i));
			
			session.save(listRecord.get(i));
			
			if (i % 50 == 0) {
				session.flush();
				session.clear();
			}
		}

		session.flush();
		session.clear();
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
