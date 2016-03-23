package test.soho.service;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.soho.model.Item;
import com.soho.service.ItemService;

@ContextConfiguration(locations={"classpath:spring-*.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
//如果是true不会改变数据库数据，如果是false会改变数据
@TransactionConfiguration(transactionManager="transactionManager",defaultRollback=false)
public class ItemTest {  

    
    @Resource
    private ItemService itemService;
    
    //@Test
    public void update() {

		List<Item> listItem = itemService.findAll();    	

		int count = listItem.size();

    	Item item = listItem.get(0);
    	
    	item.setName("update item name");
    	
    	itemService.update(item);
    	
    	
    	findAll();
    }
    
    //@Test
    public void delete() {

		List<Item> listItem = itemService.findAll();    	

		int count = listItem.size();

		System.out.println("item count: " + count);

    	Item item = listItem.get(0);
    	
    	itemService.delete(item);
    	
    	
    	findAll();
    }
    
    @Test
    public void insert(){
    	
    	Item item = new Item();

    	item.setItem_id(5);
    	item.setName("item new");
    	item.setOrder_num(1);
    	item.setType(0);

		
    	Item newItem = itemService.insert(item);

    	System.out.println(newItem.getItem_id());
    	
		System.out.println("insert end");
		
		findAll();
    }
    
//    @Test
    public void findAll(){
		System.out.println("find All");
    	
		List<Item> listItem = itemService.findAll();    	

		int count = listItem.size();

		System.out.println("item count: " + count);
		
		for (int i = 0; i < count; i++) {
			
			Item obj = listItem.get(i);
			
			System.out.println(obj);			
		}
    }
}
