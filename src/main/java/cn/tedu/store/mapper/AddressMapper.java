package cn.tedu.store.mapper;

import cn.tedu.store.entity.Address;

/**
 * 处理收货地址数据的持久层接口
 * @author dell
 *
 */
public interface AddressMapper {
	
	Integer insert(Address address);
	
	Integer countByUid(Integer uid);
	
}
