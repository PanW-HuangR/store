package cn.tedu.store.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import cn.tedu.store.entity.User;

public interface UserMapper {//下面方法是按照增-删-改-查的顺序
	
	/******************************增**********************************/
	
	/**
	 * 插入用户数据
	 * @param user
	 * @return
	 */
	Integer insert(User user);
	
	/******************************删**********************************/
	
	
	/******************************改**********************************/
	
	/**
	 * 修改某用户的密码
	 * @param uid 用户的id
	 * @param password 新密码
	 * @param modifiedUser 修改执行人
	 * @param modifiedTime 修改的时间
	 * @return 返回执行的行数
	 */
	Integer updatePasswordByUid(
			@Param("uid") Integer uid,
			@Param("password") String password,
			@Param("modifiedUser") String modifiedUser,
			@Param("modifiedTime") Date modifiedTime);
	
	/******************************查**********************************/
	
	/**
	 * 根据用户名查询用户数据详情
	 * @param username 用户名
	 * @return
	 */
	User findByUsername(String username);
	
	/**
	 * 根据用户id查询用户数据详情
	 * @param uid 用户id
	 * @return 匹配的用户数据，如果没有匹配的数据，则返回null
	 */
	User findByUid(Integer uid);
	
}
