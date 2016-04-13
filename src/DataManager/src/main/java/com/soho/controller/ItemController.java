package com.soho.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.soho.model.Item;
import com.soho.model.ItemParam;
import com.soho.model.OtherName;
import com.soho.model.ValidateItem;
import com.soho.service.ItemService;
import com.soho.service.OtherNameService;
import com.soho.service.ValidateItemService;

@Controller
@RequestMapping("/item")
public class ItemController {
    
    @Resource
    private ItemService itemService;
    
    @Resource
    private OtherNameService otherNameService;
    
    @Resource
    private ValidateItemService validateItemService;

	@RequestMapping(value = "/item", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String view() {
		
		String response = "";
		
		List<Item> listItem = itemService.findAll();
		
		List<ItemParam> listData = new ArrayList();
		for (int i = 0; i < listItem.size(); i++) {
			
			Item item = listItem.get(i);
			ItemParam itemParam = new ItemParam(item);
			
			Integer itemId = item.getItem_id();
			
			List<OtherName> listOtherName = otherNameService.findByItemId(itemId);
			List<ValidateItem> listValidateItem = validateItemService.findByItemId(itemId);
			
			// 更新 验证表 与 别名表
			itemParam.modOtherNameByList(listOtherName);
			itemParam.modValidateByList(listValidateItem);
			
			listData.add(itemParam);
		}

		response = buildResponse(listData);

		return response;
	}


	@RequestMapping(value = "/item", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String create(@RequestBody ItemParam itemParam) {
		
		String response = "";

		// 1. 插入最新的字段
		Item item = new Item(itemParam);
		
		Item newItem = itemService.insert(item);
		
		// 返回最新创建的 item id
		itemParam.setItem_id(newItem.getItem_id());
		
		// 2. 更新最新的值
		otherNameService.updateAllByItemId(itemParam.findListOtherName());
		validateItemService.updateAllByItemId(itemParam.findListValidateItem());
		
		response = buildResponse(itemParam);

		return response;
	}


	@RequestMapping(value = "/item/{itemId}", method = RequestMethod.PUT, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String update(@PathVariable String itemId, @RequestBody ItemParam itemParam) {
		
		String response = "";

		Item item = new Item(itemParam);
		
		itemService.update(item);

		// 2. 更新最新的值
		otherNameService.updateAllByItemId(itemParam.findListOtherName());
		validateItemService.updateAllByItemId(itemParam.findListValidateItem());
		
		response = buildResponse(itemParam);
		
		return response;
	}


	@RequestMapping(value = "/item/{itemId}", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String destroy(@PathVariable String itemId, @RequestBody ItemParam itemParam) {
		
		
		String response = "";

		// 1. 删除依赖的字段
		Integer deleteItemId = itemParam.getItem_id();
		otherNameService.deleteAllByItemId(deleteItemId);
		validateItemService.deleteAllByItemId(deleteItemId);
		

		// 2. 删除字段表
		Item item = new Item(itemParam);
		
		itemService.delete(item);

		response = buildResponse(itemParam);
		
		return response;
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
