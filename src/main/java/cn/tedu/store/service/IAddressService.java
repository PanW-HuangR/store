package cn.tedu.store.service;

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
	
}
