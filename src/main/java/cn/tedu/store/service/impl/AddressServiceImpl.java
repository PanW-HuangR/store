package cn.tedu.store.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tedu.store.entity.Address;
import cn.tedu.store.mapper.AddressMapper;
import cn.tedu.store.service.IAddressService;
import cn.tedu.store.service.IDistrictService;
import cn.tedu.store.service.ex.AccessDeniedException;
import cn.tedu.store.service.ex.AddressCountLimitException;
import cn.tedu.store.service.ex.AddressNotFoundException;
import cn.tedu.store.service.ex.DeleteException;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.service.ex.UpdateException;

/**
 * 处理收货地址数据的业务层实现类
 * @author dell
 *
 */
@Service
public class AddressServiceImpl implements IAddressService {
	
	@Autowired
	private AddressMapper addressMapper;
	
	@Autowired
	private IDistrictService iDistrictService;
	
	@Value("${project.address-max-count}")
	private int addressMaxCount;

	@Override
	public void addNew(Integer uid, String username, Address address) {
		//根据uid统计该用户的收货地址数量
		Integer count = countByUid(uid);
		//判断数量是否超出设置值
		if(count >= addressMaxCount) {
			//是：抛出AddressCountLimitException
			throw new AddressCountLimitException("增加收货地址失败！收货地址数量已经达到上限("+addressMaxCount+")!");
		}
		
		//根据以上统计的数量是否为0，得到isDefault值
		Integer isDefault = count == 0 ? 1 : 0;
		//创建当前时间now
		Date now = new Date();
		
		//补全数据：uid
		address.setUid(uid);
		//补全数据：is_default
		address.setIsDefault(isDefault);
		//补全数据：4项日志(username ,now )
		address.setCreatedUser(username);
		address.setCreatedTime(now);
		address.setModifiedUser(username);
		address.setModifiedTime(now);
		address.setProvinceName(iDistrictService.getNameByCode(address.getProvinceCode()));
		address.setCityName(iDistrictService.getNameByCode(address.getCityCode()));
		address.setAreaName(iDistrictService.getNameByCode(address.getAreaCode()));
		//执行插入用户数据
		insert(address);
	}

	@Override
	public List<Address> getByUid(Integer uid) {
		List<Address> list = findByUid(uid);
		for (Address address : list) {
			address.setUid(null);
			address.setProvinceCode(null);
			address.setCityCode(null);
			address.setAreaCode(null);
			address.setIsDefault(null);
			address.setCreatedTime(null);
			address.setCreatedUser(null);
			address.setModifiedTime(null);
			address.setModifiedUser(null);
		}
		return list;
	}

	@Override
	@Transactional
	public void setDefault(Integer aid, Integer uid, String username) {
		//根据参数aid查询收货地址数据，并判断查询结果中的uid与参数uid是否不一致
		Address resultAddress = findByAid(aid);
		//判断查询结果中的uid与参数uid(登录用户的uid)是否不一致
		//注意：对比integer类型的值时，如果值的范围在 -128~127 之间，使用==或equals()均可，如果超过这个范围，必须使用equals()
		if(!resultAddress.getUid().equals(uid)) {
			//是：AccessDeniedException
			throw new AccessDeniedException("设置默认地址失败！非法访问已经被拒绝！");
		}
		
		//将该用户的所有收货地址设置为非默认
		updateNonDefaultByUid(uid);
		
		//将指定的收货地址设置为默认
		updateDefaultByAid(aid, username, new Date());
		
	}

