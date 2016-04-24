package test.soho.service;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.soho.model.User;
import com.soho.service.ItemService;
import com.soho.service.UserService;


@ContextConfiguration(locations={"classpath:spring-*.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
//如果是true不会改变数据库数据，如果是false会改变数据
@TransactionConfiguration(transactionManager="transactionManager",defaultRollback=false)
public class UserTest {

    
    @Resource
    private UserService userService;

    // @Test
    public void findAll() {
    	
    	List<User> list = userService.findAll();
    	
		System.out.println(list);
    }
    
    @Test
    public void findUser() {
    	
    	User user = userService.findUser("1", "2");
    	
		System.out.println(user);
    }
}
