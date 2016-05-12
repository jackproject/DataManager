package com.soho.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import jxl.read.biff.BiffException;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.soho.model.Item;
import com.soho.model.OtherName;
import com.soho.model.RecordData;
import com.soho.model.ValidateItem;
import com.soho.service.ItemService;
import com.soho.service.OtherNameService;
import com.soho.service.RecordDataService;
import com.soho.service.ValidateItemService;
import com.soho.utils.ExcelAnalyser;
import com.soho.utils.PathUtil;

@Controller
@RequestMapping("/file")
public class UploadController {
	
    @Resource
    private RecordDataService recordDataService;

	@Resource
	private ItemService itemService;

	@Resource
	private OtherNameService otherNameService;
	
    @Resource
    private ValidateItemService validateItemService;
    
    private Map mapValidate = null;
    
    // 用于保存记录
    private Integer userItemNameRow = 1;
    

	@RequestMapping(value = "/config", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String config(@RequestBody Map<String, String> configParam) {

		String response = "";
		
		System.out.println("itemNameRow: " + configParam.get("itemNameRow"));
		
		userItemNameRow = Integer.parseInt(configParam.get("itemNameRow"));
		
		response = buildResponse(true, null, null);

		return response;
	}
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String upload(@RequestParam("file") MultipartFile file) {
		
		
		String response = "";

		PathUtil pathUtil = new PathUtil();
		String uploadPath = pathUtil.getWebRoot(); 
		
		uploadPath += "upload/";
		
		File testFile = new File(uploadPath);
		if (!testFile.exists()) {
			testFile.mkdir();
		}
		
		String newFileName = "" + new Date().getTime();
		
		String newFilePath = uploadPath;
		newFilePath += newFileName;
		newFilePath += ".xls";
		
		File newFile = new File(newFilePath);
		
		try {
			byte[] bytes = file.getBytes();
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(newFile));
	        stream.write(bytes);
	        stream.close();
		} catch (Exception e1) {
			e1.printStackTrace();

			response = buildResponse(false, null, null);
			
			// 直接终止
			return response;
		}		
		
		List listResult = new ArrayList();
		
		
		// 移除数据库中所有记录
		recordDataService.deleteAll();
		
		
		ExcelAnalyser excel = new ExcelAnalyser();

		try {
			excel.Init(newFilePath);

		} catch (Exception e) {
			e.printStackTrace();
			
			response = buildResponse(false, null, null);
			return response;
		}

		
//		boolean status = saveExcelToDb(newFilePath, itemNameRow, listResult);
		
		
		List listRecord = findRecordList(excel, userItemNameRow, listResult);
		
		recordDataService.insertBatch(listRecord);
		
		String logPath = uploadPath;
		
		String logFile = newFileName;		
		// 日志文件
		logFile += "_log.txt";

		logPath += logFile; 
		
		saveListResult(logPath, listResult);
		
		response = buildResponse(true, logFile, listResult);

		return response;
	}

	// 查找所有满足条件的字段列表
	private Map findItemIdList(ExcelAnalyser excel, Integer itemNameRow, List listResult) {

		Map map = new HashMap();

		int cols = excel.findExcelCols();
		
		for (int j = 0; j < cols; j++) {

			String strItemName = excel.findCellString(j, itemNameRow);

			// 查找字段id
			Integer itemId = findItemId(strItemName);

			if (itemId == -1) {
				String location = "(0, " + (j+1) + ") "  + strItemName;
				String message = "找不到此字段名或对应的别名!";
				
				appendListResult(listResult, location, message);

				// 别名表中也找不到，跳过下面的操作
				continue;
			}
			
			map.put(j, itemId);
		}
		
		return map;
	}

	// 查找所有满足条件的字段列表
	private List findRecordList(ExcelAnalyser excel, Integer itemNameRow, List listResult) {
		
		List listRecord = new ArrayList();

		// 重新加载验证规则
		mapValidate = new HashMap();
		
		
		// 将用户习惯的1开始计数，规划成0开始计数
		itemNameRow = itemNameRow - 1;
		
		int datRowStart = itemNameRow + 1; 
		
		Map mapItemId = findItemIdList(excel, itemNameRow, listResult);

		int rows = excel.findExcelRows();
		int cols = excel.findExcelCols();

		Integer newRecordId = recordDataService.findNewRecordId();
		
		for (int i = datRowStart; i < rows; i++) {
			// 这里插入一条数据记录
			
			boolean isRecordEmpty = true;
			
			for (int j = 0; j < cols; j++) {

				Integer itemId = (Integer) mapItemId.get(j);

				if (itemId == null) {
					continue;
				}

				String strContent = excel.findCellString(j, i);
				
				if (strContent.equals("")) {
					// 内容为空则跳过
					continue;
				}

				// 验证内容
				boolean isValid = isContentValid(itemId, strContent);
				
				if (!isValid) {

					String location = "(" + (i+1) + ", " + (j+1) + ") " + strContent;
					String message = "此内容不满足字段的验证条件！";

					appendListResult(listResult, location, message);

					// 验证通不过，直接跳过此条记录
					break;
				}			
				
				RecordData recordData = new RecordData();

				recordData.setData_id(newRecordId);
				recordData.setItem_id(itemId);
				recordData.setContent_item(strContent);
				
				listRecord.add(recordData);
				
				// recordDataService.insert(recordData);
				
				isRecordEmpty = false;
			}
			
			if (isRecordEmpty) {
				// 遇到空行时，终止查找记录
				break;
			}
			
			newRecordId ++;
		}
		
		return listRecord;
	}
	
