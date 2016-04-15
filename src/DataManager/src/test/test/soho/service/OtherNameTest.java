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
import com.soho.model.OtherName;
import com.soho.service.ItemService;
import com.soho.service.OtherNameService;


@ContextConfiguration(locations={"classpath:spring-*.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
//如果是true不会改变数据库数据，如果是false会改变数据
@TransactionConfiguration(transactionManager="transactionManager",defaultRollback=false)
public class OtherNameTest {
    @Resource
    private OtherNameService otherNameService;
    
    // @Test
    public void findByItemId() {

		List<OtherName> listItem = otherNameService.findByItemId(2);    	

		System.out.println(listItem);
    }

    //@Test
    public void findById() {
    	OtherName otherName = otherNameService.findById(1);

		System.out.println(otherName);
    }

    @Test
    public void updateAllByItemId() {
    	
		List<OtherName> listItem = otherNameService.findByItemId(1);
		
		System.out.println(listItem);
		
    	otherNameService.updateAllByItemId(listItem);
    	
    }
    
    // @Test
    public void insert(){
    	
    	OtherName item = new OtherName();

    	item.setItem_id(2);
    	item.setName("othername new");

		
    	OtherName newItem = otherNameService.insert(item);

    	System.out.println(newItem.getOthername_id());
    	
		System.out.println("insert end");

		List<OtherName> listItem = otherNameService.findByItemId(2);    	

		System.out.println(listItem);
    }



}
