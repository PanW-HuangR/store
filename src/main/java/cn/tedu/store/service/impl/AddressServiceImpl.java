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
		Integer count = addressMapper.countByUid(uid);
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
		//执行插入用户数据，并获取返回的受影响行数
		Integer rows = addressMapper.insert(address);
		//判断受影响行数是否不为1
		if(rows != 1) {
			//是：抛出InsertException
			throw new InsertException("增加收货地址失败！插入收货地址数据时出现位置错误，请联系系统管理员！");
		}
	}

	@Override
	public List<Address> getByUid(Integer uid) {
		List<Address> list = addressMapper.findByUid(uid);
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
		//根据参数aid查询收货地址数据
		Address resultAddress = addressMapper.findByAid(aid);
		//判断查询结果是否为null
		if(resultAddress == null) {
			//是：AddressNotFoundException
			throw new AddressNotFoundException("设置默认收货地址失败！尝试访问的数据不存在！");
		}
		
		//判断查询结果中的uid与参数uid是否不一致
		if(!resultAddress.getUid().equals(uid)) {
			//是：AccessDeniedException
			throw new AccessDeniedException("设置默认收货地址失败！非法访问已经被拒绝！");
		}
		
		//将该用户的所有收货地址设置为非默认，并获取返回值
		Integer rows = addressMapper.updateNonDefaultByUid(uid);
		//判断返回值是否小于1
		if(rows < 1) {
			//是：UpdateException
			throw new UpdateException("设置默认收货地址失败[1]！更新收货地址数据是出现未知错误，请联系系统管理员！");
		}
		
		//将指定的收货地址设置为默认，并获取返回值
		rows = addressMapper.updateDefaultByAid(aid, username, new Date());
		//判断返回值是否不为1
		if(rows != 1) {
			//是：UpdateException
			throw new UpdateException("设置默认收货地址失败[1]！更新收货地址数据是出现未知错误，请联系系统管理员！");
		}
		
	}
	
}
