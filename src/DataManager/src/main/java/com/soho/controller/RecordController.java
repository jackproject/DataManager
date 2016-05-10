package com.soho.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.soho.model.Item;
import com.soho.model.ItemParam;
import com.soho.model.OtherName;
import com.soho.model.PageParam;
import com.soho.model.PickItem;
import com.soho.model.RecordData;
import com.soho.model.ValidateItem;
import com.soho.service.ItemService;
import com.soho.service.PickItemService;
import com.soho.service.RecordDataService;


@Controller
@RequestMapping("/record")
public class RecordController {
    @Resource
    private ItemService itemService;

    @Resource
    private PickItemService pickItemService;
    
    @Resource
    private RecordDataService recordDataService;
    
    private List listRecordData; 

	@RequestMapping(value = "/recordbypage", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String viewByPage(@RequestBody PageParam pageParam) {

		System.out.println("pageParam:" + pageParam);
		System.out.println("listRecordData:" + listRecordData);
		
		String response = "";
		
		if (listRecordData == null) {
			System.out.println("listRecordData is null");
			
			listRecordData = createClientList(recordDataService.findAll());
		}
		
		Integer currentPage = pageParam.getCurrentPage();
		
		Integer start = (pageParam.getCurrentPage() - 1) * pageParam.getPageAmount();
		Integer end = start + pageParam.getPageAmount();
		
		if (end > listRecordData.size()) {
			end = listRecordData.size();
		}
		
		List listPage = listRecordData.subList(start, end);
		
		Map map = new HashMap();
		
		map.put("totalCount", listRecordData.size());
		map.put("data", listPage);
		
		response = buildResponse(map);

		return response;
	}
    
    
	@RequestMapping(value = "/record", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String view() {
		
		String response = "";
		
		List<RecordData> listRecordData = recordDataService.findAll();

		List list = createClientList(listRecordData);
		
		response = buildResponse(list);

		return response;
	}
    
    
	@RequestMapping(value = "/pickrecord/{pickId}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String viewByPickId(@PathVariable String pickId) {
		
		String response = "";

		Integer nPickId = Integer.parseInt(pickId);
		
		List<PickItem> listPickItem = pickItemService.findByPickId(nPickId);
		
		List list = recordDataService.findAllByPick(listRecordData, listPickItem);
		
		response = buildResponse(list);

		return response;
	}
	
	@RequestMapping(value = "/record", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String create(@RequestBody Map<String, String> recordParam) {
		
		String response = "";

		String strId = "record_id";
		
		Integer newDataId = recordDataService.findNewRecordId(); 

		List<RecordData> list = convertToListData(newDataId, recordParam);

		for (RecordData record : list) {
			recordDataService.insert(record);
		}
		
		
		// 更新最新的id
		recordParam.put(strId, ""+newDataId);
		
		response = buildResponse(recordParam);
		
		return response;
	}

	@RequestMapping(value = "/record/{dataId}", method = RequestMethod.PUT, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String update(@PathVariable String dataId, @RequestBody Map<String, String> recordParam) {
		
		String response = "";

		String strId = "record_id";
		
		Integer newDataId = Integer.parseInt(recordParam.get(strId)); 
		
		List<RecordData> list = convertToListData(newDataId, recordParam);

		for (RecordData record : list) {
			recordDataService.update(record);
		}
		
		response = buildResponse(recordParam);
		
		return response;
	}

	@RequestMapping(value = "/record/{dataId}", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String destroy(@PathVariable String dataId, @RequestBody Map<String, String> recordParam) {

		String response = "";

		Integer delDataId = Integer.parseInt(dataId); 
		
		List<RecordData> list = convertToListData(delDataId, recordParam);
		
		for (RecordData record : list) {
			recordDataService.delete(record);
		}
		
		response = buildResponse(recordParam);
		
		return response;
	}


	private List createClientList(List<RecordData> listRecordData) {

		Comparator<RecordData> comparator = new Comparator<RecordData>() {

			@Override
			public int compare(RecordData o1, RecordData o2) {
				return o1.getData_id() - o2.getData_id();
			}
		};

		// 按照 data_id 来排序，确保相同data_id 都被放在一起
		Collections.sort(listRecordData, comparator);
		
		List list = new ArrayList();

		Integer prevDataId = -1;
		Map obj = null;
		
		for (int i = 0; i < listRecordData.size(); i++) {

			RecordData record = listRecordData.get(i);
			
			if (!prevDataId.equals(record.getData_id())) {

				if (obj != null) {
					list.add(obj);
				}
				
				obj = new HashMap();
				prevDataId = record.getData_id();
				obj.put("record_id", prevDataId);

//				System.out.println("压入新数据 id prevDataId: " + prevDataId);
			}
			
			obj.put("" + record.getItem_id(), record.getContent_item());
		}
		
		if (obj != null) {
			list.add(obj);
		}
		return list;
	}
	
	private List convertToListData(Integer dataId,
			Map<String, String> recordParam) {
		
		List listData = new ArrayList();
		
		for(String key : recordParam.keySet()) {
			if (!key.equals("record_id")) {

				Integer itemId = Integer.parseInt(key);
				
				RecordData record = new RecordData();
				
				record.setData_id(dataId);
				record.setItem_id(itemId);
				record.setContent_item(recordParam.get(key));
				
				listData.add(record);
			}
		}
		
		return listData;
	}

	private String buildResponse(Object objData) {
		
		String response = "";
		
		Map map = new HashMap();
		map.put("success", true);
		map.put("message", "message");
		map.put("data", objData);
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		try {
			response = objectMapper.writeValueAsString(map);
		} catch (Exception e) {
			map.put("success", false);
			e.printStackTrace();
		}
		
		return response;
	}
}
