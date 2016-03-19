package com.soho.controller;

import java.util.List;

import javax.annotation.Resource;

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
        
        List<Item> listItem = itemService.getAll();
        
        mv.addObject("message", listItem);
        
        mv.setViewName("users");
        
        return mv;
    }

    @RequestMapping(value="/list",method=RequestMethod.GET)
    @ResponseBody
    public List<Item> list(){
        List<Item> listItem = itemService.getAll();
        
        return listItem;
    }
}
