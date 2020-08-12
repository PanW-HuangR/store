package cn.tedu.store.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.Address;
import cn.tedu.store.service.ex.ServiceException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressServiceTests {
	
	@Autowired
	private IAddressService iAddressService;
	
	@Test
	public void addNew() {
		try {
			Integer uid = 2;
			String username = "Haha";
			Address address = new Address();
			address.setReceiver("小刘");
			iAddressService.addNew(uid, username, address);
			System.err.println("OK.");
		} catch (ServiceException e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void getByUid() {
		Integer uid = 4;
		List<Address> list = iAddressService.getByUid(uid);
		for (Address address : list) {
			System.err.println(address);
		}
	}
	
	@Test
	public void setDefault() {
		try {
			Integer aid = 12;
			Integer uid = 4;
			String username = "zhangsan";
			iAddressService.setDefault(aid, uid, username);
		} catch (ServiceException e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void delete() {
		try {
			Integer aid = 16;
			Integer uid = 4;
			String username = "zhangsan";
			iAddressService.delete(aid, uid, username);
		} catch (ServiceException e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
}
