package com.soho.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
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

	public List<RecordData> findAll() {
		String hsql = "from t_data";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hsql);

		return query.list();
	}

	public List<RecordData> findAllByPickTest(List<PickItem> listPickItem) {

		List<RecordData> listRecordData = new ArrayList();

		List<RecordData> list = findAll();

		return listRecordData;
	}

	public List<RecordData> findAllByPick(List<PickItem> listPickItem) {

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
