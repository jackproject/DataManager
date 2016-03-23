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
import com.soho.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {
    
    @Resource
    private ItemService itemService;

	@RequestMapping(value = "/item", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String view() {
		
		String response = "";
		
		List<Item> listItem = itemService.findAll();
		
		List<ItemParam> listData = new ArrayList();
		for (int i = 0; i < listItem.size(); i++) {
			
			Item item = listItem.get(i);
			ItemParam itemParam = new ItemParam(item);
			
			listData.add(itemParam);
		}

		response = buildResponse(listData);

		return response;
	}


	@RequestMapping(value = "/item", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String create(@RequestBody ItemParam itemParam) {
		
		String response = "";

		Item item = new Item(itemParam);
		
		Item newItem = itemService.insert(item);
		
		// 返回最新创建的 item id
		itemParam.setItem_id(newItem.getItem_id());
		
		response = buildResponse(itemParam);

		return response;
	}


	@RequestMapping(value = "/item/{itemId}", method = RequestMethod.PUT, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String update(@PathVariable String itemId, @RequestBody ItemParam itemParam) {
		
		String response = "";

		Item item = new Item(itemParam);
		
		itemService.update(item);
		
		response = buildResponse(itemParam);
		
		return response;
	}


	@RequestMapping(value = "/item/{itemId}", method = RequestMethod.DELETE, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String destroy(@PathVariable String itemId, @RequestBody ItemParam itemParam) {
		
		
		String response = "";
		
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
		
//		if (objData instanceof List) {
//			
//			List data = new ArrayList();
//			
//			
//			 
//			map.put("data", data);
//			
//		} else if (objData instanceof Item) {
//			
//			ItemParam data = new ItemParam((Item)objData);
//			
//			map.put("data", data);
//		}
		

		ObjectMapper objectMapper = new ObjectMapper();
		
		try {
			response = objectMapper.writeValueAsString(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return response;
	}

    @RequestMapping(value="/items",method=RequestMethod.GET)
    public ModelAndView hello2(){
        ModelAndView mv = new ModelAndView();
        mv.addObject("message", "HelloMVC");
        mv.setViewName("users");
        
        return mv;
    }
    

    @RequestMapping(value="/listItem",method=RequestMethod.GET)
    public ModelAndView listItem(){
        ModelAndView mv = new ModelAndView();
        
        List<Item> listItem = itemService.findAll();
        
        mv.addObject("message", listItem);
        
        mv.setViewName("users");
        
        return mv;
    }
    
	@RequestMapping(value = "/test", method = RequestMethod.GET,consumes="application/json;charset=UTF-8",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String test() throws UnsupportedEncodingException {
		
		String response = "";
		
		response = "测试输出中文";
		
		response = new String(response.getBytes("ISO-8859-1"), "UTF-8"); 
		
		
		return response;
	}
    
	@RequestMapping(value = "/list", method = RequestMethod.POST, consumes="application/json", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String list() {
		
		String response = "";
		
		List<Item> listItem = itemService.findAll();
		
		System.out.println(listItem);
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		Item item = listItem.get(0);

//		try {
//			response = objectMapper.writeValueAsString(item);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        System.out.println(response);
		
		int count = listItem.size();
		
		for (int i = 0; i < count; i++) {
			
			Item obj = listItem.get(i);
			
			System.out.println(obj.getName());			
		}
		
		
		try {
			response = objectMapper.writeValueAsString(listItem);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return response;
	}
	
//
//    @RequestMapping(value="/list",method=RequestMethod.GET)
//    @ResponseBody
//    public List<Item> list(){
//        List<Item> listItem = itemService.getAll();
//        
//        return listItem;
//    }
}
