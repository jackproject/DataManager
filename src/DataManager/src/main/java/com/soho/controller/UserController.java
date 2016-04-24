package com.soho.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.soho.model.ItemParam;
import com.soho.model.User;
import com.soho.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
    
    @Resource
    private UserService userService;

	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String login(@RequestBody User userParam) {

		String response = "";

		User user = userService.findUser(userParam.getUsername(),
				userParam.getPassword());
		
		response = buildResponse(user);
		
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
