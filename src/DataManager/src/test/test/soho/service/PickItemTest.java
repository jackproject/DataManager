package test.soho.service;

import java.util.List;

import javax.annotation.Resource;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.soho.model.Pick;
import com.soho.model.PickItem;
import com.soho.service.PickItemService;
import com.soho.service.PickService;

@ContextConfiguration(locations={"classpath:spring-*.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
//如果是true不会改变数据库数据，如果是false会改变数据
public class PickItemTest {
    @Resource
    private PickItemService pickItemService;


    public void findAll(){
		System.out.println("find All");
    	
		List<PickItem> listItem = pickItemService.findAll();  
		
		System.out.println(listItem);
    }
}
