package cn.tedu.store.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
			String username = "root";
			String password = "1234";
			User user = iUserService.login(username, password);
			System.err.println("OK..."+user);
		} catch (ServiceException e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void changePassword() {
		//changePassword(Integer uid, String oldPassword, String newPassword, String username)
		Integer uid = 1;
		String oldPassword = "123";
		String newPassword = "1234";
		String username = "root";
		iUserService.changePassword(uid, oldPassword, newPassword, username);
	}
	
	@Test
	public void showInfo() {
		try {
			Integer uid = 1;
			User resultUser = iUserService.showInfo(uid);
			System.err.println("OK."+resultUser);
		} catch (ServiceException e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void changeInfo() {
		try {
			Integer uid = 2;
			String username = "李四";
			User user = new User();
			user.setPhone("13598746563");
			user.setEmail("lisi@gmail.com");
			user.setGender(0);
			iUserService.changeInfo(uid, username, user);
			System.err.println("OK.");
		} catch (ServiceException e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void changeAvatar() {
		try {
			Integer uid = 1;
			String avatar = "123";
			String username = "root";
			iUserService.changeAvatar(uid, avatar, username);
		} catch (ServiceException e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
}
