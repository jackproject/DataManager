package com.soho.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.soho.model.Pick;
import com.soho.model.PickItem;
import com.soho.model.PickItemParam;
import com.soho.model.ValidateItem;
import com.soho.service.ItemService;
import com.soho.service.PickItemService;
import com.soho.service.PickService;

@Controller
@RequestMapping("/pick")
public class PickController {
    @Resource
    private PickService pickService;

    @Resource
    private PickItemService pickItemService;
    
    @Resource
    private ItemService itemService;

	@RequestMapping(value = "/pick", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String view() {
		
		String response = findAllPickJson();
		
		return response;
	}

	@RequestMapping(value = "/pick", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String create(@RequestBody Pick pick) {
		
		pickService.insert(pick);

		String response = findAllPickJson();
		
		return response;
	}

	@RequestMapping(value = "/pick/{pickId}", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String destroy(@PathVariable String pickId) {
		
		Integer id = Integer.parseInt(pickId);
		
		pickItemService.deleteAllByPickId(id);
		
		Pick pick = new Pick();
		
		pick.setPick_id(id);
		
		pickService.delete(pick);

		String response = findAllPickJson();
		
		return response;
	}

	
	// PickItem 相关项
	@RequestMapping(value = "/pickitem/{pickId}", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String createPickItem(@PathVariable String pickId, @RequestBody PickItem pickItem) {
		
		String response = "";

		Integer nPickId = Integer.parseInt(pickId);
		
		pickItem.setPick_id(nPickId);
		
		pickItemService.insert(pickItem);
		
		response = findPickItemJson(nPickId);
		
		return response;
	}
	
	@RequestMapping(value = "/pickitem/{pickId}/{pickItemId}", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String destroyPickItem(@PathVariable String pickId, @PathVariable String pickItemId) {
		
		String response = "";

		Integer nPickId = Integer.parseInt(pickId);
		Integer nPickItemId = Integer.parseInt(pickItemId);
		
		PickItem pickItem = new PickItem();
		
		pickItem.setPick_item_id(nPickItemId);
		
		pickItemService.delete(pickItem);
		
		response = findPickItemJson(nPickId);
		
		return response;
	}
	

	// -----------------------------------

	/**
	 * 查询所有的刷选项目
	 */
	private String findAllPickJson() {
		String response = "";

		List<Pick> listPick = pickService.findAll();  
		
		List listData = new ArrayList();
		for (int i = 0; i < listPick.size(); i++) {
			
			Pick pick = listPick.get(i);
			
			System.out.println(pick);

			List<PickItem> listPickItem = findListItemByPickId(pick.getPick_id());

			Map map = new HashMap();

			map.put("pick_id", pick.getPick_id());
			map.put("pick_name", pick.getPick_name());
			map.put("pick_item", listPickItem);
			
			listData.add(map);
		}


		ObjectMapper objectMapper = new ObjectMapper();
		try {
			response = objectMapper.writeValueAsString(listData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}


	private String findPickItemJson(Integer pickId) {

		String response = "";
		
		List<PickItem> listPickItem = findListItemByPickId(pickId);

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			response = objectMapper.writeValueAsString(listPickItem);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	private List<PickItem> findListItemByPickId(Integer pickId) {
		List<PickItemParam> listPickParam = new ArrayList();
		
		List<PickItem> listPickItem = pickItemService.findByPickId(pickId);
		// List<PickItem> listPickItem = pickItemService.findAll();

		System.out.println(listPickItem);
		for  (int j = 0; j < listPickItem.size(); j++) {
			PickItem pickItem = listPickItem.get(j);

			PickItemParam param = new PickItemParam(pickItem);
			
			listPickParam.add(param);
		}
		
		return listPickItem;
	}

}
