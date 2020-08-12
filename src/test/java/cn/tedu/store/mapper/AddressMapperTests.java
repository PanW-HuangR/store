package cn.tedu.store.mapper;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.Address;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressMapperTests {
	
	@Autowired
	private AddressMapper addressMapper;
	
	@Test
	public void insert() {
		
		Address address = new Address();
		address.setUid(2);
		address.setReceiver("小李");
		Integer rows = addressMapper.insert(address);
		System.err.println("rows="+rows);
		
	}
	
	@Test
	public void countByUid() {
		Integer uid = 2;
		Integer rows = addressMapper.countByUid(uid);
		System.err.println("rows="+rows);
	}
	
	@Test
	public void findByUid() {
		Integer uid = 4;
		List<Address> list = addressMapper.findByUid(uid);
		for (Address address : list) {
			System.err.println(address);
		}
	}
	
	@Test
	public void updateNonDefaultByUid() {
		Integer uid = 4;
		Integer rows = addressMapper.updateNonDefaultByUid(uid);
		System.err.println("rows="+rows);
	}
	
	@Test
	public void updateDefaultByAid() {
		Integer aid = 11;
		Integer rows = addressMapper.updateDefaultByAid(aid, "zhangsan", new Date());
		System.err.println("rows="+rows);
	}
	
	@Test
	public void findByAid() {
		Integer aid = 11;
		Address address = addressMapper.findByAid(aid);
		System.err.println(address);
	}
	
	@Test
	public void deleteByAid() {
		Integer aid = 10;
		Integer rows = addressMapper.deleteByAid(aid);
		System.err.println("rows="+rows);
	}
	
	@Test
	public void findLastModifiedByUid() {
		Integer uid = 4;
		Address address = addressMapper.findLastModifiedByUid(uid);
		System.err.println(address);
	}
	
}
