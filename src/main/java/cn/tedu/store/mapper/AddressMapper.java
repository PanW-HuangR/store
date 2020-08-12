package cn.tedu.store.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.tedu.store.entity.Address;

/**
 * 处理收货地址数据的持久层接口
 * @author dell
 *
 */
public interface AddressMapper {
	
	/**
	 * 插入收货地址数据
	 * @param address 收货地址数据
	 * @return 受影响的行数
	 */
	Integer insert(Address address);
	
	/**
	 * 统计某用户的收货地址数据的数量
	 * @param uid 用户的id
	 * @return 该用户的收货地址数据的数量
	 */
	Integer countByUid(Integer uid);
	
	/**
	 * 查询某用户的收货地址列表
	 * @param uid 用户的id
	 * @return 该用户的收货地址列表
	 */
	List<Address> findByUid(Integer uid);
	
	/**
	 * 在点击设置默认按钮时，先将该用户的所有收货地址全部设置成非默认
	 * @param uid 用户id
	 * @return 收影响的行数
	 */
	Integer updateNonDefaultByUid(Integer uid);
	
	/**
	 * 将指定的收货地址设置为默认
	 * @param aid 收货地址的id
	 * @param modifiedUser 修改执行人
	 * @param modifiedTime 修改时间
	 * @return 收影响的行数
	 */
	Integer updateDefaultByAid(
			@Param("aid") Integer aid,
			@Param("modifiedUser") String modifiedUser,
			@Param("modifiedTime") Date modifiedTime);
	
	/**
	 * 根据收货地址id查询收货地址详情
	 * @param aid 收货地址id
	 * @return 匹配的收货地址详情，如果没有匹配的数据，则返回null
	 */
	Address findByAid(Integer aid);
	
	/**
	 * 根据收货地址id，删除数据
	 * @param aid
	 * @return
	 */
	Integer deleteByAid(Integer aid);
	
	/**
	 * 查询某用户最后修改的那条收货地址
	 * @param uid
	 * @return
	 */
	Address findLastModifiedByUid(Integer uid);
	
}