	private boolean saveExcelToDb(String path, Integer itemNameRow, List listResult) {

		ExcelAnalyser excel = new ExcelAnalyser();

		try {
			excel.Init(path);

		} catch (Exception e) {
			e.printStackTrace();
			
			return false;
		}

		int rows = excel.findExcelRows();
		int cols = excel.findExcelCols();

		// 将用户习惯的1开始计数，规划成0开始计数
		itemNameRow = itemNameRow - 1;
		
		int datRowStart = itemNameRow + 1; 
		
		for (int i = datRowStart; i < rows; i++) {
			// 这里插入一条数据记录
			
			Integer newRecordId = recordDataService.findNewRecordId();
			
			for (int j = 0; j < cols; j++) {

				String strItemName = excel.findCellString(j, itemNameRow);

				// 查找字段id
				Integer itemId = findItemId(strItemName);

				if (itemId == -1) {
					String location = "(0, " + j + ") "  + strItemName;
					String message = "找不到此字段名或别名!";
					
					appendListResult(listResult, location, message);

					// 别名表中也找不到，跳过下面的操作
					continue;
				}


				// 验证内容
				String strContent = excel.findCellString(j, i);

				boolean isValid = isContentValid(itemId, strContent);
				
				if (!isValid) {

					String location = "(" + i + ", " + j + ") " + strContent;
					String message = "此内容不满足字段【"  + strItemName + "】 的验证条件！";

					System.out.println(message);
					
					appendListResult(listResult, location, message);

					// 别名表中也找不到，跳过下面的操作
					continue;
				}			
				
				RecordData recordData = new RecordData();

				recordData.setData_id(newRecordId);
				recordData.setItem_id(itemId);
				recordData.setContent_item(strContent);
				
				recordDataService.insert(recordData);
			}
		}
		
		return true;
	}


	private boolean isContentValid(Integer itemId, String strContent) {
		
		boolean result = true;
		
		List<ValidateItem> listValidateItem = (List<ValidateItem>) mapValidate.get(itemId);
		
		if (listValidateItem == null) {
			listValidateItem = validateItemService.findByItemId(itemId);
			
			// 压入缓冲
			mapValidate.put(itemId, listValidateItem);
		}
		
		if (listValidateItem.size() > 0) {

			result = false;
			for (int k = 0; k <listValidateItem.size(); k++) {
				
				ValidateItem validateItem = listValidateItem.get(k);
				if (strContent.equals(validateItem.getValidate_item())) {
					result = true;
					break;
				}
			}
		}
		
		return result;
	}


	private void saveListResult(String path, List listResult) {
		
		try {
			FileOutputStream fos = new FileOutputStream(path);
			OutputStreamWriter osw = new OutputStreamWriter(fos, "gbk");
	        
			StringBuilder fileContent = new StringBuilder();
		        
			for (int i = 0; i < listResult.size(); i++) {
				Map map = (Map) listResult.get(i);

				fileContent.append("位置: ");
				fileContent.append((String)map.get("location"));
				fileContent.append("\r\n");

				fileContent.append("错误提示: ");
				fileContent.append((String)map.get("message"));
				fileContent.append("\r\n");
				
				fileContent.append("\r\n");
		
			}
			osw.write(fileContent.toString());
			osw.flush();	
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}	

/*
		FileWriter writer = null;
		try {
			writer = new FileWriter(path);
			
			for (int i = 0; i < listResult.size(); i++) {
				Map map = (Map) listResult.get(i);
		
				writer.write("位置: ");
				writer.write((String)map.get("location"));
				writer.write("\r\n");

				writer.write("错误提示: ");
				writer.write((String)map.get("message"));
				writer.write("\r\n");
				
				writer.write("\r\n");
			}
	
			writer.flush();
	        writer.close();
	        
		} catch (IOException e) {
			
			e.printStackTrace();
		}	
*/
	}

	private void appendListResult(List listResult, String location,
			String message) {
		Map map = new HashMap();
		
		map.put("location", location);
		map.put("message", message);
		
		listResult.add(map);
	}
	
	private Integer findItemId(String strItemName) {

		Integer itemId = -1;

		Item item = itemService.findItemByItemName(strItemName);

		if (item == null) {
			// 字段名中找不到，就在别名表中找

			OtherName other = otherNameService.findByItemName(strItemName);

			if (other != null) {
				itemId = other.getItem_id();
			}

		} else {

			itemId = item.getItem_id();
		}

		return itemId;
	}
	
	private String buildResponse(boolean status, String message, Object objData) {
		
		String response = "";
		
		Map map = new HashMap();
		map.put("success", status);
		map.put("message", message);
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
