package com.soho.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.soho.model.Item;
import com.soho.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {
    
    @Resource
    private ItemService itemService;
	
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
