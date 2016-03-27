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
import com.soho.service.RecordDataService;

@ContextConfiguration(locations={"classpath:spring-*.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
//�����true����ı����ݿ����ݣ������false��ı�����
@TransactionConfiguration(transactionManager="transactionManager",defaultRollback=false)
public class RecordDataTest {

    @Resource
    private RecordDataService recordDataService;
    
    // @Test    
    public void findAll() {    	
		List list = recordDataService.findAll();    	

		System.out.println(list);
		
    }

    @Test
    public void findNewRecordId() {
		Integer newId = recordDataService.findNewRecordId();    	

		System.out.println(newId);    	
    }

}
