package cn.tedu.store.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.tedu.store.entity.Address;
import cn.tedu.store.mapper.AddressMapper;
import cn.tedu.store.service.IAddressService;
import cn.tedu.store.service.ex.AddressCountLimitException;
import cn.tedu.store.service.ex.InsertException;

/**
 * 处理收货地址数据的业务层实现类
 * @author dell
 *
 */
@Service
public class AddressServiceImpl implements IAddressService {
	
	@Autowired
	private AddressMapper addressMapper;
	
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
		//执行插入用户数据，并获取返回的受影响行数
		Integer rows = addressMapper.insert(address);
		//判断受影响行数是否不为1
		if(rows != 1) {
			//是：抛出InsertException
			throw new InsertException("增加收货地址失败！插入收货地址数据时出现位置错误，请联系系统管理员！");
		}
	}
	
}
