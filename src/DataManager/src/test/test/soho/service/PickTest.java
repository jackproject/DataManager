package test.soho.service;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.soho.model.Item;
import com.soho.model.Pick;
import com.soho.service.ItemService;
import com.soho.service.PickItemService;
import com.soho.service.PickService;


@ContextConfiguration(locations={"classpath:spring-*.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
//如果是true不会改变数据库数据，如果是false会改变数据
public class PickTest {
    @Resource
    private PickService pickService;
    
    @Test
    public void findAll(){
		System.out.println("find All");
    	
		List<Pick> listItem = pickService.findAll();  
		
		System.out.println(listItem);

		int count = listItem.size();

		System.out.println("item count: " + count);
		
		for (int i = 0; i < count; i++) {
			
			Pick obj = listItem.get(i);
			
			System.out.println(obj);			
		}
    }

}
