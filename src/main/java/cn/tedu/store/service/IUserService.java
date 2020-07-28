package cn.tedu.store.service;

import cn.tedu.store.entity.User;

/**
 * 处理用户数据的业务层接口
 * @author dell
 *
 */
public interface IUserService {
	
	/**
	 * 用户注册
	 * @param user
	 */
	void reg(User user);

	/**
	 * 用户登录
	 * @param username 用户名
	 * @param password 密码
	 * @return 成功登录的用户数据
	 */
	User login(String username, String password);
	
	
}
