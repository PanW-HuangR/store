package cn.tedu.store.mapper;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTests {
	
	@Autowired
	private UserMapper userMapper;
	
	@Test
	public void insert() {
		User user = new User();
		user.setUsername("张三");
		user.setPassword("1234");
	}
	
	@Test
	public void updatePasswordByUid() {
		
		Integer uid = 1;
		String password = "1234";
		String modifiedUser = "密码管理员";
		Date modifiedTime = new Date();
		Integer rows = userMapper.updatePasswordByUid(uid, password, modifiedUser, modifiedTime);
		System.err.println("rows="+rows);
	}
	
	@Test
	public void findByUid() {
		Integer uid = 2;
		User resultUser = userMapper.findByUid(uid);
		System.err.println(resultUser);
	}
}
