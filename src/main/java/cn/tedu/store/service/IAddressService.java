package cn.tedu.store.service;

import java.util.List;

import cn.tedu.store.entity.Address;

/**
 * 处理收货地址数据的业务层接口
 * @author dell
 *
 */
public interface IAddressService {
	
	/**
	 * 新增收货地址
	 * @param uid 用户的id
	 * @param username 用户名
	 * @param address 收货地址数据
	 */
	void addNew(Integer uid,String username,Address address);
	
	/**
	 * 查询某用户的收货地址列表
	 * @param uid 用户的id
	 * @return 该用户的收货地址列表
	 */
	List<Address> getByUid(Integer uid);
	
	/**
	 * 将某地址设置成默认地址
	 * @param aid 地址id
	 * @param uid 用户id
	 * @param username 用户名
	 */
	void setDefault(Integer aid,Integer uid,String username);
	
	/**
	 * 将某地址删除
	 * @param aid 地址id
	 * @param uid 用户id
	 * @param username 用户名
	 */
	void delete(Integer aid,Integer uid,String username);
}