	@Override
	@Transactional
	public void delete(Integer aid, Integer uid, String username) {
		//根据参数aid查询收货地址数据
		Address resultAddress = findByAid(aid);
		
		//判断查询结果中的uid与参数uid(登录用户的uid)是否不一致
		//注意：对比integer类型的值时，如果值的范围在 -128~127 之间，使用==或equals()均可，如果超过这个范围，必须使用equals()
		if(!resultAddress.getUid().equals(uid)) {
			//是：AccessDeniedException
			throw new AccessDeniedException("删除地址失败！非法访问已经被拒绝！");
		}
		
		//执行删除
		deleteByAid(aid);
		
		
		//判断查询结果(对应刚刚删除的数据)中的isDefault是否不为1
		if(resultAddress.getIsDefault() != 1) {
			return;
		}
		
		//统计当前用户还剩多少收获地址
		Integer count = countByUid(uid);
		//判断统计结果是否为0
		if(count == 0) {
			return;
		}
		
		//查询当前用户的最后修改的那一条收货地址
		Address address = findLastModifiedByUid(uid);
		//从本次查询中取出数据的aid
		Integer lastModifiedAid = address.getAid();
		//执行设置默认收获地址
		updateDefaultByAid(lastModifiedAid, username, new Date());
		
	}
	
	/**
	 * 将指定的收货地址设置为默认
	 * @param aid 地址id
	 * @param modifiedUser 修改人
	 * @param modifiedTime 修改时间
	 */
	private void updateDefaultByAid(Integer aid, String modifiedUser, Date modifiedTime) {
		//将指定的收货地址设置为默认，并获取返回值
		Integer rows = addressMapper.updateDefaultByAid(aid, modifiedUser, modifiedTime);
		//判断返回值是否不为1
		if(rows != 1) {
			//是：UpdateException
			throw new UpdateException("设置默认收货地址失败[2]！更新收货地址数据时出现未知错误，请联系系统管理员！");
		}
	}
	
	/**
	 * 根据uid统计该用户的收货地址数量
	 * @param uid 用户id
	 * @return 收货地址数量
	 */
	private Integer countByUid(Integer uid) {
		Integer count = addressMapper.countByUid(uid);
		return count;
	}
	
	/**
	 * 执行插入地址操作
	 * @param address 要插入的地址
	 */
	private void insert(Address address) {
		Integer rows = addressMapper.insert(address);
		//判断受影响行数是否不为1
		if(rows != 1) {
			//是：抛出InsertException
			throw new InsertException("增加收货地址失败！插入收货地址数据时出现位置错误，请联系系统管理员！");
		}
	}
	
	/**
	 * //根据参数aid查询收货地址数据，并判断查询结果中的uid与参数uid是否不一致
	 * @param aid 地址id
	 * @param uid 当前登录的用户id
	 */
	private Address findByAid(Integer aid) {
		//根据参数aid查询收货地址数据
		Address resultAddress = addressMapper.findByAid(aid);
		//判断查询结果是否为null
		if(resultAddress == null) {
			//是：AddressNotFoundException
			throw new AddressNotFoundException("尝试访问的数据不存在！");
		}
		
		return resultAddress;
	}
	
	/**
	 * 将该用户的所有收货地址设置为非默认
	 * @param uid
	 */
	private void updateNonDefaultByUid(Integer uid) {
		//将该用户的所有收货地址设置为非默认，并获取返回值
		Integer rows = addressMapper.updateNonDefaultByUid(uid);
		//判断返回值是否小于1
		if(rows < 1) {
			//是：UpdateException
			throw new UpdateException("设置默认收货地址失败[1]！更新收货地址数据是出现未知错误，请联系系统管理员！");
		}
	}
	
	/**
	 * 根据收货地址id删除数据
	 * @param aid 收货地址id
	 */
	private void deleteByAid(Integer aid) {
		Integer rows = addressMapper.deleteByAid(aid);
		if (rows != 1) {
			throw new DeleteException(
				"删除收货地址失败！删除收货地址数据时出现未知错误，请联系系统管理员！");
		}
	}
	
	/**
	 * 查询某用户最后修改的收货地址
	 * @param uid 用户的id
	 * @return 该用户最后修改的收货地址，如果该用户没有收货地址，则返回null
	 */
	private Address findLastModifiedByUid(Integer uid) {
		return addressMapper.findLastModifiedByUid(uid);
	}
	
	/**
	 * 查询某用户的收货地址列表
	 * @param uid 用户的id
	 * @return 该用户的收货地址列表
	 */
	private List<Address> findByUid(Integer uid) {
		return addressMapper.findByUid(uid);
	}
	
}
