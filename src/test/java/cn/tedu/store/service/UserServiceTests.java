package cn.tedu.store.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mysql.cj.protocol.x.Ok;

import cn.tedu.store.entity.User;
import cn.tedu.store.service.ex.ServiceException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTests {
	
	@Autowired
	private IUserService iUserService;
	
	@Test
	public void reg() {
		try {
			User user = new User();
			user.setUsername("张三");
			user.setPassword("123");
			user.setGender(1);
			user.setPhone("15727788742");
			user.setEmail("zhangsan@boe.com.cn");
			user.setAvatar("avatar01");
			iUserService.reg(user);
			System.err.println("OK...");
		} catch (ServiceException e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void login() {
		try {
			String username = "张三";
			String password = "123";
			User user = iUserService.login(username, password);
			System.err.println("OK..."+user);
		} catch (ServiceException e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
}
