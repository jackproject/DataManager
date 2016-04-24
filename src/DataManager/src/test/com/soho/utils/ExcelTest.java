package com.soho.utils;

import java.io.IOException;

import javax.annotation.Resource;

import jxl.read.biff.BiffException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.soho.model.Item;
import com.soho.model.OtherName;
import com.soho.model.RecordData;
import com.soho.service.ItemService;
import com.soho.service.OtherNameService;



@ContextConfiguration(locations={"classpath:spring-*.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
//如果是true不会改变数据库数据，如果是false会改变数据
@TransactionConfiguration(transactionManager="transactionManager",defaultRollback=false)
public class ExcelTest {

    @Resource
    private ItemService itemService;
    
    @Resource
    private OtherNameService otherNameService;
    
    
    private Integer findItemId(String strItemName) {

		// System.out.println(strItemName);
		
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
    @Test
    public void analysis() {
    	ExcelAnalyser excel = new ExcelAnalyser();

    	try {
			excel.Init("D:\\百度云同步盘\\emacs\\project\\code\\DataManager\\doc\\测试数据.xls");
			
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}


    	int rows = excel.findExcelCols();
    	int cols = excel.findExcelCols();

		for (int i = 1; i < rows; i++) {
			// 这里插入一条数据记录
			
			
			for (int j = 0; j < cols; j++) {

				String strItemName = excel.findCellString(j, 0);

				Integer itemId = findItemId(strItemName);

				if (itemId == -1) {
					// 别名表中也找不到，跳过下面的操作
					System.out.println("找不到字段名：" + strItemName);

					continue;
				}

				System.out.println(strItemName + ": " + itemId);
				

				String strContent = excel.findCellString(j, i);
				
				RecordData recordData = new RecordData();
				
				// TODO: 更新data id
				recordData.setData_id(i);
				
				recordData.setItem_id(itemId);
				recordData.setContent_item(strContent);
				
				System.out.println(recordData);
			}
		}
		

    		
    	
    }

}
