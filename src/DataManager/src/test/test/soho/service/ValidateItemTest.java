package test.soho.service;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.soho.model.OtherName;
import com.soho.model.ValidateItem;
import com.soho.service.OtherNameService;
import com.soho.service.ValidateItemService;


@ContextConfiguration(locations={"classpath:spring-*.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
//�����true����ı����ݿ����ݣ������false��ı�����
@TransactionConfiguration(transactionManager="transactionManager",defaultRollback=false)
public class ValidateItemTest {
    @Resource
    private ValidateItemService validateItemService;
    
    @Test
    public void findByItemId() {
    	
//    	String validate = "��֤1, ��֤2�� ��֤3";
//
//    	String[] arr = validate.split("[,��]");
//        for(String s : arr){
//            System.out.println(s.trim());
//        }

		List<ValidateItem> listItem = validateItemService.findByItemId(1);    	

		System.out.println(listItem);
    }

    //@Test
    public void findById() {
    	ValidateItem validateItem = validateItemService.findById(1);

		System.out.println(validateItem);
    }
    
    
}
