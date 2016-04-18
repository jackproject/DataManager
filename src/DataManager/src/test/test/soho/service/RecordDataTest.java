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
import com.soho.model.PickItem;
import com.soho.service.ItemService;
import com.soho.service.PickItemService;
import com.soho.service.RecordDataService;

@ContextConfiguration(locations={"classpath:spring-*.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
//如果是true不会改变数据库数据，如果是false会改变数据
@TransactionConfiguration(transactionManager="transactionManager",defaultRollback=false)
public class RecordDataTest {

    @Resource
    private RecordDataService recordDataService;

    @Resource
    private PickItemService pickItemService;

    @Test
    public void findAllByPick() {

		List<PickItem> listPickItem = pickItemService.findByPickId(1);
		
		System.out.println(listPickItem);
		
		
		List list = recordDataService.findAllByPick(listPickItem);
		

		System.out.println(list);		
		
    }
    
    // @Test    
    public void findAll() {    	
		List list = recordDataService.findAll();    	

		System.out.println(list);		
    }

 // @Test
    public void findNewRecordId() {
		Integer newId = recordDataService.findNewRecordId();    	

		System.out.println(newId);    	
    }

}
